package geert.stef.sm.beheerautokm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Manager manager;
    EditText txtUsername;
    EditText txtPassword;
    TextView txtInfo;
    String popupName;

    List<Car> carList = new ArrayList<>();
    List<Driver> driverList = new ArrayList<>();
    List<Rit> ritList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driverList.add(new Driver("geert","g","Geert Berkers"));
        driverList.add(new Driver("stef","s","Stef Philipsen"));
        driverList.add(new Driver("koen","k","Koen Meeuws"));

        carList.add(new Car("Peugeot", R.drawable.peugeot, 2001, "Benzine", 90 ,185000, "XH-FJ-99", driverList.get(0)));
        carList.add(new Car("Volkswagen Polo", R.drawable.polo, 2008, "Diesel", 122, 54500, "BL-AB-LA", driverList.get(1)));
        carList.add(new Car("Bugatti Veyron SS", R.drawable.bugatti, 2014, "LPG", 147, 10000.5, "JA-33-NE", driverList.get(2)));

        ritList.add(new Rit(1, 1, 12.12));
        ritList.add(new Rit(1, 1, 12.12));

        manager = new Manager();
        // INTEGER TO TEST MANAGER
        manager.setMyInt(100);
        manager.setCars(carList);
        manager.setDrivers(driverList);

        txtInfo = (TextView)findViewById(R.id.txtInfo);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        txtUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (txtUsername.getText().toString().equals("Username")) {
                    txtUsername.setText("");
                }
                return false;
            }
        });

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtPassword.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logIn(View view) {
        logIn();
    }

    public void logIn() {
        boolean loggedIn = manager.Login(this, txtUsername.getText().toString(), txtPassword.getText().toString());
        if(loggedIn)
        {
            Intent intent = new Intent(MainActivity.this, Overview.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            finish();
        }
        else {
            txtInfo.setText(R.string.logInFailed);
        }
    }

    public void createAccount(View view) {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Set name:")
                .setMessage("Name of the driver:")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        popupName = input.getText().toString();
                        manager.Register(getApplicationContext(), txtUsername.getText().toString(), txtPassword.getText().toString(), popupName);
                        if(manager.Login(getApplicationContext(),txtUsername.getText().toString(), txtPassword.getText().toString())) {
                            logIn();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();

    }
}
