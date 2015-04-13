package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.internal.pa;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Parcelable{

    private int myInt = 0;
    private List<Car> cars;
    private List<Driver> drivers;
    private Driver loggedInDriver;

    public Manager() {
        cars = new ArrayList<>();
    }

    public List<Car> getCars(){return this.cars;}
    public void setCars(List<Car> cars){this.cars = cars;}
    public List<Driver> getDrivers(){return this.drivers;}
    public void setDrivers(List<Driver> drivers) {this.drivers = drivers;}
    public int getMyInt(){return this.myInt;}
    public void setMyInt(int myInt){this.myInt = myInt;}

    public Manager(Parcel in){
        myInt = in.readInt();
        cars = new ArrayList<>();
        drivers = new ArrayList<>();
        in.readTypedList(cars, Car.CREATOR);
        in.readTypedList(drivers, Driver.CREATOR);
    }

    public boolean Login(Context c, String username, String password){
        boolean correct = false;
        for(Driver d : drivers) {
            if ((d.getUsername().equals(username)) && (d.getPassword().equals(password))) {
                correct = true;
                this.loggedInDriver = d;
            }
        }
        return correct;
    }

    public void Register(Context c, String username, String password, String name)
    {
        drivers.add(new Driver(username,password,name));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeInt(myInt);
        outParcel.writeTypedList(cars);
        outParcel.writeTypedList(drivers);
    }

    public static final Parcelable.Creator<Manager> CREATOR
            = new Parcelable.Creator<Manager>(){

        @Override
        public Manager createFromParcel(Parcel in) {
            return new Manager(in);
        }

        @Override
        public Manager[] newArray(int size) {
            return new Manager[size];
        }
    };

    public void addCar(Car car) {
        if (car != null) {
            this.cars.add(car);
        }
    }
}
