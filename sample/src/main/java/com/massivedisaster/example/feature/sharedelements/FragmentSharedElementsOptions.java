package com.massivedisaster.example.feature.sharedelements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Shared Elements Options
 */
public class FragmentSharedElementsOptions extends Fragment implements View.OnClickListener {

    private Button mBtnAddFragment, mBtnOpenFragment, mBtnReplaceFragment;
    private ImageView mImgSharedElement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shared_elements_options, container, false);

        mBtnAddFragment = (Button) v.findViewById(R.id.btnAddFragment);
        mBtnOpenFragment = (Button) v.findViewById(R.id.btnOpenFragment);
        mBtnReplaceFragment = (Button) v.findViewById(R.id.btnReplaceFragment);

        mImgSharedElement = (ImageView) v.findViewById(R.id.imgSharedElement);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnAddFragment.setOnClickListener(this);
        mBtnReplaceFragment.setOnClickListener(this);
        mBtnOpenFragment.setOnClickListener(this);

        FragmentTransaction f = getFragmentManager().beginTransaction();
        f.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFragment:
                add();
                break;
            case R.id.btnReplaceFragment:
                replace();
                break;
            case R.id.btnOpenFragment:
                open();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Add a new fragment, the transaction dont work here.
     */
    private void add() {
        ActivityFragmentManager.add(null, null);

        ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentSharedElement.class)
                .addSharedElement(mImgSharedElement, ViewCompat.getTransitionName(mImgSharedElement))
                .commit();
    }

    /**
     * Replace the fragment
     */
    private void replace() {
        ActivityFragmentManager.replace((AbstractFragmentActivity) getActivity(), FragmentSharedElement.class)
                .addSharedElement(mImgSharedElement, ViewCompat.getTransitionName(mImgSharedElement))
                .commit();
    }

    /**
     * Open a new fragment on a new activity
     */
    private void open() {
        ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentSharedElement.class)
                .addSharedElement(getActivity().findViewById(R.id.imgSharedElement),
                        ViewCompat.getTransitionName(getActivity().findViewById(R.id.imgSharedElement)))
                .commit();
    }
}
