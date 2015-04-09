package geert.stef.sm.beheerautokm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Manager manager;
    EditText txtUsername;
    EditText txtPassword;
    TextView txtInfo;

    List<Car> carList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carList.add(new Car("Peugot", 2001, "XH-FJ-99"));
        carList.get(0).setName("Renault2");

        manager = new Manager();
        manager.setMyInt(100);
        manager.setCars(carList);

        txtInfo = (TextView)findViewById(R.id.txtInfo);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        txtUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (txtUsername.getText().toString().equals("Username")) {
                    txtUsername.setText("");
                }
                return false;
            }
        });

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtPassword.setText("");
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logIn(View view) {
        boolean loggedIn = manager.Login(this, txtUsername.getText().toString(), txtPassword.getText().toString());
        if(loggedIn)
        {
            Intent intent = new Intent(MainActivity.this, Overview.class);
            intent.putExtra("parcel", manager);
            //intent.putParcelableArrayListExtra("parcel", manager.getCars());
            this.startActivity(intent);
            finish();
        }
        else {
            txtInfo.setText(R.string.logInFailed);
        }

    }

    public void createAccount(View view) {
    }
}
