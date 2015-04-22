package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Geert on 15-4-2015.
 */
public class AddCarActivity extends ActionBarActivity {

    private Manager manager;
    private EditText txtBrand;
    private EditText txtModel;
    private EditText txtYear;
    private EditText txtFuel;
    private EditText txtHP;
    private EditText txtMileage;
    private EditText txtKMTank;
    private EditText txtKMLeft;
    private EditText txtLicensePlate;
    private EditText txtDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);

        txtBrand = (EditText) findViewById(R.id.txtBrandValue);
        txtModel = (EditText) findViewById(R.id.txtModelValue);
        txtYear = (EditText) findViewById(R.id.txtYearValue);
        txtFuel = (EditText) findViewById(R.id.txtFuelValue);
        txtHP = (EditText) findViewById(R.id.txtHPValue);
        txtMileage = (EditText) findViewById(R.id.txtKMStandValue);
        txtKMTank = (EditText) findViewById(R.id.txtKMTankValue);
        txtKMLeft = (EditText) findViewById(R.id.txtKMToDriveValue);
        txtLicensePlate = (EditText) findViewById(R.id.txtLicenseplateValue);
        txtDriver = (EditText) findViewById(R.id.txtOwnerValue);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_rit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logOff)  {
            Intent intent = new Intent(AddCarActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.action_about)  {
            Intent intent = new Intent(AddCarActivity.this, AboutActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCar(View view){
        Car newCar;
        String car = txtBrand.getText().toString() + " " + txtModel.getText().toString();
        String brand = txtBrand.getText().toString();
        CarBrand carBrand = null;
        int year = Integer.valueOf(txtYear.getText().toString());
        String fuel = txtFuel.getText().toString();
        int hp = Integer.valueOf(txtHP.getText().toString());
        int mileage = Integer.valueOf(txtMileage.getText().toString());
        int kmtank = Integer.valueOf(txtKMTank.getText().toString());
        int kmleft = Integer.valueOf(txtKMLeft.getText().toString());
        String licenseplate = txtLicensePlate.getText().toString();

        int pic = 0;
        switch(brand.toLowerCase()){
            case "opel": carBrand = CarBrand.OPEL; pic = R.mipmap.opel; break;
            case "peugeot": carBrand = CarBrand.PEUGEOT; pic = R.mipmap.peugeot; break;
            case "audi": carBrand = CarBrand.AUDI; pic = R.mipmap.audi; break;
            case "bmw": carBrand = CarBrand.BMW; pic = R.mipmap.bmw; break;
            case "vw": carBrand = CarBrand.VW; pic = R.mipmap.vw; break;
            case "volkswagen": carBrand = CarBrand.VW; pic = R.mipmap.vw; break;
        }

        Driver driver = null;
        for(Driver d : manager.getDrivers()){
            if(d.getUsername().equals(txtDriver.getText().toString())){
                driver = d;
            }
        }
        newCar = new Car(car,carBrand,pic,year,fuel,hp,mileage,kmtank,kmleft,licenseplate,driver);

        manager.addCar(newCar);

        System.out.println("Added");
        Intent intent = new Intent(AddCarActivity.this, Overview.class);
        intent.putExtra("parcel", manager);
        this.startActivity(intent);
        finish();
    }
}
