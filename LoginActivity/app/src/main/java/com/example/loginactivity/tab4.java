package com.example.loginactivity;


import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
import java.util.List;


public class tab4 extends Fragment {
    static Context tab4;
    View v;
    String userid="";
    JSONObject user;


    public tab4(){


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=SubActivity.user;
        try {
            userid = user.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static TextView[] monday = new TextView[14];
    public static TextView[] tuesday = new TextView[14];
    public static TextView[] wednesday = new TextView[14];
    public static TextView[] thursday = new TextView[14];
    public static TextView[] friday = new TextView[14];
    public static Schedule schedule = new Schedule();

    public static void showschedule(String temp) throws JSONException {
        JSONObject showschedule=new JSONObject();
        showschedule.accumulate("user_id",temp);

        new JSONTaskSchedule (showschedule).execute("http://192.249.19.254:7180/showschedule");
    }



    @Override
    public void onActivityCreated(Bundle b) {  //초기화
        super.onActivityCreated(b);
        monday[0] = (TextView) getView().findViewById(R.id.monday0);
        monday[1] = (TextView) getView().findViewById(R.id.monday1);
        monday[2] = (TextView) getView().findViewById(R.id.monday2);
        monday[3] = (TextView) getView().findViewById(R.id.monday3);
        monday[4] = (TextView) getView().findViewById(R.id.monday4);
        monday[5] = (TextView) getView().findViewById(R.id.monday5);
        monday[6] = (TextView) getView().findViewById(R.id.monday6);
        monday[7] = (TextView) getView().findViewById(R.id.monday7);
        monday[8] = (TextView) getView().findViewById(R.id.monday8);
        monday[9] = (TextView) getView().findViewById(R.id.monday9);
        monday[10] = (TextView) getView().findViewById(R.id.monday10);
        monday[11] = (TextView) getView().findViewById(R.id.monday11);
        monday[12] = (TextView) getView().findViewById(R.id.monday12);
        monday[13] = (TextView) getView().findViewById(R.id.monday13);
        tuesday[0] = (TextView) getView().findViewById(R.id.tuesday0);
        tuesday[1] = (TextView) getView().findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) getView().findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) getView().findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) getView().findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) getView().findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) getView().findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) getView().findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) getView().findViewById(R.id.tuesday8);
        tuesday[9] = (TextView) getView().findViewById(R.id.tuesday9);
        tuesday[10] = (TextView) getView().findViewById(R.id.tuesday10);
        tuesday[11] = (TextView) getView().findViewById(R.id.tuesday11);
        tuesday[12] = (TextView) getView().findViewById(R.id.tuesday12);
        tuesday[13] = (TextView) getView().findViewById(R.id.tuesday13);
        wednesday[0] = (TextView) getView().findViewById(R.id.wednesday0);
        wednesday[1] = (TextView) getView().findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) getView().findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) getView().findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) getView().findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) getView().findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) getView().findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) getView().findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) getView().findViewById(R.id.wednesday8);
        wednesday[9] = (TextView) getView().findViewById(R.id.wednesday9);
        wednesday[10] = (TextView) getView().findViewById(R.id.wednesday10);
        wednesday[11] = (TextView) getView().findViewById(R.id.wednesday11);
        wednesday[12] = (TextView) getView().findViewById(R.id.wednesday12);
        wednesday[13] = (TextView) getView().findViewById(R.id.wednesday13);
        thursday[0] = (TextView) getView().findViewById(R.id.thursday0);
        thursday[1] = (TextView) getView().findViewById(R.id.thursday1);
        thursday[2] = (TextView) getView().findViewById(R.id.thursday2);
        thursday[3] = (TextView) getView().findViewById(R.id.thursday3);
        thursday[4] = (TextView) getView().findViewById(R.id.thursday4);
        thursday[5] = (TextView) getView().findViewById(R.id.thursday5);
        thursday[6] = (TextView) getView().findViewById(R.id.thursday6);
        thursday[7] = (TextView) getView().findViewById(R.id.thursday7);
        thursday[8] = (TextView) getView().findViewById(R.id.thursday8);
        thursday[9] = (TextView) getView().findViewById(R.id.thursday9);
        thursday[10] = (TextView) getView().findViewById(R.id.thursday10);
        thursday[11] = (TextView) getView().findViewById(R.id.thursday11);
        thursday[12] = (TextView) getView().findViewById(R.id.thursday12);
        thursday[13] = (TextView) getView().findViewById(R.id.thursday13);
        friday[0] = (TextView) getView().findViewById(R.id.friday0);
        friday[1] = (TextView) getView().findViewById(R.id.friday1);
        friday[2] = (TextView) getView().findViewById(R.id.friday2);
        friday[3] = (TextView) getView().findViewById(R.id.friday3);
        friday[4] = (TextView) getView().findViewById(R.id.friday4);
        friday[5] = (TextView) getView().findViewById(R.id.friday5);
        friday[6] = (TextView) getView().findViewById(R.id.friday6);
        friday[7] = (TextView) getView().findViewById(R.id.friday7);
        friday[8] = (TextView) getView().findViewById(R.id.friday8);
        friday[9] = (TextView) getView().findViewById(R.id.friday9);
        friday[10] = (TextView) getView().findViewById(R.id.friday10);
        friday[11] = (TextView) getView().findViewById(R.id.friday11);
        friday[12] = (TextView) getView().findViewById(R.id.friday12);
        friday[13] = (TextView) getView().findViewById(R.id.friday13);

        try {
            showschedule(userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public static class JSONTaskSchedule extends AsyncTask<String, String, String> {
        JSONObject showschedule;
        private List<Course> courseList;
        JSONObject jsonObject_user = new JSONObject();
        JSONArray jsonarray = new JSONArray();
        public JSONTaskSchedule(JSONObject showschedule){
            this.showschedule=showschedule;

        }
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송


                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    /*writer.write(jsonObject_each.toString());*/
                    writer.write(showschedule.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int count = 0;
            String courseProfessor = "";
            String courseTime ="";
            String courseTitle = "";
            int courseID;
            schedule = new Schedule();
            JSONArray schedules = new JSONArray();
            try {
                schedules = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            while(count<schedules.length()){
                JSONObject object = null;

                try {
                    object = schedules.getJSONObject(count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    courseID = object.getInt("courseId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    courseProfessor = object.getString("courseProfessor");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    courseTime = object.getString("courseTime");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    courseTitle = object.getString("courseTitle");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                schedule.addSchedule(courseTime, courseTitle, courseProfessor); //스케쥴도 똑같이 들어가게 된다.
                count++;

            }
            schedule.setting(monday,tuesday,wednesday,thursday,friday,tab4);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab4, container, false);
        user = SubActivity.user;
        try {
            userid=user.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tab4=container.getContext();

        return v;
    }

}