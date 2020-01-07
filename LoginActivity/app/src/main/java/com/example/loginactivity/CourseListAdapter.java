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
//                boolean validate = false;
//                validate = schedule.validate(courseList.get(i).getCourseTime());
//                user = SubActivity.user;
//                if (!alreadyIn(courseIDList, courseList.get(i).getCourseID())) {  //이미 신청한 강의 인지 검증
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
//                            .setPositiveButton("다시 시도", null)
//                            .create();
//                    dialog.show();
//
//                }else if (validate == false) {  //유효성채크(SCHEDUEL 클래스에서 채크)  이미
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    AlertDialog dialog = builder.setMessage("시간표가 중복 됩니다.")
//                            .setPositiveButton("다시 시도", null)
//                            .create();
//                    dialog.show();
//                }else{
//
//                    //addcourse코드
//                    //(성공시)
//                    courseIDlist.add(courseList.get(i).getCourseID());
//                    schedule.addSchedule(courseList.get(i).getCourseTime());
//
//
//                }
//
//
//                try {
//                    userid = user.getString("userid");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //중복 및 유효성 채크
//                try {
//                    addcourse(courseList.get(i).getCourseID()+ "",userid);  //사용자 아이디과 강의 아이디를 서버측에 보내서 특정 사용자가 특정수업을 들었다를 저장
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                //서버측에서 리스폰스로 성공 및 실패에 대한 정보를 return
//                boolean success =
//                if()



                //validate = schedule.validate(courseList.get(i).getCourseTime()); //유효성 채크
//                if (!alreadyIn(courseIDList, courseList.get(i).getCourseID())) {  //중복채크
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    AlertDialog dialog = builder.setMessage("강의가 추가 되었습니다..")
//                            .setPositiveButton("확인", null)
//                            .create();
//                    dialog.show();
//
//                } else if (validate == false) {  //유효성채크(SCHEDUEL 클래스에서 채크)
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    AlertDialog dialog = builder.setMessage("강의 추가에 실패 했습니다.")
//                            .setPositiveButton("다시 시도", null)
//                            .create();
//                    dialog.show();
//                }


//                addRequest = new AddRequest(userid, courseList.get(i).getCourseID() + "" ,responseListener);  //데이터의 스케쥴데이터를 스케쥴 큐 에 넣어준다.
//                RequestQueue queue = Volley.newRequestQueue(context);
//                queue.add(addRequest);


            }



                ///////////////////////////////////수업시간표 체크/////////////////////////////////////////////

//                boolean validate = false;
//                validate = schedule.validate(courseList.get(i).getCourseTime());
//                if(!alreadyln(courseIDList,courseList.get(i).getCourseID())){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext,getActivity());
//                    AlertDialog dialog = builder.setMessage("이미 추가한 강의 입니다.")
//                            .setPositiveButton("확인",null)
//                            .create();
//                    dialog.show();
//
//                }
//                else if(validate==false){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext,getActivity());
//                    AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
//                            .setPositiveButton("다시 시도",null)
//                            .create();
//                    dialog.show();
//
//                }else{
//
//                    //서버의 회신을 받는다.
                ////////////성공시/////////////////////////////
                // courseIDList.add(courseList.get(i).getCourseID());
                //schedule.addSchedule(courseList.get(i).getCourseTime());
                ///////////////////////////////////////////////////////
//
//                }

                /////////////////////////////////////////////////////////////////////////////////////////////////////
//                user=SubActivity.user;
//                try {
//                    userid = user.getString("userid");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                String courseid = URLEncoder.encode(courseList.get(i).getCourseID());
//
//                addcourse();//강의 추가

                //////////////////디아로그로 가의 추가 실패 성공 추가 에정지//////////////////////////////////








                ////////////////////////////////////////////////////////////////////////////////////////////////



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

    public void addcourse (String courseid,String temp) throws JSONException {
        JSONObject addcourse=new JSONObject();
        addcourse.accumulate("user_id",temp);
        addcourse.accumulate("course_id", courseid);

        new JSONTaskCourse(addcourse).execute("http://192.249.19.254:7180/addcourse", courseid );
    }

//    public void checkschedule (String temp) throws JSONException {
//        JSONObject checkschedule=new JSONObject();
//        checkschedule.accumulate("user_id",temp);
//
//        new JSONTaskSchedule(checkschedule).execute("http://192.249.19.254:7180/checkschedule");
//    }

}
