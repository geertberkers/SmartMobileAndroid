package geert.stef.sm.beheerautokm;

/**
 * Created by Geert on 16-4-2015.
 */
public enum CarBrand {

    AUDI("Audi",0),
    BMW("Male", 1),
    OPEL("Opel",2),
    PEUGEOT("Peugeot",3),
    VW("Volkswagen", 4),
    UNKNOWN("Unknown", 5);

    private String stringValue;
    private int intValue;

    CarBrand(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int toInt() {
        return intValue;
    }

    public CarBrand getBrand(String brand){
        for(CarBrand cb : CarBrand.values())
        {
            if(cb.toString().equals(brand)){
                return cb;
            }
        }
        return null;
    }
}
