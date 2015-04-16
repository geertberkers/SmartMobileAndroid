package geert.stef.sm.beheerautokm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
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
    CheckBox cbRemember;

    SharedPreferences sharedPref;
    List<Car> carList = new ArrayList<>();
    List<Driver> driverList = new ArrayList<>();
    List<Rit> ritList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        //int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
        //cbRemember.setButtonDrawable(id);
        cbRemember.setTextColor(Color.parseColor("#FFFFFF"));
        cbRemember.setChecked(sharedPref.getBoolean("remember", false));

        if (cbRemember.isChecked()) {

            String defaultUsername = getResources().getString(R.string.username);
            String defaultPassword = getResources().getString(R.string.password);
            String username = sharedPref.getString(getString(R.string.username), defaultUsername);
            String password = sharedPref.getString(getString(R.string.password), defaultPassword);

            txtUsername.setText(username);
            txtPassword.setText(password);
        }

        driverList.add(new Driver("geert", "g", "Geert Berkers"));
        driverList.add(new Driver("stef", "s", "Stef Philipsen"));
        driverList.add(new Driver("koen", "k", "Koen Meeuws"));

        carList.add(new Car("Peugeot 206", CarBrand.PEUGEOT, R.mipmap.peugeot, 2001, "Benzine", 90, 185000, "XH-FJ-99", driverList.get(0)));
        carList.add(new Car("Volkswagen Polo", CarBrand.VW, R.mipmap.vw, 2008, "Diesel", 122, 54500, "BL-AB-LA", driverList.get(1)));
        carList.add(new Car("BMW M3", CarBrand.BMW, R.mipmap.bmw, 2014, "LPG", 147, 10000.5, "JA-33-NE", driverList.get(2)));

        carList.get(0).setFavorite(true);

        ritList.add(new Rit(1, "XH-FJ-99", 12.12, "Henk"));
        ritList.add(new Rit(1, "AA-11-BB", 12.12, "Stef"));

        manager = new Manager();
        manager.setMyInt(1);
        manager.setCars(carList);
        manager.setDrivers(driverList);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        txtUsername.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

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


    public void cbRememberPressed(View view) {
        if (view.getId() == R.id.cbRemember) {
            SharedPreferences.Editor editor = sharedPref.edit();
            if(cbRemember.isChecked()){ editor.putBoolean("remember", true);}
            else { editor.putBoolean("remember", false);}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            System.out.println("About pressed");
            // TODO HANDLE EVENT
            // POPUP OR ACTIVITY WITH INFORMATOUS ABOUT US
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logIn(View view) {
        logIn();
    }

    public void logIn() {
        setRemember();
        Driver loggedIn = manager.Login(txtUsername.getText().toString(), txtPassword.getText().toString());
        if (loggedIn != null) {
            Intent intent = new Intent(MainActivity.this, Overview.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            finish();
        } else {
            txtInfo.setText(R.string.logInFailed);
        }
    }

    public void setRemember(){
        if (cbRemember.isChecked()) {
            System.out.println("Log in");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.putBoolean("remember", true);
            editor.putString(getString(R.string.username), txtUsername.getText().toString());
            editor.putString(getString(R.string.password), txtPassword.getText().toString());
            editor.commit();
        }
        else{
            sharedPref.edit().putBoolean("remember", false).commit();
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
                        manager.Register(txtUsername.getText().toString(), txtPassword.getText().toString(), popupName);
                        logIn();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        setRemember();
        super.onDestroy();
    }
}
