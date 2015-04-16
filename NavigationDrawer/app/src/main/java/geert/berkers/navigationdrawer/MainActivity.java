package geert.berkers.navigationdrawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnItemClickListener {

	private Car selectedCar = null;
	
	private ListView listView;
	private DrawerLayout drawerLayout;
	
	private MyAdapter myAdapter;
	private ActionBarDrawerToggle drawerListener;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        setContentView(R.layout.activity_main);
                
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawerList);
        
        myAdapter = new MyAdapter(this);
        
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
        
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();
			}
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();
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

    public void selectItem(int position) {
    	listView.setItemChecked(position, true);
    	
    	for(Car c : myAdapter.getCarList())
    	{
    		if(myAdapter.getItem(position).equals(c))
    		{
    			selectedCar = c;
    	    	setTitle(c.getCarName());
    	    	setIcon(c.getImage());
                drawerLayout.closeDrawer(listView);
    		}
    	}
    	
    	//ADD A CAR
    	if(selectedCar.getCarName().equals("Add Car"))
    	{
    		myAdapter.addCar(new Car("Test", R.drawable.ic_launcher));
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
        // Inflate the menu from action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        if(drawerListener.onOptionsItemSelected(item))
        {
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
}

