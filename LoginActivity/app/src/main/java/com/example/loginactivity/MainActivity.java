package com.example.loginactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private LoginButton btn_facebook_login;
    private LoginCallback mLoginCallback;
    private Button btn_custom_login;
    private CallbackManager mCallbackManager;
    String user_info = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();

        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SubActivity.class);
                startActivity(intent);

//                LoginManager loginManager = LoginManager.getInstance();
//                loginManager.logInWithReadPermissions(MainActivity.this,
//                        Arrays.asList("public_profile", "email"));
//                loginManager.registerCallback(mCallbackManager, mLoginCallback);
            }
        });


    }

    public static  boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }


    private class LoginCallback implements FacebookCallback<LoginResult> {
        public JSONObject jsonuser=new JSONObject();
        // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
        @Override
        public void onSuccess(LoginResult loginResult) {

            Log.e("Callback :: ", "onSuccess");
            requestMe(loginResult.getAccessToken());
        }


        // 로그인 창을 닫을 경우, 호출됩니다.
        @Override
        public void onCancel() {
            Log.e("Callback :: ", "onCancel");
        }


        // 로그인 실패 시에 호출됩니다.
        @Override
        public void onError(FacebookException error) {
            Log.e("Callback :: ", "onError : " + error.getMessage());
        }


        // 사용자 정보 요청
        public void requestMe(AccessToken token) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                    new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.e("result", object.toString());

                            String id="";
                            String name="";
                            try {
                                id=object.getString("id");
                                name=object.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonuser.accumulate("user_id", id);
                                jsonuser.accumulate("user_name", name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            new JSONTask(jsonuser, null).execute("http://192.249.19.254:7180/login");

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();

        }

        public class JSONTask extends AsyncTask<String, String, String>{
            JSONObject jsonObject_user = new JSONObject();
            JSONArray jsonarray = new JSONArray();


            public JSONTask(JSONObject jsonObject_user, JSONArray jsonarray) {
                this.jsonarray = jsonarray;
                this.jsonObject_user = jsonObject_user;
            }

            @Override
            protected String doInBackground(String... urls) {
                try {
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
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
                        writer.write(jsonObject_user.toString());
                        writer.flush();
                        writer.close();//버퍼를 받아줌

                        //서버로 부터 데이터를 받음
                        InputStream stream = con.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(stream));

                        StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }

                        return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
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
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                user_info = result;
                intent.putExtra("user", user_info);
                startActivity(intent);
                finish();
            }
        }
    }
}

