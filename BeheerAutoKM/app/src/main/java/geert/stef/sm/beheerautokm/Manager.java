package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Parcelable{

    private int myInt = 0;
    private List<Car> cars;

    public Manager() {
        cars = new ArrayList<>();
    }

    public List<Car> getCars(){
        return this.cars;
    }
    public void setCars(List<Car> cars){this.cars = cars;}
    public int getMyInt(){return this.myInt;}
    public void setMyInt(int myInt){this.myInt = myInt;}

    public Manager(Parcel in){
        myInt = in.readInt();
        in.readTypedList(cars, Car.CREATOR);
    }

    public boolean Login(Context c, String username, String password){
        if(username.equals("henk") && password.equals("hallo"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeInt(myInt);
        outParcel.writeTypedList(cars);
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
