package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Car implements Parcelable {
    private String name;
    private int buildyear;
    private String licensePlate;
    private List<Rit> ritten;

    public Car(String name, int buildyear, String licensePlate)
    {
        this.name = name;
        this.buildyear = buildyear;
        this.licensePlate = licensePlate;
        ritten = new ArrayList<>();
    }

    public Car(Parcel read){
        this.name = read.readString();
        this.buildyear = read.readInt();
        this.licensePlate = read.readString();
    }

    public static final Parcelable.Creator<Car> CREATOR =
            new Parcelable.Creator<Car>(){

                @Override
                public Car createFromParcel(Parcel source) {
                    return new Car(source);
                }

                @Override
                public Car[] newArray(int size) {
                    return new Car[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(name);
        arg0.writeInt(buildyear);
        arg0.writeString(licensePlate);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
