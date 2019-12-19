package org.httpsknighthacks.knighthacksandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Timestamp implements Parcelable {

    private long seconds;
    private int nanoseconds;

    public Timestamp() {}

    public Timestamp(long seconds, int nanoseconds) {
        this.setSeconds(seconds);
        this.setNanoseconds(nanoseconds);
    }

    public Timestamp(Date date) {

    }

    protected Timestamp(Parcel in) {
        setSeconds(in.readLong());
        setNanoseconds(in.readInt());
    }

    public static final Creator<Timestamp> CREATOR = new Creator<Timestamp>() {
        @Override
        public Timestamp createFromParcel(Parcel in) {
            return new Timestamp(in);
        }

        @Override
        public Timestamp[] newArray(int size) {
            return new Timestamp[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getSeconds());
        dest.writeInt(getNanoseconds());
    }

    public Date toDate() {
        return new Date(seconds * 1000L);
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(int nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
