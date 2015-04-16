package geert.stef.sm.beheerautokm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Geert on 13-4-2015.
 */
public class Driver implements Parcelable {
    private String name;
    private String password;
    private String username;

    public Driver(String username, String password, String name)
    {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Driver(String username, String name)
    {
        this.username = username;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Driver(Parcel read){
        this.username = read.readString();
        this.password = read.readString();
        this.name = read.readString();
    }

    public static final Parcelable.Creator<Driver> CREATOR =
            new Parcelable.Creator<Driver>(){

                @Override
                public Driver createFromParcel(Parcel source) {
                    return new Driver(source);
                }

                @Override
                public Driver[] newArray(int size) {
                    return new Driver[size];
                }
            };

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(username);
        arg0.writeString(password);
        arg0.writeString(name);
    }

    @Override
    public String toString() {
        return username + " " + password + " " +name;
    }
}
