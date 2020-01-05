package com.example.loginactivity;

import android.os.AsyncTask;

import org.json.JSONArray;
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

public class JSONTaskdel extends AsyncTask<String, String, String> {
    JSONObject delcontact;
    JSONArray jsonarray = new JSONArray();
    public JSONTaskdel(JSONObject delcontact){
        this.delcontact=delcontact;
    }
    @Override
    protected String doInBackground(String... parms) {
        try {
            //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(parms[0]);                                     //url을 가져온다.
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");//POST방식으로 보냄
                con.setRequestProperty("Cache-Control", "no-cache");            //캐시 설정
                con.setRequestProperty("Content-Type", "application/json");     //application JSON 형식으로 전송

                con.setRequestProperty("Accept", "text/html");                  //서버에 response 데이터를 html로 받음
                con.setDoOutput(true);                                          //Outstream으로 post 데이터를 넘겨주겠다는 의미
                con.setDoInput(true);                                           //Inputstream으로 서버로부터 응답을 받겠다는 의미

                con.connect();          //연결 수행

                //서버로 보내기위해서 스트림 만듬
                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(delcontact.toString());
                writer.flush();
                writer.close();//버퍼를 받아줌


                //입력 스트림 생성
                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다
                StringBuffer buffer = new StringBuffer();                      //실제 데이터를 받는곳

                //line별 스트링을 받기 위한 temp 변수
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();

                //아래는 예외처리 부분이다.
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //종료가 되면 disconnect메소드를 호출한다.
                if (con != null) {
                    con.disconnect();
                }
                try {
                    //버퍼를 닫아준다.
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//finally 부분
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        tab1.convey(result);
    }
}