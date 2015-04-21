package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Stef on 9-4-2015.
 */
public class Rit implements Parcelable {

    private int ritID;
    private String car;
    private double distance;
    private Driver driver;
    private Date date;

    public Rit(int ritID, String carID, double distance, String driver) {
        this.ritID = ritID;
        this.car = carID;
        this.distance = distance;
        this.driver = new Driver(driver);
    }

    public Rit(int ritID, String carID, double distance, String driver, Date date) {
        this.ritID = ritID;
        this.car = carID;
        this.distance = distance;
        this.date = date;
        this.driver = new Driver(driver);
    }

    public Rit(Parcel read){
        this.ritID = read.readInt();
        this.car = read.readString();
        this.distance = read.readDouble();
        this.driver = new Driver(read.readString(),read.readString(),read.readString());
        //this.date = new Date(....);
    }

    public static final Parcelable.Creator<Rit> CREATOR =
            new Parcelable.Creator<Rit>(){

                @Override
                public Rit createFromParcel(Parcel source) {
                    return new Rit(source);
                }

                @Override
                public Rit[] newArray(int size) {
                    return new Rit[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(ritID);
        arg0.writeString(car);
        arg0.writeDouble(distance);
        arg0.writeString(driver.getUsername());
        arg0.writeString(driver.getPassword());
        arg0.writeString(driver.getUsername());
        //arg0.writeDate...
    }

    public String getCar()
    {
        return this.car;
    }

    public double getDistance()
    {
        return this.distance;
    }

    public Driver getDriver()
    {
        return this.driver;
    }

    public Date getDate()
    {
        return this.date;
    }

}
