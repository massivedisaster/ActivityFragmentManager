package com.example;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Detector.JavaScanner;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.Speed;

import java.util.Arrays;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.MethodInvocation;

/**
 * Checks for missing {@code recycle} calls on resources that encourage it, and
 * for missing {@code commit} calls on FragmentTransactions, etc.
 */
public class CommitTransaction extends Detector implements JavaScanner {

    private static final Implementation IMPLEMENTATION = new Implementation(
            CommitTransaction.class,
            Scope.JAVA_FILE_SCOPE);

    /**
     * Problems with missing commit calls.
     */
    public static final Issue COMMIT_FRAGMENT = Issue.create(
            "CommitTransaction11", //$NON-NLS-1$
            "Missing `commit()` calls",

            "After1 creating a `FragmentTransaction`, you typically need to commit it as well",

            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            IMPLEMENTATION);

    // Target method names
    private static final String OBTAIN = "obtain";                                    //$NON-NLS-1$
    private static final String ACQUIRE_CPC = "acquireContentProviderClient";         //$NON-NLS-1$
    private static final String OBTAIN_NO_HISTORY = "obtainNoHistory";                //$NON-NLS-1$
    private static final String OBTAIN_ATTRIBUTES = "obtainAttributes";               //$NON-NLS-1$
    private static final String OBTAIN_TYPED_ARRAY = "obtainTypedArray";              //$NON-NLS-1$
    private static final String OBTAIN_STYLED_ATTRIBUTES = "obtainStyledAttributes";  //$NON-NLS-1$
    private static final String BEGIN_TRANSACTION = "add";                              //$NON-NLS-1$
    private static final String COMMIT = "commit";                                    //$NON-NLS-1$
    private static final String COMMIT_ALLOWING_LOSS = "commitAllowingStateLoss";     //$NON-NLS-1$
    private static final String QUERY = "query";                                      //$NON-NLS-1$
    private static final String RAW_QUERY = "rawQuery";                               //$NON-NLS-1$
    private static final String QUERY_WITH_FACTORY = "queryWithFactory";              //$NON-NLS-1$
    private static final String RAW_QUERY_WITH_FACTORY = "rawQueryWithFactory";       //$NON-NLS-1$

    private static final String MOTION_EVENT_CLS = "android.view.MotionEvent";        //$NON-NLS-1$
    private static final String DIALOG_FRAGMENT = "android.app.DialogFragment";       //$NON-NLS-1$
    private static final String DIALOG_V4_FRAGMENT =
            "android.support.v4.app.DialogFragment";                                  //$NON-NLS-1$
    private static final String FRAGMENT_MANAGER_CLS = "android.app.FragmentManager"; //$NON-NLS-1$
    private static final String FRAGMENT_MANAGER_V4_CLS =
            "android.support.v4.app.FragmentManager";                                 //$NON-NLS-1$
    private static final String FRAGMENT_TRANSACTION_CLS =
            "android.app.FragmentTransaction";                                        //$NON-NLS-1$
    private static final String FRAGMENT_TRANSACTION_V4_CLS =
            "android.support.v4.app.FragmentTransaction";                             //$NON-NLS-1$


    /**
     * Constructs a new
     */
    public CommitTransaction() {
    }

