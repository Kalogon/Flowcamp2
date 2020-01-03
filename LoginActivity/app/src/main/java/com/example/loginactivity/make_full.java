package com.example.loginactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class make_full extends AppCompatActivity {

    public class ViewPagerAdapter extends PagerAdapter {

        private ArrayList<Bitmap> images;
        private Context context;

        private ViewPagerAdapter(Context context, ArrayList<Bitmap> list) {
            this.context = context;
            images = list;
        }

        @Override
        public int getCount() {return images.size();}

        @Override
        public boolean isViewFromObject(View view, Object object) {return view == object;}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.big_image, container, false);
            ImageView imageView = view.findViewById(R.id.imageView);

            imageView.setImageBitmap(images.get(position));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {container.invalidate();}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");


        ViewPager viewPager = findViewById(R.id.full_view);
        viewPager.setCurrentItem(position);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
