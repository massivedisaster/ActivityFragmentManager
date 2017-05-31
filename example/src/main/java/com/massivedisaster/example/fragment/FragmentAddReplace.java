package com.massivedisaster.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.massivedisaster.activitymanager.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.TransactionAnimation;
import com.massivedisaster.example.activitymanager.R;

/**
 * Activity Manager
 * Created by jms on 27/04/16.
 */
public class FragmentAddReplace extends Fragment implements View.OnClickListener {

    private Button mBtnAddFragment, mBtnAddFragmentWithAnimation, mBtnReplaceFragment;
    private TextView mTxtNumberOfFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        mBtnAddFragment = (Button) v.findViewById(R.id.btnAddFragment);
        mBtnReplaceFragment = (Button) v.findViewById(R.id.btnReplaceFragment);
        mBtnAddFragmentWithAnimation = (Button) v.findViewById(R.id.btnAddFragmentWithAnimation);
        mTxtNumberOfFragments = (TextView) v.findViewById(R.id.txtNumberOfFragments);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnAddFragment.setOnClickListener(this);
        mBtnReplaceFragment.setOnClickListener(this);
        mBtnAddFragmentWithAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFragment:
                ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class);
                break;
            case R.id.btnAddFragmentWithAnimation:
                ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class, new TransactionAnimation() {
                    @Override
                    public int getEnter() {
                        return R.anim.enter_from_right;
                    }

                    @Override
                    public int getExit() {
                        return R.anim.exit_from_left;
                    }

                    @Override
                    public int getPopEnter() {
                        return R.anim.pop_enter;
                    }

                    @Override
                    public int getPopExit() {
                        return R.anim.pop_exit;
                    }
                });
                break;
            case R.id.btnReplaceFragment:
                ActivityFragmentManager.replace((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTxtNumberOfFragments.setText(new StringBuilder().append(getString(R.string.number_fragment_in_this_activity)).append(getActivity().getSupportFragmentManager().getBackStackEntryCount()).toString());
    }
}
