package com.example.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SubActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3,tab4,tab5,tab6;
    public PagerAdapter pagerAdapter;
    static ArrayList<Bitmap> im_array;
    public static boolean first=false;
    public static final String SP_NAME = "image_sf";
    public static SharedPreferences sharedPreferences;
    public static JSONObject user;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        context = getApplicationContext();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TabLayout tablayout = findViewById(R.id.tablayout);
        tab1 = findViewById(R.id.Tab1);
        tab2 = findViewById(R.id.Tab2);
        tab3 = findViewById(R.id.Tab3);
        tab4 = findViewById(R.id.Tab4);
        tab5 = findViewById(R.id.Tab5);

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
                }else if(tab.getPosition()==3) {
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==4) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }


    private long lastTimeBackPressed;

    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis()-lastTimeBackPressed<1500){
            finish();
            return;
        }
        Toast.makeText(this,"'뒤로' 버튼을 한번더 눌러 종료합니다.",Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();

    }



}
