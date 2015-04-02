package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String name;
    private int buildyear;
    private String licensePlate;

    public Car(String name, int buildyear, String licensePlate)
    {
        this.name = name;
        this.buildyear = buildyear;
        this.licensePlate = licensePlate;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
