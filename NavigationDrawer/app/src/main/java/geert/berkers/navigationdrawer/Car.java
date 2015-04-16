package geert.berkers.navigationdrawer;

/**
 * Created by Geert on 13-4-2015.
 */
public class Car{
    private String carName;
    private int image;
    private boolean favorite;

    public Car(String car, int image){
        this.carName = car;
        this.image = image;
        this.favorite = false;
        if(car.equals("Peugeot 206"))
        {
            this.favorite = true;
        }
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

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}