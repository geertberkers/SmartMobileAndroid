package geert.stef.sm.beheerautokm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Overview extends ActionBarActivity implements AdapterView.OnItemClickListener {
    Manager manager;
    private Car selectedCar = null;

    private ListView listView;
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayout;

    private MyAdapter myAdapter;
    private ActionBarDrawerToggle drawerListener;

    TextView tvCar;
    TextView tvYear;
    TextView tvFuel;
    TextView tvHP;
    TextView tvKM;
    TextView tvLicensePlate;
    TextView tvOwner;
    CheckBox cbFavorite;
    ImageView ivCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        tvCar = (TextView) findViewById(R.id.txtCar);
        ivCar = (ImageView) findViewById(R.id.carPhoto);
        tvYear = (TextView) findViewById(R.id.txtYearValue);
        tvFuel = (TextView) findViewById(R.id.txtFuelValue);
        tvHP = (TextView) findViewById(R.id.txtHPValue);
        tvKM = (TextView) findViewById(R.id.txtMileageValue);
        tvLicensePlate = (TextView) findViewById(R.id.txtLicenseplateValue);
        tvOwner = (TextView) findViewById(R.id.txtOwnerValue);
        cbFavorite = (CheckBox) findViewById(R.id.cbFavorite);
        cbFavorite.setChecked(false);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        for (Car c : manager.getCars()) {
            if (c.isFavorite()) {
                selectedCar = c;
                setSelectedCar(selectedCar);
            }
        }

        linearLayout = (LinearLayout) findViewById(R.id.DrawerLinear);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawerList);

        myAdapter = new MyAdapter(this.getApplicationContext(), manager.getCars());

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                Toast.makeText(Overview.this, "Closed", Toast.LENGTH_SHORT).show();
            }

            public void onDrawerOpened(View drawerView) {
                Toast.makeText(Overview.this, "Opened", Toast.LENGTH_SHORT).show();
            }
        };

        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Pressed on Settings menu
            return true;
        }

        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
        listView.setAdapter(myAdapter);
    }

    public void addRitOnClick(View view) {
        if (view.getId() == R.id.btnAddRit) {
            Intent intent = new Intent(this, AddRitActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
        }
    }

    public void historyOnClick(View view) {
        if (view.getId() == R.id.btnHistory) {
            Intent intent = new Intent(this, HistoryActivity.class);
            this.startActivity(intent);
        }
    }

    public void addCar(View view) {
        if (view.getId() == R.id.btnAddCar) {
            Intent intent = new Intent(Overview.this, AddCar.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
        }
    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);

        for (Car c : manager.getCars()) {
            if (myAdapter.getItem(position).equals(c)) {
                setSelectedCar(c);
                drawerLayout.closeDrawer(linearLayout);
            }
        }
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
        getSupportActionBar().setTitle(selectedCar.getCar());
        getSupportActionBar().setIcon(selectedCar.getImage());

        System.out.println(selectedCar);

        tvCar.setText(selectedCar.getCar());
        ivCar.setImageResource(selectedCar.getImage());
        ivCar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tvYear.setText(String.valueOf(selectedCar.getYear()));
        tvFuel.setText(selectedCar.getFuel());
        tvHP.setText(String.valueOf(selectedCar.getHorsepower()));
        tvKM.setText(String.valueOf(selectedCar.getMileage()));
        tvLicensePlate.setText(selectedCar.getLicensePlate());
        tvOwner.setText(selectedCar.getOwner().getName());
        if (selectedCar.isFavorite()) {
            cbFavorite.setChecked(true);
        } else {
            cbFavorite.setChecked(false);
        }
    }

    public void setAsFavorite(View view) {
        if (view.getId() == R.id.cbFavorite) {

            if(cbFavorite.isChecked())
            {
                for (int i = 0; i < manager.getCars().size(); i++) {
                    manager.getCars().get(i).setFavorite(false);
                    if (manager.getCars().get(i).equals(selectedCar)) {
                        manager.getCars().get(i).setFavorite(true);
                    }
                }
                myAdapter.setCarList(manager.getCars());
            }
            else {
                cbFavorite.setChecked(true);
                new AlertDialog.Builder(this)
                        .setTitle("Favorite Car")
                        .setMessage("You must have a favorite car")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).show();
            }
        }
    }
}

