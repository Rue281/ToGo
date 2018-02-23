package com.bricksai.togo.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bricksai.togo.R;

/**
 * Created by Remonda on 5/7/2017.
 */

public class AndroidImageAdapter extends PagerAdapter {
    Context mContext;
    private int[] sliderImagesId = new int[]{
            R.drawable.fish, R.drawable.phone, R.drawable.button,
            R.drawable.card, R.drawable.lock};

    public AndroidImageAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(sliderImagesId[position]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
