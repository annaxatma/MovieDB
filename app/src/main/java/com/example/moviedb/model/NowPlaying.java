package com.example.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NowPlaying implements Parcelable {
    protected NowPlaying(Parcel in) {
    }

    public static final Creator<NowPlaying> CREATOR = new Creator<NowPlaying>() {
        @Override
        public NowPlaying createFromParcel(Parcel in) {
            return new NowPlaying(in);
        }

        @Override
        public NowPlaying[] newArray(int size) {
            return new NowPlaying[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
