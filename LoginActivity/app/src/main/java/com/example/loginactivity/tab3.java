package com.example.loginactivity;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class tab3 extends Fragment {

    TextView tvData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);
        tvData = (TextView)v.findViewById(R.id.textView);

        Button btn = (Button)v.findViewById(R.id.httpTest);

        //버튼이 눌리면 아래 리스너가 수행된다.

        btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                //아래 링크를 파라미터를 넘겨준다는 의미.

                new JSONTask().execute("http://192.249.19.254:8180/post");

            }

        });
        return v;
    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override

        protected String doInBackground(String... urls) {

            try {

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("user_id", "androidTest");

                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;

                BufferedReader reader = null;

                try{

                    //URL url = new URL(“http://192.168.25.16:3000/users“);

                    URL url = new URL(urls[0]);//url을 가져온다.

                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미


                    con.connect();//연결 수행
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //입력 스트림 생성

                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.

                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳

                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수

                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.

                    while((line = reader.readLine()) != null){

                        buffer.append(line);

                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String… urls) 니까

                    return buffer.toString();

                    //아래는 예외처리 부분이다.

                } catch (MalformedURLException e){

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    //종료가 되면 disconnect메소드를 호출한다.

                    if(con != null){

                        con.disconnect();

                    }

                    try {

                        //버퍼를 닫아준다.

                        if(reader != null){

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

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            tvData.setText(result);

        }

    }

}

