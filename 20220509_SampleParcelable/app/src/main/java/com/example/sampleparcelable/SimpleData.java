package com.example.sampleparcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable{

    int number;
    String message;

    public SimpleData( int num, String msg) {
        number = num;
        message = msg;
    }

    protected SimpleData(Parcel src) {
        number = src.readInt();
        message = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        @Override
        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) { // Parcel 객체 쓰기
        parcel.writeInt(number); // SimpleData 객체 안에 들어있는 데이터를 Parcel 객체로 만드는 역할
        parcel.writeString(message);
    }
}
