package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Overview extends ActionBarActivity {
    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");

        TextView tv = (TextView) findViewById(R.id.txtBouwjaar);
        Car c = manager.getCars().get(0);
        tv.setText(c.getCar());
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
}
