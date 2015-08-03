package com.slobx.slobodan.lastfmtest;

import android.graphics.Bitmap;

/**
 * Created by Slobodan on 8/3/2015.
 */
public class TopTrackList {


    String imageTrackList;
    String track;
    String artist;
    String url;

    public TopTrackList(String imageTrackList, String track, String artist, String url){
        this.imageTrackList = imageTrackList;
        this.track = track;
        this.artist = artist;
        this.url = url;
    }

    public String getImageTrackList() {
        return imageTrackList;
    }

    public void setImageTrackList(String imageTrackList) {
        this.imageTrackList = imageTrackList;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
