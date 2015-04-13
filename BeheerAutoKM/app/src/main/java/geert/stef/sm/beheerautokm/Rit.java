package geert.stef.sm.beheerautokm;

import java.util.Date;

/**
 * Created by Stef on 9-4-2015.
 */
public class Rit {

    int ritID;
    int carID;
    double distance;
    String driver;
    Date date;

    public Rit(int ritID, int carID, double distance) {
        this.ritID = ritID;
        this.carID = carID;
        this.distance = distance;
    }
}
