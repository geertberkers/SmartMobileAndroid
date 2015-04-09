package geert.stef.sm.beheerautokm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class AddRitActivity extends ActionBarActivity {

    Manager manager;
    DatabaseConnector dc;
    Spinner s;
    String[] carSpinner;
    EditText txtDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rit);

        dc = new DatabaseConnector();

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        txtDistance = (EditText) findViewById(R.id.txtDistance);
        s = (Spinner) findViewById(R.id.spinner_cars);

        String[] carSpinner = new String[manager.getCars().size()];
        for(int i = 0; i < manager.getCars().size(); i++) {
            carSpinner[i] = manager.getCars().get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carSpinner);
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

    public void addRit(){
        String name = s.getSelectedItem().toString();
        Car car = null;
        for(Car c : manager.getCars())
        {
            if(c.getName().equals(name))
            {
                car = c;
            }
        }
        Double distance = Double.valueOf(txtDistance.getText().toString());
        //Car moet nog een ID hebben, dus moet ook met Parcelable geimplementeerd worden, 1 is testwaarde.
        dc.addRit(distance, 1);
    }
}
