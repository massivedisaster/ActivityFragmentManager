package com.massivedisaster.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.massivedisaster.activitymanager.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activity.PrimaryThemeActivity;
import com.massivedisaster.example.activity.SecondaryThemeActivity;
import com.massivedisaster.example.activitymanager.R;

/**
 * Activity Manager
 * Created by jms on 27/04/16.
 */
public class FragmentOpenAdd extends Fragment implements View.OnClickListener {

    private Button mBtnAddFragemnt, mBtnOpenSecondaryActivity, mBtnOpenPrimaryActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_open_add, container, false);

        mBtnAddFragemnt = (Button) v.findViewById(R.id.btnAddFragment);
        mBtnOpenSecondaryActivity = (Button) v.findViewById(R.id.btnOpenSecondaryTheme);
        mBtnOpenPrimaryActivity = (Button) v.findViewById(R.id.btnOpenPrimaryTheme);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnAddFragemnt.setOnClickListener(this);
        mBtnOpenSecondaryActivity.setOnClickListener(this);
        mBtnOpenPrimaryActivity.setOnClickListener(this);

        getActivity().setTitle(getString(R.string.number_fragment_in_this_activity) + (getActivity().getSupportFragmentManager().getBackStackEntryCount() + 1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFragment:
                ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentOpenAdd.class);
                break;
            case R.id.btnOpenPrimaryTheme:
                ActivityFragmentManager.open(getActivity(), PrimaryThemeActivity.class, FragmentOpenAdd.class);
                break;
            case R.id.btnOpenSecondaryTheme:
                ActivityFragmentManager.open(getActivity(), SecondaryThemeActivity.class, FragmentOpenAdd.class);
                break;
        }
    }
}
