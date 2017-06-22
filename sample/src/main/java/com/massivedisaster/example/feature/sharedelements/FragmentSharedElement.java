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
        //TODO
    }

    @Override
    protected void doOnCreated() {
        getActivity().setTitle(R.string.image_detail);

        ImageView imgExample = findViewById(R.id.imgSharedElement);

        Glide.with(getContext())
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imgExample);

    }
}
