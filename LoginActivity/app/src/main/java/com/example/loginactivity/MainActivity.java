package com.example.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private LoginButton btn_facebook_login;
    private LoginCallback mLoginCallback;
    private Button btn_custom_login;
    private CallbackManager mCallbackManager;


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
                            Log.e("result",object.toString());
                            Intent intent=new Intent(getApplicationContext(),SubActivity.class);
                            try {
                                intent.putExtra("user_id",object.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            finish();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();

        }

    }
}

