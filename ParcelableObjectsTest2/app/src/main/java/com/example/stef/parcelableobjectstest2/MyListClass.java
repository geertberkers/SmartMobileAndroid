package com.example.stef.parcelableobjectstest2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stef on 5-4-2015.
 */
public class MyListClass implements Parcelable {

    private int test;

    public MyListClass()
    {}

    public MyListClass(Parcel read){
        test = read.readInt();
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public static final Parcelable.Creator<MyListClass> CREATOR =
            new Parcelable.Creator<MyListClass>() {

                @Override
                public MyListClass createFromParcel(Parcel source) {
                    return new MyListClass(source);
                }

                @Override
                public MyListClass[] newArray(int size) {
                    return new MyListClass[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(test);
    }
}