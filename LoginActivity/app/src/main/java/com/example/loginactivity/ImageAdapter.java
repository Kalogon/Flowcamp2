package com.example.loginactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Bitmap> imageArray;


    public ImageAdapter(Context mContext, ArrayList<Bitmap> list){
        this.mContext = mContext;
        imageArray = list;
    }


    @Override
    public int getCount() {return imageArray.size();}

    @Override
    public Object getItem(int position) {return imageArray.get(position);}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(imageArray.get(position));
        imageView.setScaleType((ImageView.ScaleType.CENTER_CROP));
        imageView.setLayoutParams(new GridView.LayoutParams(340,350));


        return imageView;
    }
}