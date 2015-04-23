package geert.stef.sm.beheerautokm;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;


public class Car implements Parcelable {
    private String name;
    private CarBrand brand;
    private int image;
    private int year;
    private String fuel;
    private int horsepower;
    private double mileage;
    private double KMTank;
    private double KMDriven;
    private String licensePlate;
    private Driver owner;
    private boolean favorite;
    private List<Rit> ritten;
    private Bitmap localImage;

    public Car(String name,CarBrand brand,  int image, int year, String fuel, int horsepower, double mileage, double KMTank, double KMDriven, String licensePlate, Driver owner){
        this.name = name;
        this.brand = brand;
        this.image = image;
        this.year = year;
        this.fuel = fuel;
        this.horsepower = horsepower;
        this.mileage = mileage;
        this.KMTank = KMTank;
        this.KMDriven = KMDriven;
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.favorite = false;
        ritten = new ArrayList<>();
    }

    public Car(Parcel read){
        this.name = read.readString();
        String carBrand = read.readString();
        for(CarBrand cb : CarBrand.values())
        {
            if(cb.toString().equals(carBrand)){
                this.brand = cb;
            }
        }
        this.image = read.readInt();
        this.year = read.readInt();
        this.fuel = read.readString();
        this.horsepower = read.readInt();
        this.mileage = read.readDouble();
        this.KMTank = read.readDouble();
        this.KMDriven = read.readDouble();
        this.licensePlate = read.readString();
        this.owner = new Driver(read.readString(),read.readString(),read.readString());
        this.favorite = (read.readInt() == 0) ? false : true;
        ritten = new ArrayList<>();
        read.readTypedList(ritten, Rit.CREATOR);
    }

    public static final Parcelable.Creator<Car> CREATOR =
            new Parcelable.Creator<Car>(){

                @Override
                public Car createFromParcel(Parcel source) {
                    return new Car(source);
                }

                @Override
                public Car[] newArray(int size) {
                    return new Car[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(name);
        for(CarBrand cb : CarBrand.values())
        {
            if(cb.toString().equals(brand.toString())){
                arg0.writeString(brand.toString());
            }
        }
        arg0.writeInt(image);
        arg0.writeInt(year);
        arg0.writeString(fuel);
        arg0.writeInt(horsepower);
        arg0.writeDouble(mileage);
        arg0.writeDouble(KMTank);
        arg0.writeDouble(KMDriven);
        arg0.writeString(licensePlate);
        arg0.writeString(owner.getUsername());
        arg0.writeString(owner.getPassword());
        arg0.writeString(owner.getName());
        arg0.writeInt((favorite ? 1 : 0));
        arg0.writeTypedList(ritten);

    //    arg0.writeString(owner);
    }

    public String getCar() {return name; }

    public void setCar(String name) {
        this.name = name;
    }

    public int getYear() {return year; }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Driver getOwner() {
        return owner;
    }

    public void getOwner(Driver owner) {
        this.owner = owner;
    }

    public int getImage(){
        return image;
    }
    @Override
    public String toString() {
        return name;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public Bitmap getLocalImage() {
        return localImage;
    }

    public void setLocalImage(Bitmap localImage) {
        this.localImage = localImage;
    }

    public double getKMTank() {
        return KMTank;
    }

    public void setKMTank(double KMTank) {
        this.KMTank = KMTank;
    }

    public double getKMDriven() {
        return KMDriven;
    }

    public void setKMDriven(double KMDriven) {
        this.KMDriven = KMDriven;
    }
}
