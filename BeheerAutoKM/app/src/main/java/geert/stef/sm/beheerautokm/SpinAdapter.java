package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Geert on 20-4-2015.
 */
public class SpinAdapter extends ArrayAdapter<Car> {

        private Context context;
        private List<Car> values;

        public SpinAdapter(Context context, int textViewResourceId, List<Car> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        public int getCount(){
            return values.size();
        }

        public Car getItem(int position){
            System.out.println(values.get(position).toString());
            return values.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getCar());
            //  System.out.println(label + "added to dropdown");
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getCar());
            label.setPadding(0,10,0,0);

            return label;
        }
    }