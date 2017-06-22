/*
 * ActivityFragmentManager - A library to help android developer working easily with activities and fragments.
 *
 * Copyright (c) 2017 ActivityFragmentManager
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.massivedisaster.example.feature.sharedelements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.adal.adapter.OnChildClickListener;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activity.ActivityToolbar;
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
        getActivity().setTitle(R.string.shared_elements);

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

        ActivityFragmentManager.open(getActivity(), ActivityToolbar.class, FragmentSharedElement.class)
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
