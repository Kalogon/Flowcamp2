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



/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {

    private View v;
    TextView tvData;
    JSONObject user;
    /*private String id=SubActivity.user_id;*/
    static Context tab1;
    /*private String name=SubActivity.user_name;*/
    static List<A_DATA> datas = new ArrayList<>();
    static RecyclerView rv;
    String userid="";

    private void start() throws JSONException {
        rv= v.findViewById(R.id.contact);
        user=SubActivity.user;
        JSONArray temp = new JSONArray();
        datas = new ArrayList<>();
        userid = user.getString("userid");
        if(SubActivity.first){

            JSONObject jsonObject_user = new JSONObject();
            JSONArray jsonarray=new JSONArray();
            jsonObject_user.accumulate("user_id",user.getString("userid"));
            jsonObject_user.accumulate("user_name",user.getString("username"));
            SubActivity.first=false;
            jsonarray.put(jsonObject_user);
            ContentResolver resolver = getContext().getContentResolver();

            Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            String [] proj = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI};


            Cursor cursor = resolver.query(phoneUri, proj, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(proj[0]));
                    String name = cursor.getString(cursor.getColumnIndex(proj[1]));
                    String tel = cursor.getString(cursor.getColumnIndex(proj[2]));
                    String image = cursor.getString(cursor.getColumnIndex(proj[3]));

                    JSONObject temp2=new JSONObject();
                    temp2.accumulate("friend_number",tel);
                    temp2.accumulate("friend_name",name);
                    jsonarray.put(temp2);
                }
            }
            cursor.close();
            new JSONTask(jsonObject_user,jsonarray).execute("http://192.249.19.254:7180/phonePost");
        }
        else{
            convey(userid);
        }
        System.out.println(datas);
    }

    public static void convey(String id_User){
        new JSONTaskView(id_User).execute("http://192.249.19.254:7180/view");
    }

    public static void viewPhone(JSONArray friends) throws JSONException {
        datas = new ArrayList<>();
        for(int i=0;i<friends.length();i++){
            String element1=friends.getJSONObject(i).getString("friend");
            String element2=friends.getJSONObject(i).getString("phonenumber");
            A_DATA data = new A_DATA(1, element1, element2, null);
            datas.add(data);
        }
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
                                String temtem="";
                                try {
                                    temtem=user.getString("userid");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    addcontact(edt_register_name.getText().toString(),
                                            edt_register_number.getText().toString(),temtem);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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
        else {
            try {
                start();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }


    public void addcontact (String name_edt, String number_edt,String temp) throws JSONException {
        JSONObject addcontact=new JSONObject();
        addcontact.accumulate("user_id",temp);
        addcontact.accumulate("friend_name",name_edt);
        addcontact.accumulate("friend_number",number_edt);
        new JSONTaskAddcontact(addcontact).execute("http://192.249.19.254:7180/addcontact",  name_edt, number_edt);
    }



    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                try {
                    start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else
                getActivity().finish();
        }
    }


}



