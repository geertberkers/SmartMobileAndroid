package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class LocationActivity extends ActionBarActivity {

    TextView txtLocation;
    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }else{
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
        }

        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtLocation.setText("Location: " /* + location */);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOff)  {
            Intent intent = new Intent(LocationActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.action_about)  {
            Intent intent = new Intent(LocationActivity.this, AboutActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
