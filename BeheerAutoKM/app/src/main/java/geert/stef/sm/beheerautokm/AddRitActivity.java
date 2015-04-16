package geert.stef.sm.beheerautokm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class AddRitActivity extends ActionBarActivity {

    Manager manager;
    Spinner s;
    String[] carSpinner;
    EditText txtDistance;
    ArrayList<Rit> ritten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rit);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        txtDistance = (EditText) findViewById(R.id.txtDistance);
        s = (Spinner) findViewById(R.id.spinner_cars);

        String[] carSpinner = new String[manager.getCars().size()];

        for(int i = 0; i < manager.getCars().size(); i++) {
            carSpinner[i] = manager.getCars().get(i).getCar();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carSpinner);
        s.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_rit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addRit(View view) {
        String name = s.getSelectedItem().toString();
        Car car = null;

        for(Car c : manager.getCars())
        {
            if(c.getCar().equals(name))
            {
                car = c;
            }
        }

        Double distance = 0.00;
        try {
            if (tryParseDouble(txtDistance.getText().toString())) {
                distance = Double.parseDouble(txtDistance.getText().toString());
                new AddRitTask().execute(distance, 1, 1);
                txtDistance.setText("");
                showAddedDialog();
            }
        } catch (Exception e) {
            System.out.println("Convert to double failed.");
        }
        //Car moet nog een ID hebben, dus moet ook met Parcelable geimplementeerd worden, 1 is testwaarde.
        //dc.addRit(distance, 1);

    }

    boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException nfe) {
            showNotValidNumberDialog();
            return false;
        }
    }

    public void showNotValidNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Not a valid number")
                .setMessage("Something like \"12.34\"")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do some thing here which you need
                    }
                });
        /*builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });*/
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showAddedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Added")
                .setMessage("The number is added.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do some thing here which you need
                    }
                });
        /*builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });*/
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void getRitten(View view) {
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

    public void parseJson(String json)
    {
        ritten = new ArrayList<>();
        try {
//            JSONObject jObject = new JSONObject(json);
            JSONArray jArray =  new JSONArray(json);

            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    int ritID = oneObject.getInt("RitID");
                    String car = oneObject.getString("Car");
                    double distance = oneObject.getDouble("Distance");
                    String driver = oneObject.getString("Driver");

                    int year = Integer.parseInt(oneObject.getString("Datum").substring(0,4));
                    int month = Integer.parseInt(oneObject.getString("Datum").substring(5,7));
                    int day = Integer.parseInt(oneObject.getString("Datum").substring(8,10));

                    Date date = new Date(year,month, day);
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

    public void trackLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        this.startActivity(intent);
        finish();
    }
}
