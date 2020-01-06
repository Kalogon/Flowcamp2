package com.example.loginactivity;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

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
import java.net.URLEncoder;

public class tab3 extends Fragment {
    View v;
    String userid="";
    JSONObject user;

    public tab3(){

    }


    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    private String courseUniversity = "";
    private String courseYear = "";
    private String courseTerm = "";
    private String courseArea = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RadioGroup courseUniversityGroup = (RadioGroup)getView().findViewById(R.id.courseUniversityGroup);
        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        majorSpinner = (Spinner)getView().findViewById(R.id.majorSpinner);

        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton courseButton = (RadioButton)getView().findViewById(i);
                courseUniversity = courseButton.getText().toString();
                yearAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.year,android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);
                termAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.term,android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);

                if(courseUniversity.equals("학부")){

                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.universityArea,android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                    majorAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.universityRefinementMajor,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);

                }else if(courseUniversity.equals("대학원")){

                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.graduateArea,android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                    majorAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.graduateMajor,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);

                }
            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(areaSpinner.getSelectedItem().equals("교양및기타")){
                    majorAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.universityRefinementMajor,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
                if(areaSpinner.getSelectedItem().equals("전공")){
                    majorAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.universityMajor,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
                if(areaSpinner.getSelectedItem().equals("일반대학원")){
                    majorAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.graduateMajor,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button searchButton = (Button)getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String univ = URLEncoder.encode(courseUniversity);
                String term = URLEncoder.encode(termSpinner.getSelectedItem().toString());
                String year = URLEncoder.encode(yearSpinner.getSelectedItem().toString().substring(0,4));
                String area = URLEncoder.encode(areaSpinner.getSelectedItem().toString());
                String major = URLEncoder.encode(majorSpinner.getSelectedItem().toString());

                try {
                    findcourse(univ,term,year,area,major,userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });



    }

    public static tab3 getInstance(){return new tab3();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab3, container, false);
        user = SubActivity.user;
        try {
            userid=user.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

    public void findcourse (String university,String term, String year, String area,String major,String temp) throws JSONException {
        JSONObject findcourse=new JSONObject();
        findcourse.accumulate("user_id",temp);
        findcourse.accumulate("course_university",university);
        findcourse.accumulate("course_term",term);
        findcourse.accumulate("course_year",year);
        findcourse.accumulate("course_Area",area);
        findcourse.accumulate("course_major",major);
        new JSONTaskCourse(findcourse).execute("http://192.249.19.254:7180/findcourse",  university,term, year, area,major);
    }





}

