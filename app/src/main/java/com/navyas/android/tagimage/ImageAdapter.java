package com.navyas.android.tagimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return GridViewActivity.gridArray.length;
    }

    public Object getItem(int position) {
        return GridViewActivity.gridArray[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(Uri.parse(getItem(position).toString()))
                .noFade()
                .resize(450, 450)
                .centerCrop()
                .into(imageView);
        final int pos = position;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(getItem(pos).toString()), "image/*");
                mContext.startActivity(intent);
            }
        });

        return imageView;
    }




}