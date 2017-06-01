package com.massivedisaster.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activity.ActivityFullscreen;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Home
 */
public class FragmentHome extends Fragment implements View.OnClickListener {

    private Button mBtnOpenFragmentOtherActivity,
            mBtnOpenFragmentOtherActivityFullscreen,
            mBtnOpenFragmentOtherActivityWithCustomAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mBtnOpenFragmentOtherActivity = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivity);
        mBtnOpenFragmentOtherActivityFullscreen = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityFullscreen);
        mBtnOpenFragmentOtherActivityWithCustomAnimation = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityWithCustomAnimation);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnOpenFragmentOtherActivity.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityFullscreen.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityWithCustomAnimation.setOnClickListener(this);

        getActivity().setTitle(getString(R.string.home));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenFragmentOtherActivity:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class);
                break;
            case R.id.btnOpenFragmentOtherActivityFullscreen:
                ActivityFragmentManager.open(getActivity(), ActivityFullscreen.class, FragmentAddReplace.class);
                break;
            case R.id.btnOpenFragmentOtherActivityWithCustomAnimation:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class);
                break;
            default:
                break;
        }
    }
}
