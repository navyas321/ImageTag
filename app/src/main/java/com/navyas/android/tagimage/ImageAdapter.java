package com.navyas.android.tagimage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }


            switch (GridViewActivity.screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    Picasso.with(mContext)
                            .load(Uri.parse(getItem(position).toString()))
                            .noFade()
                            .resize(500, 500)
                            .centerCrop()
                            .into(imageView);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    Picasso.with(mContext)
                            .load(Uri.parse(getItem(position).toString()))
                            .noFade()
                            .resize(500, 500)
                            .centerCrop()
                            .into(imageView);
                    break;

                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    Picasso.with(mContext)
                            .load(Uri.parse(getItem(position).toString()))
                            .noFade()
                            .resize(450, 450)
                            .centerCrop()
                            .into(imageView);
                    break;

                default:
                    Picasso.with(mContext)
                            .load(Uri.parse(getItem(position).toString()))
                            .noFade()
                            .resize(450, 450)
                            .centerCrop()
                            .into(imageView);
            }


            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(getItem(pos).toString()), "image/*");
                    mContext.startActivity(intent);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getItem(pos).toString()));
                    intent.setType("image/*");
                    mContext.startActivity(Intent.createChooser(intent, "Share"));
                    return true;
                }
            });

        return imageView;

    }




}