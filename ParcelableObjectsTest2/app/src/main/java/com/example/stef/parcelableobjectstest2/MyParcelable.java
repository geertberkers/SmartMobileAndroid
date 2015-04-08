package com.example.stef.parcelableobjectstest2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stef on 5-4-2015.
 */
public class MyParcelable implements Parcelable {

    private List<MyListClass> arrList = new ArrayList<MyListClass>();
    private int myInt = 0;
    private String str = null;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<MyListClass> getArrList() {
        return arrList;
    }

    public void setArrList(List<MyListClass> arrList) {
        this.arrList = arrList;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    MyParcelable() {
        // initialization
        arrList = new ArrayList<MyListClass>();
    }

    public MyParcelable(Parcel in) {
        myInt = in.readInt();
        str = in.readString();
        in.readTypedList(arrList, MyListClass.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeInt(myInt);
        outParcel.writeString(str);
        outParcel.writeTypedList(arrList);
    }

    public static final Parcelable.Creator<MyParcelable> CREATOR =
            new Parcelable.Creator<MyParcelable>() {

        @Override
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        @Override
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };
}
