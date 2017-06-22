package com.massivedisaster.example.feature.sharedelements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.adal.adapter.OnChildClickListener;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Shared Elements Options
 */
public class FragmentSharedElementsOptions extends AbstractBaseFragment {


    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shared_elements_options;
    }

    @Override
    protected void getFromBundle(Bundle bundle) {
        //TODO
    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        //TODO
    }

    @Override
    protected void doOnCreated() {
        ImageAdapter adapter = new ImageAdapter(makeSequence(1, 10));
        adapter.setOnChildClickListener(new OnChildClickListener<Integer>() {
            @Override
            public void onChildClick(View view, Integer integer, int position) {
                openItem(view, integer);
            }
        });


        RecyclerView rclItems = findViewById(R.id.rclItems);
        rclItems.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rclItems.setAdapter(adapter);
    }

    /**
     * Open a new Activity and share the element
     *
     * @param view    item view
     * @param integer element id
     */
    protected void openItem(View view, Integer integer) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://lorempixel.com/400/200/nature/" + integer);

        ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentSharedElement.class)
                .setBundle(bundle)
                .addSharedElement(view.findViewById(R.id.imgExample), "sharedElement")
                .commit();
    }

    /**
     * Generate a List of integers between 2 values
     *
     * @param begin the lowest value
     * @param end   the biggest value
     * @return the List of items.
     */
    private List<Integer> makeSequence(int begin, int end) {
        List<Integer> ret = new ArrayList<>();

        for (int i = begin; i <= end; i++) {
            ret.add(i);
        }

        return ret;
    }

}
