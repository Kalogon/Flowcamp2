package com.example.loginactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.net.URL;

public class JSONTest extends AsyncTask<String, String, String> {
    Bitmap bitmap;
    @Override
    protected String doInBackground(String... parms) {
        try{

            JSONObject jsonObject = new JSONObject();
            String userid = parms[1];
            String photoname = parms[2];
            jsonObject.accumulate("user_id", userid);
            jsonObject.accumulate("photo_name", photoname);
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
                bitmap = BitmapFactory.decodeStream(stream);
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


    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result.equals("\"No images\"")){
            return;
        }else{
            try{
                JSONObject jsonObject = new JSONObject(result);
                    /*JSONArray jsonArray = jsonObject.getJSONArray("image_urls");

                    images = new String[jsonArray.length()];
                    for(int i=0; i<jsonArray.length(); i++)
                        images[i] = jsonArray.get(i).toString();

                    galleryGridView = (GridView) view.findViewById(R.id.grid_view);
                    ImageAdapter adapter = new ImageAdapter(tab2, images);
                    galleryGridView.setAdapter(adapter);*/

            }catch (Exception e){ e.printStackTrace(); }

        }
    }

}