package geert.berkers.navigationdrawer;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
    class MyAdapter extends BaseAdapter{
    	private Context context;
    	
    	private String[] cars;
    	private Integer[] images ={R.drawable.peugeot, R.drawable.polo, R.drawable.bugatti, R.drawable.plus};
    	    	
    	private List<Car> carList = new ArrayList<>();
    	
    	public void addCar(Car car){
    		carList.add(carList.size()-1, car);
    		notifyDataSetChanged();
    	}
    	
    	public List<Car> getCarList(){
    		return carList;
    	}
    	
    	public MyAdapter(Context context){
    		this.context = context;
    		
    		cars = context.getResources().getStringArray(R.array.cars);

    		for(int i = 0; i<cars.length;i++)
    		{
    			carList.add(new Car(cars[i],images[i]));
    		}
    	}
    	
		@Override
		public int getCount() {
			return carList.size();
		}

		@Override
		public Object getItem(int position) {
			return carList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = null;
			if(convertView == null)	
			{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.drawer_layout, parent, false);
			}
			else
			{
				row = convertView;
			}
			TextView titleMenuItem = (TextView) row.findViewById(R.id.menuItem);
			ImageView titleImageView = (ImageView) row.findViewById(R.id.menuPicture);
			
			titleMenuItem.setText(carList.get(position).getCarName());
			titleImageView.setImageResource(carList.get(position).getImage());
			
			return row;
		}
}
    class Car{
    	private String carName;
    	private int image;
    	
    	public Car(String car, int image){
    		this.carName = car;
    		this.image = image;
    	}
    	
    	public String getCarName(){
    		return carName;
    	}
    	
    	public int getImage(){
    		return image;
    	}
    	@Override
    	public String toString() {
    		return carName;
    	}
    }
