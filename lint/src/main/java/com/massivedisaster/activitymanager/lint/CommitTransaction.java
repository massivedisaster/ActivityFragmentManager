package com.massivedisaster.activitymanager.lint;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Detector.JavaScanner;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.Speed;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.BinaryExpression;
import lombok.ast.BinaryOperator;
import lombok.ast.ConstructorInvocation;
import lombok.ast.Expression;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;
import lombok.ast.Return;
import lombok.ast.VariableDefinitionEntry;
import lombok.ast.VariableReference;

/**
 * Checks for missing {@code commit} or {@code getIntent}calls on resources that encourage it.
 */
public class CommitTransaction extends Detector implements JavaScanner {

    private static final Implementation IMPLEMENTATION = new Implementation(
            CommitTransaction.class,
            Scope.JAVA_FILE_SCOPE);

    /**
     * Problems with missing commit calls.
     */
    static final Issue COMMIT_FRAGMENT = Issue.create(
            "CommitTransaction", //$NON-NLS-1$
            "Missing `commit()` calls",

            "After creating a `Transaction`, you typically need to commit it as well",

            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            IMPLEMENTATION);


    private static final String ADD_TRANSACTION = "add";                                                            //$NON-NLS-1$
    private static final String OPEN_TRANSACTION = "open";                                                          //$NON-NLS-1$
    private static final String REPLACE_TRANSACTION = "replace";                                                    //$NON-NLS-1$
    private static final String COMMIT = "commit";                                                                  //$NON-NLS-1$
    private static final String GET_INTENT = "getIntent";                                                           //$NON-NLS-1$

    private static final String ACTIVITY_MANAGER = "com.massivedisaster.activitymanager.ActivityFragmentManager";

