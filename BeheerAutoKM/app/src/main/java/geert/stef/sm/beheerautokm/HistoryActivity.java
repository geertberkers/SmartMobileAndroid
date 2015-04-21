package geert.stef.sm.beheerautokm;

import android.content.Intent;
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


public class HistoryActivity extends ActionBarActivity {

    Manager manager;
    Car selectedCar = null;
    ArrayList<Rit> ritten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");
        selectedCar = b.getParcelable("car");
        ritten = new ArrayList<>();
        this.getRitten();
        TableLayout tl = (TableLayout) findViewById(R.id.tbl_history);

        for(Rit r : ritten)
        {
            if(r.getCar().equals(selectedCar.getLicensePlate()))
            {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                //TextView date = new TextView(this);
                //date.setText(r.getDate().toString());

                TextView txDriver = new TextView(this);
                txDriver.setText(r.getDriver().getUsername());

                TextView txDistance = new TextView(this);
                txDistance.setText(String.valueOf(r.getDistance()));

                txDriver.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(txDriver);
                tr.addView(txDistance);
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOff)  {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRitten() {
        GetRittenTask grTask = new GetRittenTask();
        //grTask.execute();
        String json = null;
        try {
            json = grTask.execute().get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        parseJson(json);
    }

    public void parseJson(String json) {
        ritten = new ArrayList<>();
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
                    int year = Integer.parseInt(oneObject.getString("Datum").substring(0, 4));
                    int month = Integer.parseInt(oneObject.getString("Datum").substring(5, 7));
                    int day = Integer.parseInt(oneObject.getString("Datum").substring(8, 10));

                    Date date = new Date(year, month, day);
                    System.out.println(date.toString());
                    ritten.add(new Rit(ritID, car, distance, driver));
                } catch (JSONException e) {
                    // Oops

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
