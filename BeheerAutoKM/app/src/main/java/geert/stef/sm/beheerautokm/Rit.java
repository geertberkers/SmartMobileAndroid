package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        this.date = parseDate(read.readString());
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        arg0.writeString(sdf.format(date));
    }
    public int getRitID() {
        return ritID;
    }

    public void setRitID(int ritID) {
        this.ritID = ritID;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Driver getDriver()
    {
        return this.driver;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {/*
        System.out.println(ritID);
        System.out.println(car);
        System.out.println(distance);
        System.out.println(driver);
        System.out.println(date);*/
        return super.toString();
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd-mm-yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
