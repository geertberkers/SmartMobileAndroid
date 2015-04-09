package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Overview extends ActionBarActivity {
    Manager m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Bundle b = getIntent().getExtras();
        Manager object = b.getParcelable("parcel");

        TextView tv = (TextView) findViewById(R.id.txtBouwjaar);
        //Car c = object.getCars().get(0);
        //tv.setText(c.getName());
 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
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

    public void addRitOnClick(View view) {
        Intent intent = new Intent(this, AddRitActivity.class);
        this.startActivity(intent);
    }

    public void historyOnClick(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        this.startActivity(intent);
    }
}
