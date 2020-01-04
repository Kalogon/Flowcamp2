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

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        ImageView imageView =(ImageView)findViewById(R.id.imageView);
        setImage(imageView);
    }

    private void setImage(ImageView imageView){
        Intent receivedIntent = getIntent();
        Bitmap bitmap = (Bitmap) receivedIntent.getParcelableExtra("image");
        imageView.setImageBitmap(bitmap);
    }
}
