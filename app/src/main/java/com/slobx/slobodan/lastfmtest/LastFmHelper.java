package com.slobx.slobodan.lastfmtest;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Slobodan on 8/3/2015.
 */
public class LastFmHelper {

    private static final String TOP_TRACKS_LASTFM_API_TAB1 =
            "http://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user=%s&period=overall&api_key=1327212cf60894a15138c01b3142c224&format=json";
    private static final String TOP_TRACKS_LASTFM_API_TAB2 =
            "http://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user=%s&period=12months&api_key=1327212cf60894a15138c01b3142c224&format=json";
    private static final String TOP_TRACKS_LASTFM_API_TAB3 =
            "http://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user=%s&period=6months&api_key=1327212cf60894a15138c01b3142c224&format=json";


    public static JSONObject getJSON(Context context, String username, int index) {
        try {

            String fetchUrl = null;
            switch (index) {
                case 1:
                    fetchUrl = String.format(TOP_TRACKS_LASTFM_API_TAB1, username);
                    break;
                case 2:
                    fetchUrl = String.format(TOP_TRACKS_LASTFM_API_TAB2, username);
                    break;
                case 3:
                    fetchUrl = String.format(TOP_TRACKS_LASTFM_API_TAB3, username);
                    break;
                default:
                    fetchUrl = String.format(TOP_TRACKS_LASTFM_API_TAB1, username);

            }
            URL url = new URL(fetchUrl);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());


            if (data.length() == 0) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }

}
