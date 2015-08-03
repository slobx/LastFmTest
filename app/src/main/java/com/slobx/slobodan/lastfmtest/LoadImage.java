package com.slobx.slobodan.lastfmtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Slobodan on 8/3/2015.
 */
public class LoadImage {


    public static Bitmap getBitmap(String imageURL) {
        URL url = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            bitmap  = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }




}
