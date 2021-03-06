package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Manager implements Parcelable {

    private int myInt = 0;
    private Driver loggedIn;
    private List<Car> cars;
    private List<Driver> drivers;
    private List<Rit> ritten;

    public Manager() {
        cars = new ArrayList<>();
        drivers = new ArrayList<>();
        ritten = new ArrayList<>();
    }

    public List<Car> getCars() {
        return this.cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Driver> getDrivers() {
        return this.drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public int getMyInt() {
        return this.myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public Manager(Parcel in) {
        myInt = in.readInt();
        cars = new ArrayList<>();
        drivers = new ArrayList<>();
        ritten = new ArrayList<>();
        in.readTypedList(cars, Car.CREATOR);
        in.readTypedList(drivers, Driver.CREATOR);
        in.readTypedList(ritten, Rit.CREATOR);
        loggedIn =in.readParcelable(getClass().getClassLoader());
        //loggedIn = in.readParcelable( Driver.class.getClassLoader());
    }

    public Driver Login(String username, String password) {
        LogInTask loginTask = new LogInTask();
        String t = "";
        try {
            Object o = loginTask.execute(username, password).get();
            t = o.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // String json = o.toString();
        //String json2 =  loginTask.test();
        return parseJson(t);
        //return null;
    }

    public Driver loginLocal(String username, String password) {
        for (Driver d : drivers) {
            if (d.getUsername().equals(username) && d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;
    }

    public Driver parseJson(String json) {
        try {
            JSONArray jArray = new JSONArray(json);

            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String username = oneObject.getString("Username");
                    String name = oneObject.getString("Name");
                    loggedIn = new Driver(username, name);
                    return loggedIn;
                } catch (JSONException e) {
                    // Failed
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Register(String username, String password, String name) {
        drivers.add(new Driver(username, password, name));
        new AddDriverTask().execute(username, password, name);
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
        outParcel.writeTypedList(ritten);
        outParcel.writeParcelable(loggedIn, flags);
    }

    public static final Parcelable.Creator<Manager> CREATOR
            = new Parcelable.Creator<Manager>() {

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
            //TODO
            //IN DATABASE AANMAKEN
        }
    }

    public Driver getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Driver loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void logOff() {
        this.loggedIn = null;
    }

    public List<Rit> getRitten() {
        return ritten;
    }

    public void setRitten(List<Rit> ritten) {
        this.ritten = ritten;
    }

    public void addRit(int ritID, String carID, double distance, String driver, Date date){
      //  for(Driver d : drivers){
      //      if(d.getUsername().equals(driver)){
                ritten.add(new Rit(ritID, carID, distance, driver, date));
      //      }
      //  }
    }
}
