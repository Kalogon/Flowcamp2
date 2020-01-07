package com.example.loginactivity;

import android.content.Context;
import android.widget.TextView;

public class Schedule {

    private String monday[] = new String[14];
    private String tuesday[] = new String[14];
    private String wednesday[] = new String[14];
    private String thursday[] = new String[14];
    private String friday[] = new String[14];

    public Schedule(){
        for(int i = 0;i<14;i++){
            monday[i] ="";
            tuesday[i] ="";
            wednesday[i]="";
            thursday[i] = "";
            friday[i]="";

        }
    }


    public void addSchedule(String scheduleText,String courseTitle, String courseProfessor){
        String professor;
        if(courseProfessor.equals("")){
            professor = "";
        }
        else{
            professor = courseProfessor;
        }

        int temp;
        if((temp=scheduleText.indexOf("A"))>-1){
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) !=':';i++){
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint+1,endPoint))] = courseTitle + professor;
                }
            }

        }
        if((temp=scheduleText.indexOf("B"))>-1){
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) !=':';i++){
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint+1,endPoint))] = courseTitle + professor;
                }
            }

        }
        if((temp=scheduleText.indexOf("C"))>-1){
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) !=':';i++){
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint+1,endPoint))] = courseTitle + professor;
                }
            }

        }
        if((temp=scheduleText.indexOf("D"))>-1){
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) !=':';i++){
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint+1,endPoint))] = courseTitle + professor;
                }
            }

        }
        if((temp=scheduleText.indexOf("E"))>-1){
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) !=':';i++){
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint+1,endPoint))] = courseTitle + professor;
                }
            }

        }
    }
    public void setting(TextView[] monday, TextView[] tuesday, TextView[] wednesday, TextView[] thursday, TextView[] friday, Context context){
        int maxLength = 0;
        String maxString = "";
        for (int i = 0 ; i < 14; i ++){
            if(this.monday[i].length()>maxLength){
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }
            if(this.tuesday[i].length()>maxLength){
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if(this.wednesday[i].length()>maxLength){
                maxLength = this.wednesday[i].length();
                maxString = this.wednesday[i];
            }
            if(this.thursday[i].length()>maxLength){
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if(this.friday[i].length()>maxLength){
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }
        }

        for(int i = 0 ; i < 14; i++){
            monday[i].setText(this.monday[i]);
            tuesday[i].setText(this.tuesday[i]);
            wednesday[i].setText(this.wednesday[i]);
            thursday[i].setText(this.thursday[i]);
            friday[i].setText(this.friday[i]);
            monday[i].setTextColor(context.getResources().getColor(R.color.steel_blue));
            tuesday[i].setTextColor(context.getResources().getColor(R.color.steel_blue));
            wednesday[i].setTextColor(context.getResources().getColor(R.color.steel_blue));
            thursday[i].setTextColor(context.getResources().getColor(R.color.steel_blue));
            friday[i].setTextColor(context.getResources().getColor(R.color.steel_blue));

            /*if(this.monday[i].equals("")){
                monday[i].setText(this.monday[i]);
                monday[i].setTextColor(context.getResources().getColor(R.color.aqua));
            }
            else{
                monday[i].setText(maxString);
            }
            if(this.tuesday[i].equals("")){
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setTextColor(context.getResources().getColor(R.color.aqua));
            }
            else{
                tuesday[i].setText(maxString);
            }
            if(this.wednesday[i].equals("")){
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setTextColor(context.getResources().getColor(R.color.aqua));
            }
            else{
                wednesday[i].setText(maxString);
            }
            if(this.thursday[i].equals("")){
                thursday[i].setText(this.thursday[i]);
                thursday[i].setTextColor(context.getResources().getColor(R.color.aqua));
            }
            else{
                thursday[i].setText(maxString);
            }
            if(this.friday[i].equals("")){
                friday[i].setText(this.friday[i]);
                friday[i].setTextColor(context.getResources().getColor(R.color.aqua));
            }
            else{
                friday[i].setText(maxString);
            }*/
            /*monday[i].resizeText();
            tuesday[i].resizeText();
            wednesday[i].resizeText();
            thursday[i].resizeText();
            friday[i].resizeText();*/


        }

    }

}
