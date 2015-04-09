package com.example.stef.parcelableobjectstest2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReceiverParcel extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_parcel);

        Bundle b = getIntent().getExtras();
        MyParcelable object = b.getParcelable("parcel");

        TextView tv = (TextView) findViewById(R.id.txt_receiver);
        int t = object.getArrList().get(0).getTest();
        tv.setText(Integer.toString(t));

        TextView tv2 = (TextView) findViewById(R.id.txt_receiver2);
        int t2 = object.getArrList().get(1).getTest();
        tv2.setText(Integer.toString(t2));

        System.out.println(object.getArrList().get(0).getTest());
        System.out.println(object.getMyInt());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receiver_parcel, menu);
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
}
