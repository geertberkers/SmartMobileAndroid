package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Manager implements Parcelable{

    private ArrayList<Car> cars;

    public Manager()
    {
        cars = new ArrayList<>();
        cars.add(new Car("Kia Sportage", 2012, "XF-HJ-99"));
        cars.add(new Car("Peugot 306", 2012, "AG-RT-46"));
    }

    public Manager(Parcel in)
    {
        String[] data = new String[1];
        in.readStringArray(data);
    }

    public boolean Login(Context c, String username, String password){
        if(username.equals("henk") && password.equals("hallo"))
        {
            String[] carNamesStrings = new String[cars.size()];
            for(int i = 0; i < cars.size(); i++)
            {
                carNamesStrings[i] = cars.get(i).getName();
            }
            Intent intent = new Intent(c, Overview.class);
            intent.putExtra("items_to_parse", carNamesStrings);
            c.startActivity(intent);
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
    public void writeToParcel(Parcel dest, int flags) {

    }
}
