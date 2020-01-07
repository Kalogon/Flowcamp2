package com.example.loginactivity;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class tab5 extends Fragment {
    static Context tab5;
    View v;
    static String userid="";
    JSONObject user;
    static ListView cv;
    static ArrayList<Course> delcourses;
    public tab5(){

    }

    public static int totalCredit = 0;
    public static TextView credit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab5, container, false);
        Button search = (Button) v.findViewById(R.id.searchButton);
        cv= v.findViewById(R.id.courseListView);
        tab5=container.getContext();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = SubActivity.user;
                try {
                    userid=user.getString("userid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    getcourselist(userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });




        return v;
    }

    public static void viewCourse(JSONArray c) throws JSONException {
        delcourses = new ArrayList<Course>();
        for(int i=0;i<c.length();i++){
            int element1= Integer.parseInt(c.getJSONObject(i).getString("courseId"));
            String element2=c.getJSONObject(i).getString("courseUniversity");
            int element3= Integer.parseInt(c.getJSONObject(i).getString("courseYear"));
            String element4=c.getJSONObject(i).getString("courseTerm");
            String element5=c.getJSONObject(i).getString("courseArea");
            String element6=c.getJSONObject(i).getString("courseMajor");
            String element7=c.getJSONObject(i).getString("courseGrade");
            String element8=c.getJSONObject(i).getString("courseTitle");
            int element9= Integer.parseInt(c.getJSONObject(i).getString("courseCredit"));
            int element10= Integer.parseInt(c.getJSONObject(i).getString("courseDivide"));
            int element11= Integer.parseInt(c.getJSONObject(i).getString("coursePersonnel"));
            String element12=c.getJSONObject(i).getString("courseProfessor");
            String element13=c.getJSONObject(i).getString("courseTime");
            String element14=c.getJSONObject(i).getString("courseRoom");


            Course course = new Course(element1, element2, element3, element4, element5, element6, element7, element8, element9, element10,element11,element12,element13,element14);
            delcourses.add(course);

        }
    }



    public static void getcourselist(String temp) throws JSONException {
        JSONObject getcourselist=new JSONObject();
        getcourselist.accumulate("user_id",temp);
        new JSONTaskViewdelcourse(getcourselist).execute("http://192.249.19.254:7180/getcourselist" );

    }



}