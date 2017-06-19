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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activitymanager.R;

/**
 * SHared Element Fragment
 * Fragment to show the transaction
 */
public class FragmentSharedElement extends AbstractBaseFragment {

    private String mUrl;

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shared_element;
    }

    @Override
    protected void getFromBundle(Bundle bundle) {
        mUrl = bundle.getString("URL");
    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void doOnCreated() {
        ImageView imgExample = findViewById(R.id.imgSharedElement);

        Glide.with(getContext())
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)

                .into(imgExample);

    }
}
