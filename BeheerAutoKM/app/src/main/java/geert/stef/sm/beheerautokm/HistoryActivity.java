package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private ListView listView;
    private RitListAdapter ritAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        listView = (ListView) findViewById(R.id.lvRit);
        ritAdapter = new RitListAdapter(this.getApplicationContext(), manager.getRitten());



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

        listView.setAdapter(ritAdapter);
    }



        public void parseJson(String json) {
            //ritten = new ArrayList<>();
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
                        String date = oneObject.getString("Datum").substring(0,10);
                    /*
                    int year = Integer.parseInt(oneObject.getString("Datum").substring(0, 4));
                    int month = Integer.parseInt(oneObject.getString("Datum").substring(5, 7));
                    int day = Integer.parseInt(oneObject.getString("Datum").substring(8, 10));

                    Date date = new Date(year, month, day);
                    System.out.println(date.toString());*/
                        manager.addRit(ritID, car, distance, driver, parseDate(date));
//                    ritten.add(new Rit(ritID, car, distance, driver, parseDate(date)));
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
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
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

        if (id == R.id.action_logOff)  {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