    /**
     * Constructs a new
     */
    public CommitTransaction() {
    }

    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList(
                ADD_TRANSACTION,
                OPEN_TRANSACTION,
                REPLACE_TRANSACTION
        );
    }

    @Override
    public void visitMethod(@NonNull JavaContext context, @Nullable AstVisitor visitor,
                            @NonNull MethodInvocation node) {
        if (!checkTransactionHasACommit(context, node)) {
            showCommitMessage(context, node);
        }
    }


    @NonNull
    @Override
    public Speed getSpeed() {
        return Speed.FAST;
    }


    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new CallChecker(context);
    }

    /**
     * Call Checker to find lint warnings on-a-fly.
     */
    private static class CallChecker extends ForwardingAstVisitor {

        private final JavaContext mContext;

        CallChecker(JavaContext context) {
            mContext = context;
        }

        @Override
        public boolean visitMethodInvocation(@NonNull MethodInvocation call) {
            JavaParser.ResolvedNode resolved = mContext.resolve(call);
            if (resolved instanceof JavaParser.ResolvedMethod) {
                if (!checkTransactionHasACommit(mContext, call)) {
                    showCommitMessage(mContext, call);
                }
            }

            return false;
        }

        @Override
        public boolean visitConstructorInvocation(@NonNull ConstructorInvocation call) {
            return false;
        }
    }

    /**
     *
     */
    private static boolean checkTransactionHasACommit(@NonNull JavaContext context, @NonNull MethodInvocation node) {
        if (isAddReplaceOpen(context, node)) {
            if (!isCommittedInChainedCalls(context, node)) {
                return false;
            }
        }

        return true;
    }


    private static boolean isAddReplaceOpen(@NonNull JavaContext context, @NonNull MethodInvocation node) {
        String methodName = node.astName().astValue();

        if (ADD_TRANSACTION.equals(methodName) || OPEN_TRANSACTION.equals(methodName) || REPLACE_TRANSACTION.equals(methodName)) {
            JavaParser.ResolvedNode resolved = context.resolve(node);
            if (resolved instanceof JavaParser.ResolvedMethod) {
                JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolved;
                JavaParser.ResolvedClass containingClass = method.getContainingClass();
                if (containingClass.isSubclassOf(ACTIVITY_MANAGER, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return true if exists a commit or a getIntent
     */
    private static boolean isCommittedInChainedCalls(@NonNull JavaContext context, @NonNull MethodInvocation node) {
        JavaParser.ResolvedVariable boundVariable = getVariable(context, node);
        if (boundVariable == null) {
            Node parent = node.getParent();
            while (parent instanceof MethodInvocation) {
                MethodInvocation methodInvocation = (MethodInvocation) parent;
                if (isTransactionCommitMethodCall(methodInvocation)) {
                    return true;
                }

                parent = parent.getParent();
            }
        }

        if (boundVariable != null) {
            Node method = JavaContext.findSurroundingMethod(node);
            if (method == null) {
                return true;
            }

            FinishVisitor commitVisitor = new FinishVisitor(context, boundVariable) {
                @Override
                protected boolean isCleanupCall(@NonNull MethodInvocation call) {
                    if (isTransactionCommitMethodCall(call)) {
                        Expression operand = call.astOperand();
                        if (operand != null) {
                            JavaParser.ResolvedNode resolved = mContext.resolve(operand);
                            //noinspection SuspiciousMethodCalls
                            if (resolved != null && mVariables.contains(resolved)) {
                                return true;
                            } else if (resolved instanceof JavaParser.ResolvedMethod
                                    && operand instanceof MethodInvocation
                                    && isCommittedInChainedCalls(mContext, (MethodInvocation) operand)) {
                                // Check that the target of the committed chains is the
                                // right variable!
                                while (operand instanceof MethodInvocation) {
                                    operand = ((MethodInvocation) operand).astOperand();
                                }
                                if (operand instanceof VariableReference) {
                                    resolved = mContext.resolve(operand);
                                    //noinspection SuspiciousMethodCalls
                                    if (resolved != null && mVariables.contains(resolved)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }
            };

            method.accept(commitVisitor);
            if (commitVisitor.isCleanedUp()) {
                return true;
            }
        }

        return false;
    }

    private static boolean isTransactionCommitMethodCall(@NonNull MethodInvocation call) {
        String methodName = call.astName().astValue();
        return COMMIT.equals(methodName) || GET_INTENT.equals(methodName);
    }

    @Nullable
    private static JavaParser.ResolvedVariable getVariable(@NonNull JavaContext context, @NonNull Node expression) {
        Node parent = expression.getParent();
        if (parent instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) parent;
            if (binaryExpression.astOperator() == BinaryOperator.ASSIGN) {
                Expression lhs = binaryExpression.astLeft();
                JavaParser.ResolvedNode resolved = context.resolve(lhs);
                if (resolved instanceof JavaParser.ResolvedVariable) {
                    return (JavaParser.ResolvedVariable) resolved;
                }
            }
        } else if (parent instanceof VariableDefinitionEntry) {
            JavaParser.ResolvedNode resolved = context.resolve(parent);
            if (resolved instanceof JavaParser.ResolvedVariable) {
                return (JavaParser.ResolvedVariable) resolved;
            }
        }

        return null;
    }


    /**
     * Show the Lint Message
     *
     * @param context The Java Context.
     * @param node    The Node to show the message.
     */
    private static void showCommitMessage(JavaContext context, Node node) {
        String message = "This transaction should be completed with a `commit()` call.";
        context.report(COMMIT_FRAGMENT, node, context.getLocation(node),
                message);
    }

    /**
     * Visitor which checks whether an operation is "finished"; in the case
     * of a FragmentTransaction we're looking for a "commit" call; in the
     * case of a TypedArray we're looking for a "recycle", call, in the
     * case of a database cursor we're looking for a "close" call, etc.
     */
    private abstract static class FinishVisitor extends ForwardingAstVisitor {
        final JavaContext mContext;
        final List<JavaParser.ResolvedVariable> mVariables;
        private boolean mContainsCleanup;

        FinishVisitor(JavaContext context, @NonNull JavaParser.ResolvedVariable variable) {
            mContext = context;
            mVariables = Lists.newArrayList(variable);
        }

        boolean isCleanedUp() {
            return mContainsCleanup;
        }

        @Override
        public boolean visitNode(Node node) {
            return mContainsCleanup || super.visitNode(node);
        }

        protected abstract boolean isCleanupCall(@NonNull MethodInvocation call);

        @Override
        public boolean visitMethodInvocation(MethodInvocation call) {
            if (mContainsCleanup) {
                return true;
            }

            super.visitMethodInvocation(call);

            if (isCleanupCall(call)) {
                mContainsCleanup = true;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean visitVariableDefinitionEntry(VariableDefinitionEntry node) {
            Expression initializer = node.astInitializer();
            if (initializer instanceof VariableReference) {
                JavaParser.ResolvedNode resolved = mContext.resolve(initializer);
                //noinspection SuspiciousMethodCalls
                if (resolved != null && mVariables.contains(resolved)) {
                    JavaParser.ResolvedNode resolvedVariable = mContext.resolve(node);
                    if (resolvedVariable instanceof JavaParser.ResolvedVariable) {
                        JavaParser.ResolvedVariable variable = (JavaParser.ResolvedVariable) resolvedVariable;
                        mVariables.add(variable);
                    }
                }
            }
            return super.visitVariableDefinitionEntry(node);
        }

        @Override
        public boolean visitBinaryExpression(BinaryExpression node) {
            if (node.astOperator() == BinaryOperator.ASSIGN) {
                Expression rhs = node.astRight();
                if (rhs instanceof VariableReference) {
                    JavaParser.ResolvedNode resolved = mContext.resolve(rhs);
                    //noinspection SuspiciousMethodCalls
                    if (resolved != null && mVariables.contains(resolved)) {
                        JavaParser.ResolvedNode resolvedLhs = mContext.resolve(node.astLeft());
                        if (resolvedLhs instanceof JavaParser.ResolvedVariable) {
                            JavaParser.ResolvedVariable variable = (JavaParser.ResolvedVariable) resolvedLhs;
                            mVariables.add(variable);
                        }
                    }
                }
            }
            return super.visitBinaryExpression(node);
        }

        @Override
        public boolean visitReturn(Return node) {
            return super.visitReturn(node);
        }
    }
}
