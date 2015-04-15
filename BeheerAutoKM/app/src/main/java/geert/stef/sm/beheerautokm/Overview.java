package geert.stef.sm.beheerautokm;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Overview extends ActionBarActivity implements AdapterView.OnItemClickListener {
    Manager manager;
    private Car selectedCar = null;

    private ListView listView;
    private DrawerLayout drawerLayout;

    private MyAdapter myAdapter;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        TextView tv = (TextView) findViewById(R.id.txtBouwjaar);
        Car c = manager.getCars().get(0);
        tv.setText(c.getCar());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawerList);

        myAdapter = new MyAdapter(this.getApplicationContext() ,manager.getCars());

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
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

    public void selectItem(int position){
        listView.setItemChecked(position, true);

        for(Car c : manager.getCars())
        {
            if(myAdapter.getItem(position).equals(c))
            {
                selectedCar = c;
                setTitle(c.getCar());
                setIcon(c.getImage());
                drawerLayout.closeDrawer(listView);
            }
        }

        //ADD A CAR
        if(selectedCar.getCar().equals("Add Car"))
        {
            //myAdapter.addCar(new Car("Test", R.drawable.ic_launcher));
            // Activity create car aanmaken
            Toast.makeText(Overview.this, "Car added", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void setIcon(int image)
    {
        getSupportActionBar().setIcon(image);
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
            // TO DO: Implement code for settings
            return true;
        }

        if(drawerListener.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
        listView.setAdapter(myAdapter);
    }

    public void addCar(View view){
        if (view.getId() == R.id.btnAddCar){
            
        }
    }
}

