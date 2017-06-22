package com.massivedisaster.example.feature.sharedelements;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massivedisaster.adal.adapter.AbstractLoadMoreBaseAdapter;
import com.massivedisaster.adal.adapter.BaseViewHolder;
import com.massivedisaster.example.activitymanager.R;

import java.util.List;

/**
 * Image Adapter
 */
class ImageAdapter extends AbstractLoadMoreBaseAdapter<Integer> {

    /**
     * Image Adapter constructor
     *
     * @param lstItems List of items to populate adapter
     */
    ImageAdapter(List<Integer> lstItems) {
        super(R.layout.adapter_image, lstItems);
    }

    @Override
    protected void bindItem(BaseViewHolder holder, Integer item) {
        Glide.with(holder.itemView.getContext())
                .load("http://lorempixel.com/400/200/nature/" + item)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) holder.getView(R.id.imgExample));
    }

    @Override
    protected void bindError(BaseViewHolder holder, boolean loadingError) {

    }
}
