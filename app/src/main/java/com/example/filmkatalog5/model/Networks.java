package com.example.filmkatalog5.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Networks implements Parcelable {
    public static final Creator<Networks> CREATOR = new Creator<Networks>() {
        @Override
        public Networks createFromParcel(Parcel in) {
            return new Networks(in);
        }

        @Override
        public Networks[] newArray(int size) {
            return new Networks[size];
        }
    };
    @SerializedName("id")
    private int netIds;
    @SerializedName("name")
    private String netName;

    protected Networks(Parcel in) {
        netIds = in.readInt();
        netName = in.readString();
    }

    public Networks() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(netIds);
        dest.writeString(netName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getNetIds() {
        return netIds;
    }

    public void setNetIds(int netIds) {
        this.netIds = netIds;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }
}
