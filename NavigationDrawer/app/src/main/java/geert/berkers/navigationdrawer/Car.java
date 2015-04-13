package geert.berkers.navigationdrawer;

/**
 * Created by Geert on 13-4-2015.
 */
public class Car{
    private String carName;
    private int image;

    public Car(String car, int image){
        this.carName = car;
        this.image = image;
    }

    public String getCarName(){
        return carName;
    }

    public int getImage(){
        return image;
    }
    @Override
    public String toString() {
        return carName;
    }
}