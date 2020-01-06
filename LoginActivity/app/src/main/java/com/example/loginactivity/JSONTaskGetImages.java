package com.example.loginactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;

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
import java.net.URL;

public class JSONTaskGetImages extends AsyncTask<String, String, String> {
    String userid;

    public JSONTaskGetImages(String userid) {
        this.userid = userid;
    }

    @Override
    protected String doInBackground(String... parms) {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", userid);
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try{
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
                while((line = reader.readLine()) != null)
                    buffer.append(line);


                return buffer.toString();
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                if (con != null)
                    con.disconnect();
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) { e.printStackTrace(); }
            }
        } catch(Exception e){ e.printStackTrace(); }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        JSONArray images=new JSONArray();
        try {
            images=new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<images.length();i++){
            JSONObject temp = new JSONObject();
            String image = "";
            try {
                temp = images.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                image = temp.getString("photoname");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new tab2.JSONTaskUrl(images).execute("http://192.249.19.254:7180/urlsGet", userid, image );


        }

    }

}








