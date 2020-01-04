package com.example.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class SubActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    public PagerAdapter pagerAdapter;
    static ArrayList<Bitmap> im_array;
    public static boolean first=false;
    public static final String SP_NAME = "image_sf";
    public static SharedPreferences sharedPreferences;
    public static JSONObject user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        findViewById(R.id.btn_custom_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TabLayout tablayout = findViewById(R.id.tablayout);
        tab1 = findViewById(R.id.Tab1);
        tab2 = findViewById(R.id.Tab2);
        tab3 = findViewById(R.id.Tab3);
        viewPager = findViewById(R.id.viewpager);
        try {
            String temp=getIntent().getStringExtra("user");
            if(temp!=null){
                user=new JSONObject(temp);
            }
            else{
                user=null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray check = new JSONArray();
        try {
            check=new JSONArray(user.getString("friends"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        first=(check.length()==0);
        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);



        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

}
