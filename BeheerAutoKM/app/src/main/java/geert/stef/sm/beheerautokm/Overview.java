package geert.stef.sm.beheerautokm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private String mCurrentPhotoPath;
    private SharedPreferences sharedPref;

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

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        for (Car c : manager.getCars()) {
            String directory = sharedPref.getString("dir", "");
            String image = sharedPref.getString("car" + c.getCar(), "");

            if (directory.equals("") || image.equals("")) {
                System.out.println("Empty");
            } else {
                int position = manager.getCars().indexOf(c);
                manager.getCars().get(position).setLocalImage(createImageFromFilePath(directory, image));

                if (c.equals(selectedCar)) {
                    ivCar.setImageBitmap(selectedCar.getLocalImage());
                }
            }

            drawerLayout.setDrawerListener(drawerListener);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

        if (id == R.id.action_logOff)  {
            manager.logOff();
            Intent intent = new Intent(Overview.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.action_clearPhoto)  {
            sharedPref.edit().clear().apply();
            for(int i = 0; i < manager.getCars().size(); i++)
            {
                manager.getCars().get(i).setLocalImage(null);
                ivCar.setImageResource(manager.getCars().get(i).getImage());
            }
            myAdapter.notifyDataSetChanged();

            return true;
        }

        if (drawerListener.onOptionsItemSelected(item)) {
            System.out.println("DrawerLister pressed");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;

        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setPic();
        } else if (requestCode == REQUEST_CODE_SELECT && resultCode == Activity.RESULT_OK) {
            try {
                stream = getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                resizeBitmap(bitmap);

                ivCar.setImageBitmap(bitmap);
                ivCar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                setCarImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
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

    public void addRitOnClick(View view) {
        if (view.getId() == R.id.btnAddRit) {
            Intent intent = new Intent(this, AddRitActivity.class);
            intent.putExtra("parcel", manager);
            intent.putExtra("car", selectedCar);
            this.startActivity(intent);
        }
    }

    public void historyOnClick(View view) {
        if (view.getId() == R.id.btnHistory) {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("parcel",manager);
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

        if (selectedCar.getLocalImage() != null) {
            ivCar.setImageBitmap(selectedCar.getLocalImage());
        } else {
            ivCar.setImageResource(selectedCar.getImage());
            ivCar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
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
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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

    public void addNewImage(View view) {
        if (view.getId() == R.id.btnAddNewImage) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile(selectedCar.getCar());
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAPTURE);
                }
            }
        }
    }

    private File createImageFile(String car) throws IOException {

        File storageDir = new File(Environment.getExternalStorageDirectory().toString() + "/BeheerAutoKM/");
        storageDir.mkdirs();

        File image = File.createTempFile(car, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();

        galleryAddPic();

        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivCar.getWidth();
        int targetH = ivCar.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivCar.setImageBitmap(bitmap);
        setCarImage(bitmap);

    }

    public void setCarImage(Bitmap bitmap) {
        for (int i = 0; i < manager.getCars().size(); i++) {
            if (manager.getCars().get(i).equals(selectedCar)) {
                manager.getCars().get(i).setLocalImage(bitmap);
                saveImageToInternalStorage(bitmap, selectedCar.toString());
            }
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap) {
        if (bitmap.getHeight() > 4096 || bitmap.getWidth() > 4096) {
            int width = (int) (bitmap.getWidth() * 0.9);
            int height = (int) (bitmap.getHeight() * 0.9);

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

            resizeBitmap(resizedBitmap);

            return  resizedBitmap;
        } else{
            return bitmap;
        }

    }

    public boolean saveImageToInternalStorage(Bitmap bitmap, String car) {

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/BeheerAutoKM/");
            dir.mkdirs();

            File file = new File(dir, car + ".jpg");

            FileOutputStream fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();

            sharedPref.edit().putString("dir", dir.getAbsolutePath() + "/").apply();
            sharedPref.edit().putString("car" + car, car + ".jpg").apply();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    public Bitmap createImageFromFilePath(String path, String imageName) {
        File file = new File(path, imageName);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
    }

}


