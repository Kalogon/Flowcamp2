package com.example.loginactivity;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

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



/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {

    private View v;
    TextView tvData;
    Context tab1;


    public tab1() {
        // Required empty public constructor
    }

    private void start() {
        RecyclerView rv = v.findViewById(R.id.contact);

        List<A_DATA> datas = new ArrayList<>();
        JSONObject jsonObject_each = new JSONObject();
        ContentResolver resolver = getContext().getContentResolver();
        tvData = (TextView)v.findViewById(R.id.textView);

        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String [] proj = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI};


        Cursor cursor = resolver.query(phoneUri, proj, null, null, null);
        String result="";
        int temp=0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(proj[0]));
                String name = cursor.getString(cursor.getColumnIndex(proj[1]));
                String tel = cursor.getString(cursor.getColumnIndex(proj[2]));
                String image = cursor.getString(cursor.getColumnIndex(proj[3]));

                A_DATA data = new A_DATA(id, name, tel, image);
                datas.add(data);





                result+=Integer.toString(data.getId())+data.getImage()+data.getName()+data.getTel();






                temp++;
            }
            try {
                jsonObject_each.accumulate("test",result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        mAdapter my_adapter = new mAdapter(getContext(), datas);

        rv.setAdapter(my_adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        new JSONTask(jsonObject_each).execute("http://192.249.19.254:8180/post");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_tab1, container, false);
        tab1 = container.getContext();

        FloatingActionButton btnadd = (FloatingActionButton) v.findViewById(R.id.Btn_addcon);
        FloatingActionButton btndel = (FloatingActionButton) v.findViewById(R.id.Btn_delcon);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View layout_add_contact = LayoutInflater.from(tab1).inflate(R.layout.layout_add_contact, null);
                new MaterialStyledDialog.Builder(tab1)
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(layout_add_contact)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText edt_register_name = (MaterialEditText) layout_add_contact.findViewById(R.id.edit_name);
                                MaterialEditText edt_register_number = (MaterialEditText) layout_add_contact.findViewById(R.id.edit_number);

                                if(TextUtils.isEmpty(edt_register_name.getText().toString())){
                                    Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }if(TextUtils.isEmpty(edt_register_number.getText().toString())){
                                    Toast.makeText(getActivity(), "number cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                addcontact(edt_register_name.getText().toString(),
                                        edt_register_number.getText().toString());

                            }
                        }).show();


            }
        });



        ArrayList<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS))
                Toast.makeText(getContext(), "We need your permission for reading your contacts.", Toast.LENGTH_SHORT).show();
            permissions.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
                Toast.makeText(getContext(), "We need your permission for calling.", Toast.LENGTH_SHORT).show();
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (permissions.size() > 0) {
            String[] per_array = new String[permissions.size()];
            per_array = permissions.toArray(per_array);
            requestPermissions(per_array, 1);
        }
        else
            start();
        return v;
    }


    public void addcontact (String name_edt, String number_edt){
        new JSONTaskAddcontact().execute("http://192.249.19.254:7180/addcontact",  name_edt, number_edt);
    }


    public class JSONTaskAddcontact extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... parms) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                String name = parms[1];
                String number= parms[2];
                jsonObject.accumulate("email", name);
                jsonObject.accumulate("password", number);

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
                    writer.write(jsonObject.toString());
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
            //tvData.setText(result);
            Toast.makeText(getActivity(), ""+result, Toast.LENGTH_SHORT).show();
        }
    }




    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start();
            }else if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                start();
            }else
                getActivity().finish();
        }
    }


    public class JSONTask extends AsyncTask<String, String, String>{
        JSONObject jsonObject_each = new JSONObject();
        public JSONTask(JSONObject jsonObject_each){
            this.jsonObject_each=jsonObject_each;
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
                    writer.write(jsonObject_each.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌r

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
            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
    }

}



