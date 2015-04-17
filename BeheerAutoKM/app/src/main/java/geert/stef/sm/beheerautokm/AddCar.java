package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Geert on 15-4-2015.
 */
public class AddCar extends ActionBarActivity {

    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);

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
            Intent intent = new Intent(AddCar.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCar(View view){

       // manager.addCar(new Car("BMW M3", CarBrand.BMW, R.mipmap.bmw, 2014, "LPG", 147, 10000.5, "JA-33-NE", driverList.get(2)));
    }
}
