package geert.stef.sm.beheerautokm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Overview extends ActionBarActivity implements AdapterView.OnItemClickListener {
    Manager manager;
    private Car selectedCar = null;

    private ListView listView;
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayout;

    private MyAdapter myAdapter;
    private ActionBarDrawerToggle drawerListener;

    private TextView tvCar;
    private TextView tvYear;
    private TextView tvFuel;
    private TextView tvHP;
    private TextView tvKM;
    private TextView tvLicensePlate;
    private TextView tvOwner;
    private CheckBox cbFavorite;
    private ImageView ivCar;
    private Bitmap bitmap;

    private static final int REQUEST_CODE_CAPTURE = 1;
    private static final int REQUEST_CODE_SELECT = 2;

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
                // Toast.makeText(Overview.this, "Closed", Toast.LENGTH_SHORT).show();
            }

            public void onDrawerOpened(View drawerView) {
                // Toast.makeText(Overview.this, "Opened", Toast.LENGTH_SHORT).show();
            }
        };

        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

            if (cbFavorite.isChecked()) {
                for (int i = 0; i < manager.getCars().size(); i++) {
                    manager.getCars().get(i).setFavorite(false);
                    if (manager.getCars().get(i).equals(selectedCar)) {
                        manager.getCars().get(i).setFavorite(true);
                    }
                }
                myAdapter.setCarList(manager.getCars());
            } else {
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

    public void addGalleryImage(View view) {
        if (view.getId() == R.id.btnAddGalleryImage) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        }
    }

    public void addNewImage(View view){
        if (view.getId() == R.id.btnAddNewImage){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;

        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            // TO DO GET FULL IMAGE AND RESIZE
            ivCar.setImageBitmap(bitmap);
        }
        else if (requestCode == REQUEST_CODE_SELECT && resultCode == Activity.RESULT_OK){
            try {
                if (bitmap != null) { bitmap.recycle(); }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                ivCar.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

