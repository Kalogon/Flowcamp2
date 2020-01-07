package com.example.loginactivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DelcourseListAdapter extends BaseAdapter {
    JSONObject user;
    String userid="";

    private Context context;
    private List<Course> courseList;

    private Schedule schedule = new Schedule();
    private  List<Integer> courseIDList;  //



    public DelcourseListAdapter(Context context,List<Course> courseList) throws JSONException {
        this.context = context;
        this.courseList = courseList;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        user=SubActivity.user;
        userid = user.getString("userid");
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
        View v =View.inflate(context,R.layout.delcourse,null);
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

        Button delButton = (Button) v.findViewById(R.id.delButton);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseid = String.valueOf(courseList.get(i).getCourseID());
                try {
                    delcourse(courseid,userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        return v;
    }




    public void delcourse ( String courseid, String temp) throws JSONException {
        JSONObject delcourse=new JSONObject();
        delcourse.accumulate("user_id",temp);
        delcourse.accumulate("course_id", courseid);
        new JSONTaskdelCourse(delcourse).execute("http://192.249.19.254:7180/delcourse", courseid );

    }

}