    // ---- Implements JavaScanner ----

    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList(
                // FragmentManager commit check
                BEGIN_TRANSACTION
        );
    }

    @Override
    public void visitMethod(@NonNull JavaContext context, @Nullable AstVisitor visitor,
                            @NonNull MethodInvocation node) {

        String name = node.astName().astValue();
        if (BEGIN_TRANSACTION.equals(name)) {
            //checkTransactionCommits(context, node);

            String message = "This transaction should be completed with a `commit()` call";
            context.report(COMMIT_FRAGMENT, node, context.getLocation(node.astName()),
                    message);
        }
    }


    @NonNull
    @Override
    public Speed getSpeed() {
        return Speed.FAST;
    }


   /* private static boolean checkTransactionCommits(@NonNull JavaContext context,
                                                   @NonNull MethodInvocation node) {
        if (isBeginTransaction(context, node)) {
            ResolvedVariable boundVariable = getVariable(context, node);
            if (boundVariable == null && isCommittedInChainedCalls(context, node)) {
                return true;
            }

            if (boundVariable != null) {
                Node method = JavaContext.findSurroundingMethod(node);
                if (method == null) {
                    return true;
                }

                FinishVisitor commitVisitor = new FinishVisitor(context, boundVariable) {
                    @Override
                    protected boolean isCleanupCall(@NonNull MethodInvocation call) {
                        if (isTransactionCommitMethodCall(mContext, call)) {
                            Expression operand = call.astOperand();
                            if (operand != null) {
                                ResolvedNode resolved = mContext.resolve(operand);
                                //noinspection SuspiciousMethodCalls
                                if (resolved != null && mVariables.contains(resolved)) {
                                    return true;
                                } else if (resolved instanceof ResolvedMethod
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
                        } else if (isShowFragmentMethodCall(mContext, call)) {
                            StrictListAccessor<Expression, MethodInvocation> arguments =
                                    call.astArguments();
                            if (arguments.size() == 2) {
                                Expression first = arguments.first();
                                ResolvedNode resolved = mContext.resolve(first);
                                //noinspection SuspiciousMethodCalls
                                if (resolved != null && mVariables.contains(resolved)) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                };

                method.accept(commitVisitor);
                if (commitVisitor.isCleanedUp() || commitVisitor.variableEscapes()) {
                    return true;
                }
            }

            String message = "This transaction should be completed with a `commit()` call";
            context.report(COMMIT_FRAGMENT, node, context.getLocation(node.astName()),
                    message);
        }
        return false;
    }

    private static boolean isCommittedInChainedCalls(@NonNull JavaContext context,
                                                     @NonNull MethodInvocation node) {
        // Look for chained calls since the FragmentManager methods all return "this"
        // to allow constructor chaining, e.g.
        //    getFragmentManager().beginTransaction().addToBackStack("test")
        //            .disallowAddToBackStack().hide(mFragment2).setBreadCrumbShortTitle("test")
        //            .show(mFragment2).setCustomAnimations(0, 0).commit();
        Node parent = node.getParent();
        while (parent instanceof MethodInvocation) {
            MethodInvocation methodInvocation = (MethodInvocation) parent;
            if (isTransactionCommitMethodCall(context, methodInvocation)
                    || isShowFragmentMethodCall(context, methodInvocation)) {
                return true;
            }

            parent = parent.getParent();
        }

        return false;
    }

    private static boolean isTransactionCommitMethodCall(@NonNull JavaContext context,
                                                         @NonNull MethodInvocation call) {

        String methodName = call.astName().astValue();
        return (COMMIT.equals(methodName) || COMMIT_ALLOWING_LOSS.equals(methodName)) &&
                isMethodOnFragmentClass(context, call,
                        FRAGMENT_TRANSACTION_CLS,
                        FRAGMENT_TRANSACTION_V4_CLS);
    }

    private static boolean isShowFragmentMethodCall(@NonNull JavaContext context,
                                                    @NonNull MethodInvocation call) {
        String methodName = call.astName().astValue();
        return SHOW.equals(methodName)
                && isMethodOnFragmentClass(context, call,
                DIALOG_FRAGMENT, DIALOG_V4_FRAGMENT);
    }

    private static boolean isMethodOnFragmentClass(
            @NonNull JavaContext context,
            @NonNull MethodInvocation call,
            @NonNull String fragmentClass,
            @NonNull String v4FragmentClass) {
        ResolvedNode resolved = context.resolve(call);
        if (resolved instanceof ResolvedMethod) {
            ResolvedClass containingClass = ((ResolvedMethod) resolved).getContainingClass();
            return containingClass.isSubclassOf(fragmentClass, false) ||
                    containingClass.isSubclassOf(v4FragmentClass, false);
        }

        return false;
    }

    @Nullable
    public static ResolvedVariable getVariable(@NonNull JavaContext context,
                                               @NonNull Node expression) {
        Node parent = expression.getParent();
        if (parent instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) parent;
            if (binaryExpression.astOperator() == BinaryOperator.ASSIGN) {
                Expression lhs = binaryExpression.astLeft();
                ResolvedNode resolved = context.resolve(lhs);
                if (resolved instanceof ResolvedVariable) {
                    return (ResolvedVariable) resolved;
                }
            }
        } else if (parent instanceof VariableDefinitionEntry) {
            ResolvedNode resolved = context.resolve(parent);
            if (resolved instanceof ResolvedVariable) {
                return (ResolvedVariable) resolved;
            }
        }

        return null;
    }

    private static boolean isBeginTransaction(@NonNull JavaContext context,
                                              @NonNull MethodInvocation node) {
        String methodName = node.astName().astValue();
        assert methodName.equals(BEGIN_TRANSACTION) : methodName;
        if (BEGIN_TRANSACTION.equals(methodName)) {
            ResolvedNode resolved = context.resolve(node);
            if (resolved instanceof ResolvedMethod) {
                ResolvedMethod method = (ResolvedMethod) resolved;
                ResolvedClass containingClass = method.getContainingClass();
                if (containingClass.isSubclassOf(FRAGMENT_MANAGER_CLS, false)
                        || containingClass.isSubclassOf(FRAGMENT_MANAGER_V4_CLS,
                        false)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Visitor which checks whether an operation is "finished"; in the case
     * of a FragmentTransaction we're looking for a "commit" call; in the
     * case of a TypedArray we're looking for a "recycle", call, in the
     * case of a database cursor we're looking for a "close" call, etc.
     */
    /*private abstract static class FinishVisitor extends ForwardingAstVisitor {
        protected final JavaContext mContext;
        protected final List<ResolvedVariable> mVariables;
        private boolean mContainsCleanup;
        private boolean mEscapes;

        public FinishVisitor(JavaContext context, @NonNull ResolvedVariable variable) {
            mContext = context;
            mVariables = Lists.newArrayList(variable);
        }

        public boolean isCleanedUp() {
            return mContainsCleanup;
        }

        public boolean variableEscapes() {
            return mEscapes;
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

            // Look for escapes
            if (!mEscapes) {
                for (Expression expression : call.astArguments()) {
                    if (expression instanceof VariableReference) {
                        ResolvedNode resolved = mContext.resolve(expression);
                        //noinspection SuspiciousMethodCalls
                        if (resolved != null && mVariables.contains(resolved)) {
                            mEscapes = true;

                            // Special case: MotionEvent.obtain(MotionEvent): passing in an
                            // event here does not recycle the event, and we also know it
                            // doesn't escape
                            if (OBTAIN.equals(call.astName().astValue())) {
                                ResolvedNode r = mContext.resolve(call);
                                if (r instanceof ResolvedMethod) {
                                    ResolvedMethod method = (ResolvedMethod) r;
                                    ResolvedClass cls = method.getContainingClass();
                                    if (cls.matches(MOTION_EVENT_CLS)) {
                                        mEscapes = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }

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
                ResolvedNode resolved = mContext.resolve(initializer);
                //noinspection SuspiciousMethodCalls
                if (resolved != null && mVariables.contains(resolved)) {
                    ResolvedNode resolvedVariable = mContext.resolve(node);
                    if (resolvedVariable instanceof ResolvedVariable) {
                        ResolvedVariable variable = (ResolvedVariable) resolvedVariable;
                        mVariables.add(variable);
                    } else if (resolvedVariable instanceof ResolvedField) {
                        mEscapes = true;
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
                    ResolvedNode resolved = mContext.resolve(rhs);
                    //noinspection SuspiciousMethodCalls
                    if (resolved != null && mVariables.contains(resolved)) {
                        ResolvedNode resolvedLhs = mContext.resolve(node.astLeft());
                        if (resolvedLhs instanceof ResolvedVariable) {
                            ResolvedVariable variable = (ResolvedVariable) resolvedLhs;
                            mVariables.add(variable);
                        } else if (resolvedLhs instanceof ResolvedField) {
                            mEscapes = true;
                        }
                    }
                }
            }
            return super.visitBinaryExpression(node);
        }

        @Override
        public boolean visitReturn(Return node) {
            Expression value = node.astValue();
            if (value instanceof VariableReference) {
                ResolvedNode resolved = mContext.resolve(value);
                //noinspection SuspiciousMethodCalls
                if (resolved != null && mVariables.contains(resolved)) {
                    mEscapes = true;
                }
            }

            return super.visitReturn(node);
        }
    }*/
}