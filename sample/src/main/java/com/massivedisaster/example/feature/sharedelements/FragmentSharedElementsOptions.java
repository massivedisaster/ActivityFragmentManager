package com.massivedisaster.example.feature.sharedelements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.adal.adapter.OnChildClickListener;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Shared Elements Options
 */
public class FragmentSharedElementsOptions extends AbstractBaseFragment implements View.OnClickListener {

    private RecyclerView mRclItems;
    private Button mBtnAddFragment, mBtnOpenFragment, mBtnReplaceFragment;
    private ImageView mImgSharedElement;

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shared_elements_options;
    }

    @Override
    protected void getFromBundle(Bundle bundle) {

    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void doOnCreated() {
        mRclItems = findViewById(R.id.rclItems);
        mRclItems.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ImageAdapter adapter = new ImageAdapter(makeSequence(1, 10));

        mRclItems.setAdapter(adapter);

        adapter.setOnChildClickListener(new OnChildClickListener<Integer>() {
            @Override
            public void onChildClick(View view, Integer integer, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("URL", "http://lorempixel.com/400/200/nature/" + integer);

                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentSharedElement.class)
                        .setBundle(bundle)
                        .addSharedElement(view.findViewById(R.id.imgExample), "sharedElement")
                        .commit();
            }
        });
    }
/*
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
*/

    List<Integer> makeSequence(int begin, int end) {
        List<Integer> ret = new ArrayList(end - begin + 1);

        for (int i = begin; i <= end; i++, ret.add(i)) ;

        return ret;
    }

    /**
     * Add a new fragment, the transaction dont work here.
     */
    private void add() {
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

    }

    @Override
    public void onClick(View v) {

    }
}
