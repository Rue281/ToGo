package com.bricksai.togo.utiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Remonda on 5/3/2017.
 */

public class ImageDownloader {
    public Bitmap downloadImage(String imgPath)
    {
        //download Image
        Bitmap bitmap = null;

        try{
            String URLPath = "http://bricksai.16mb.com/img/"+URLEncoder.encode(imgPath, "utf-8");
            InputStream in =null;
            int responseCode = -1;
            URL url = new URL(URLPath);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                //download
                in = con.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
                in.close();
            }

        }
        catch(Exception ex){
            Log.e("Exception",ex.toString());
        }
        return bitmap;
    }
}
