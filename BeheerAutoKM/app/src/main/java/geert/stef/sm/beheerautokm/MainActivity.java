package geert.stef.sm.beheerautokm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    Manager manager;
    EditText txt_username;
    EditText txt_password;
    TextView txt_ingelogd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new Manager();

        txt_ingelogd = (TextView)findViewById(R.id.txt_ingelogd);
        txt_username   = (EditText)findViewById(R.id.txt_username);
        txt_password = (EditText)findViewById(R.id.txt_password);

        txt_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (txt_username.getText().toString().equals("Username")) {
                    txt_username.setText("");
                }
                return false;
            }
        });

        txt_password.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            public void onFocusChange( View v, boolean hasFocus ) {
                if( hasFocus ) {
                    txt_password.setText("");
                }
            }

        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void logIn(View view) {
        boolean ingelogd = manager.Login(this, txt_username.getText().toString(), txt_password.getText().toString());
        if(!ingelogd)
        {
            txt_ingelogd.setText("Inloggen mislukt.");
        }
    }

    public void createAccount(View view) {
    }
}
