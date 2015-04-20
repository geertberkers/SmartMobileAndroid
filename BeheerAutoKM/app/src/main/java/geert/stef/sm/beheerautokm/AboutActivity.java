package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Geert on 21-4-2015.
 */
public class AboutActivity extends ActionBarActivity {

    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Bundle b = getIntent().getExtras();
        manager = b.getParcelable("parcel");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOff)  {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            intent.putExtra("parcel", manager);
            this.startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

}
