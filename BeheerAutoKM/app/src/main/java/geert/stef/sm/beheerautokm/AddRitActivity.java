package geert.stef.sm.beheerautokm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class AddRitActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Manager manager;
    Spinner spinner;
    Car selectedCar;
    // EditText txtDistance;
    ArrayList<Rit> ritten;

    private String[] carSpinner;
    TextView txtKMStand;
    TextView txtKMTank;
    TextView txtKMNog;
    EditText txtKMBegin;
    EditText txtKMEind;
    EditText txtKMTotaal;

    private SpinAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rit);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");
        selectedCar = b.getParcelable("car");

        txtKMStand = (TextView) findViewById(R.id.txtKMStandValue);
        txtKMTank = (TextView) findViewById(R.id.txtKMTankValue);
        txtKMNog = (TextView) findViewById(R.id.txtKMToDriveValue);
        txtKMBegin = (EditText) findViewById(R.id.txtDistanceBegin);
        txtKMEind = (EditText) findViewById(R.id.txtDistanceEnd);
        txtKMTotaal = (EditText) findViewById(R.id.txtDistance);

        //    txtDistance = (EditText) findViewById(R.id.txtDistance);
        spinner = (Spinner) findViewById(R.id.spinner_cars);

        carSpinner = new String[manager.getCars().size()];

        for (int i = 0; i < manager.getCars().size(); i++) {
            carSpinner[i] = manager.getCars().get(i).getCar();
        }

        // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carSpinner);

        myAdapter = new SpinAdapter(this, R.layout.spinner_row, manager.getCars());
        spinner.setAdapter(myAdapter);
        //spinner.setSelection(myAdapter.getPosition(selectedCar), true);
        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(selectedCar.getCar()), true);
        spinner.setOnItemSelectedListener(this);

        //carSpinner.setSelection(myAdapter.getPosition(selectedCar), true);
        //carSpinner.setSelection(manager.getCars().get(3));
        //carSpinner.setSelection(0, true);
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
        if (id == R.id.action_logOff) {
            manager.logOff();
            Intent intent = new Intent(AddRitActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addRitBeginEind(View view) {

        try {
            String begin = txtKMBegin.getText().toString();
            String eind = txtKMEind.getText().toString();

            Double beginDouble = Double.parseDouble(begin);
            Double eindDouble = Double.parseDouble(eind);

            Double distance = eindDouble - beginDouble;

            AsyncTask execute = new AddRitTask();
            //FAILS
            //execute.execute(distance.toString(), selectedCar.getLicensePlate(), manager.getLoggedIn().getUsername());
            new AddRitTask().execute(distance, selectedCar.getLicensePlate(), manager.getLoggedIn());
            txtKMBegin.setText("");
            txtKMEind.setText("");
            showAddedDialog();
            //    }
        } catch (Exception e) {
            System.out.println("Convert to double failed.");
        }
        //Car moet nog een ID hebben, dus moet ook met Parcelable geimplementeerd worden, 1 is testwaarde.
        //dc.addRit(distance, 1)
    }

    public void addRitKM(View view) {
        Double distance = 0.00;
        try {
            if (tryParseDouble(txtKMTotaal.getText().toString())) {
                distance = Double.parseDouble(txtKMTotaal.getText().toString());
                //String distance, String license, String driver
                new AddRitTask().execute(distance, selectedCar.getLicensePlate(), manager.getLoggedIn());
                txtKMTotaal.setText("");
                showAddedDialog();
            }
        } catch (Exception e) {
            System.out.println("Convert to double failed.");
        }
    }

    public boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException nfe) {
            showNotValidNumberDialog();
            return false;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        selectedCar = myAdapter.getItem(pos);
        txtKMStand.setText(String.valueOf(selectedCar.getMileage()));
        txtKMTank.setText(String.valueOf(selectedCar.getKMTank()));
        txtKMNog.setText(String.valueOf(selectedCar.getKMTank() - selectedCar.getKMDriven()));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

    public void trackLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        this.startActivity(intent);
        finish();
    }
}

