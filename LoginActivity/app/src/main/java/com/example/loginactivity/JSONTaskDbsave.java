package com.example.loginactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

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

public class JSONTaskDbsave extends AsyncTask<String, String, String> {
    String userid;
    String image_name;

    public JSONTaskDbsave(String userid, String image_name) {
        this.userid = userid;
        this.image_name = image_name;
    }

    @Override
    protected String doInBackground(String... parms) {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", userid);
            jsonObject.accumulate("photo_name", image_name);
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
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
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
}
