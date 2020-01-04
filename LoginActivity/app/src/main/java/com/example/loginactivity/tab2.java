package com.example.loginactivity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {

    Context tab2;
    static final int REQUEST_PERMISSION_KEY = 1;
    GridView galleryGridView;
    View view;
    private boolean is_delete;
    private ArrayList<Bitmap> im_array;
    private ImageAdapter imageAdapter;
    private String[] images;
    private String username = SubActivity.username;

    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        tab2 = container.getContext();
        GridView gv = v.findViewById(R.id.grid_view);

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if(!MainActivity.hasPermissions(tab2.getApplicationContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_PERMISSION_KEY);
        }


        new JSONTaskUrl().execute("http://192.249.19.254:8180/urlsGet", username);



        FloatingActionButton btnCamera = (FloatingActionButton) v.findViewById(R.id.Btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,1);
            }
        });



        FloatingActionButton btnRefresh = (FloatingActionButton) v.findViewById(R.id.Btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new JSONTaskUrl().execute("http://192.249.19.254:8180/urlsGet", username);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public class JSONTaskUrl extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... parms) {
            try {
                JSONObject jsonObject = new JSONObject();
                String username = parms[1];
                jsonObject.accumulate("name", username);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(parms[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");

                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream outStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null)
                        buffer.append(line);


                    return buffer.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null)
                        con.disconnect();
                    try {
                        if (reader != null)
                            reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }//       @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            if (result.equals("\"No images\"")){
//                return;
//            }else{
//                try{
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONArray jsonArray = jsonObject.getJSONArray("image_urls");
//
//                    images = new String[jsonArray.length()];
//                    for(int i=0; i<jsonArray.length(); i++)
//                        images[i] = jsonArray.get(i).toString();
//
//                    galleryGridView = (GridView) view.findViewById(R.id.grid_view);
//                    ImageAdapter imageGridAdapter = new ImageAdapter(tab2, images);
//                    galleryGridView.setAdapter(imageGridAdapter);
//
//                }catch (Exception e){ e.printStackTrace(); }
//
//            }
//        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=0) {
            if (requestCode == 1 && !data.equals(null)) {
                try {
                    final Bitmap newImage = (Bitmap) data.getExtras().get("data");
                    final View layout_camera = LayoutInflater.from(tab2).inflate(R.layout.layout_camera, null);
                    new MaterialStyledDialog.Builder(tab2)
                            .setIcon(R.drawable.ic_launcher_foreground)
                            .setTitle("Add Image to server")
                            .setCustomView(layout_camera)
                            .setNegativeText("CANCEL")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveText("ADD")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    MaterialEditText edt_image_name = (MaterialEditText) layout_camera.findViewById(R.id.edit_image);

                                    if(TextUtils.isEmpty(edt_image_name.getText().toString())){
                                        Toast.makeText(tab2, "Please set image name", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String newImage_string = BitmapToString(newImage);

                                    uploadImage(newImage_string, edt_image_name.getText().toString());
                                }
                            }).show();

                } catch (Exception e) {e.printStackTrace();}
            }
        }
        return;
    }

    public void uploadImage (String newImage_string, String image_name){
        new JSONTaskUpload().execute("http://192.249.19.254:8180/imagePost", newImage_string, image_name);
    }

    public class JSONTaskUpload extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... parms) {
            try{
                Bitmap newImage = StringToBitmap(parms[1]);
                String image_name = parms[2]+".png"+"$"+username;
                FileOutputStream fileOutStream = null;
                DataOutputStream outputStream = null;
                InputStream inputStream = null;
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                byte[] decodedByteArray = Base64.decode(parms[1], Base64.NO_WRAP);

                File outputFile = new File(tab2.getCacheDir(), image_name);
                fileOutStream = new FileOutputStream(outputFile);
                fileOutStream.write(decodedByteArray);
                fileOutStream.flush();
                fileOutStream.close();

                FileInputStream fileInputStream = new FileInputStream(outputFile);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(parms[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                    con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    outputStream = new DataOutputStream(con.getOutputStream());

                    outputStream.writeBytes("--" + boundary + "\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + image_name + "\"" + "\r\n");
                    outputStream.writeBytes("Content-Type: image/png" + "\r\n");
                    outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
                    outputStream.writeBytes("\r\n");
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, 1048576);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        outputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, 1048576);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    outputStream.writeBytes("\r\n");
                    outputStream.writeBytes("--" + boundary + "--" + "\r\n");

                    inputStream = con.getInputStream();
                    int status = con.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null)
                            response.append(inputLine);
                        inputStream.close();
                        con.disconnect();
                        fileInputStream.close();
                        outputStream.flush();
                        outputStream.close();
                    }
                    con.disconnect();
                    outputStream.flush();
                    outputStream.close();



                } catch(Exception e){ e.printStackTrace(); }

            } catch(Exception e){ e.printStackTrace(); }

            return null;
        }


    }

    private Bitmap StringToBitmap(String string) {
        byte [] bytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }



}
