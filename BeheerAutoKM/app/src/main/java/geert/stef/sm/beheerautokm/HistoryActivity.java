package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class HistoryActivity extends ActionBarActivity {

    Manager manager;

    Car selectedCar = null;

    private ListView listView;
    private RitListAdapter ritAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkblue)));

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        selectedCar = b.getParcelable("car");
        getSupportActionBar().setTitle(selectedCar.getCar());
        this.getRitten();

        ArrayList<Rit> rittenSelectedCar = new ArrayList<>();
        for(Rit r : manager.getRitten()) {
            if (r.getCar().equals(selectedCar.getLicensePlate())) {
                rittenSelectedCar.add(r);
            }
        }
        listView = (ListView) findViewById(R.id.lvRit);
        ritAdapter = new RitListAdapter(this.getApplicationContext(), rittenSelectedCar);
        listView.setAdapter(ritAdapter);
    }


    public void parseJson(String json) {
        try {
            JSONArray jArray = new JSONArray(json);

            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    int ritID = oneObject.getInt("RitID");
                    String car = oneObject.getString("Car");
                    double distance = oneObject.getDouble("Distance");
                    String driver = oneObject.getString("Driver");
                    System.out.println(oneObject.get("Datum"));
                    System.out.println(oneObject.getString("Datum"));
                    String date = oneObject.getString("Datum").substring(0, 10);
                    String date2 = oneObject.getString("Datum");

                    manager.addRit(ritID, car, distance, driver, parseDate(date2));

                } catch (JSONException e) {
                    // Oops
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logOff) {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.action_about)  {
            Intent intent = new Intent(HistoryActivity.this, AboutActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRitten() {
        String json = null;
        try {
            json = new GetRittenTask().execute().get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        parseJson(json);
    }

}

