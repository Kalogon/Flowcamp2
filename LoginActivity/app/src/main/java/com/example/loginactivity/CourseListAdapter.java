package com.example.loginactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    JSONObject user;
    String userid="";

    private Context context;
    private List<Course> courseList;

    private Schedule schedule = new Schedule();
    private  List<Integer> courseIDList;  //



    public CourseListAdapter(Context context,List<Course> courseList) throws JSONException {
        this.context = context;
        this.courseList = courseList;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        //checkschedule(userid); //background task

    }





    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v =View.inflate(context,R.layout.course,null);
        final TextView courseGrade = (TextView)v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView)v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView)v.findViewById(R.id.courseCredit);
        TextView courseDevide = (TextView)v.findViewById(R.id.courseDevide);
        TextView coursePersonnel = (TextView)v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView)v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView)v.findViewById(R.id.courseTime);


        if(courseList.get(i).getCourseGrade().equals("제한없음")||courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText(("모든 학년"));
        }
        else{
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseDevide.setText(courseList.get(i).getCourseDivide() + "분반");
        if(courseList.get(i).getCoursePersonnel() ==0){
            coursePersonnel.setText("인원 제한 없음");
        }else{
            coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel() + "명");
        }
        if(courseList.get(i).getCourseProfessor().equals("")){
            courseProfessor.setText("개인 연구");
        }else{
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        }
        courseProfessor.setText(courseList.get(i).getCourseProfessor()+"교수님");
        courseTime.setText(courseList.get(i).getCourseTime() + "");
        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseid = String.valueOf(courseList.get(i).getCourseID());
                String courseTime = courseList.get(i).getCourseTime();
                try {
                    addcourse(courseTime, courseid, userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return v;
    }




    public boolean alreadyIn(List<Integer>courseIDList,int item){
        for(int i = 0;i<courseIDList.size();i++){
            if(courseIDList.get(i)==item){   //모든 강의아아디 리스트를 돌면서 추가하려는 강의가 이미 있다면 false값을 주도록한다.
                return false;
            }
        }
        return true;
    }
    public void addcourse (String coursetime, String courseid, String temp) throws JSONException {
        JSONObject addcourse=new JSONObject();
        addcourse.accumulate("user_id",temp);
        addcourse.accumulate("course_id", courseid);
        addcourse.accumulate("course_time", coursetime);
        new JSONTaskAddcourse(addcourse).execute("http://192.249.19.254:7180/addcourse", courseid );

    }

//    public void checkschedule (String temp) throws JSONException {
//        JSONObject checkschedule=new JSONObject();
//        checkschedule.accumulate("user_id",temp);
//
//        new JSONTaskSchedule(checkschedule).execute("http://192.249.19.254:7180/checkschedule");
//    }

}
