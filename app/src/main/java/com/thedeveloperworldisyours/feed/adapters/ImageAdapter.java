package com.thedeveloperworldisyours.feed.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.feed.R;

import java.util.List;

import static com.thedeveloperworldisyours.feed.R.id.rowlayout_background_imageView;

/**
 * Created by javiergonzalezcabezas on 29/3/15.
 */
public class ImageAdapter extends ArrayAdapter<String> {
    private final Activity mActivity;
    private final List<String> mValues;



    static class ViewHolder {
        public ImageView backgroundImageView;
        public ImageView imageView;
    }

    public ImageAdapter(Activity activity, List<String> vaules) {
        super(activity, R.layout.rowlayout, vaules);
        this.mActivity = activity;
        this.mValues = vaules;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.backgroundImageView = (ImageView) rowView.findViewById(rowlayout_background_imageView);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.rowlayout_imageView);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setPadding(8, 8, 8, 8);
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.close);

        holder.backgroundImageView.setAnimation(animation);


        Picasso.with(mActivity).load(mValues.get(position)).into(holder.imageView);


        return rowView;
    }

}

