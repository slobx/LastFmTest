package com.slobx.slobodan.lastfmtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Slobodan on 8/3/2015.
 */
public class Tab1 extends Fragment {

    ImageView imageArtist;
    TextView textArtist;
    TextView textTrack;
    ListView listView;
    String imageArtistString;
    private ProgressDialog progDialog;
    View v;
    Handler handler;
    ArrayList<TopTrackList> topTrackListFinal = new ArrayList<>();

    public Tab1() {
        handler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab1, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new JSONParse().execute("rj");
    }


    private void renderList(JSONObject json) {
        try {

            JSONObject obj1 = json.getJSONObject("toptracks");
            JSONArray array1 = obj1.getJSONArray("track");
            for (int i = 0; i < array1.length(); i++) {
                JSONObject JSONobj1 = array1.getJSONObject(i);

                String track = JSONobj1.getString("name");
                String url = JSONobj1.getString("url");

                JSONObject artistObject = JSONobj1.getJSONObject("artist");
                String artist = artistObject.getString("name");

                JSONArray imageArray = JSONobj1.getJSONArray("image");
                JSONObject imageObject = imageArray.getJSONObject(1);
                imageArtistString = imageObject.getString("#text");





                TopTrackList topTrackList = new TopTrackList(imageArtistString, track, artist, url);
                topTrackListFinal.add(topTrackList);
            }
        } catch (JSONException e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }


    }

    class MyAdapter extends BaseAdapter {

        ArrayList<TopTrackList> topTrackList = new ArrayList<>();

        public MyAdapter(Context context, ArrayList<TopTrackList> topTrackList) {
            this.topTrackList = topTrackList;
        }

        @Override
        public int getCount() {
            return topTrackList.size();
        }

        @Override
        public Object getItem(int position) {
            return topTrackList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return topTrackList.indexOf(topTrackList.get(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.row_tabs, null);

            TopTrackList topTrack = topTrackList.get(position);

            imageArtist = (ImageView) v.findViewById(R.id.image_list);
            textArtist = (TextView) v.findViewById(R.id.text_artist);
            textTrack = (TextView) v.findViewById(R.id.text_track);

            textArtist.setText(topTrack.getArtist());
            textTrack.setText(topTrack.getTrack());

            return v;
        }
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = ProgressDialog.show(getActivity(), "Search", "Loading...",true, false);


        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json = LastFmHelper.getJSON(getActivity(), args[0], 1);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            progDialog.dismiss();
            renderList(json);

            listView = (ListView) v.findViewById(R.id.my_list_tab1);
            MyAdapter adapter = new MyAdapter(getActivity(), topTrackListFinal);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    String url = topTrackListFinal.get(position).getUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });


        }

    }
}
