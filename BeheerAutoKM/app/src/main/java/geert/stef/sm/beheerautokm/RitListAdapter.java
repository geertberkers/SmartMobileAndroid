package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geert on 21-4-2015.
 */
public class RitListAdapter extends BaseAdapter {
    private Context context;

    private List<Rit> ritList = new ArrayList<>();
    public void  setRitList(List<Rit> ritList){ this.ritList = ritList; notifyDataSetChanged();}

    public RitListAdapter(Context context, List<Rit> ritList){
        this.context = context;
        this.ritList = ritList;
    }

    @Override
    public int getCount() {
        return ritList.size();
    }

    @Override
    public Object getItem(int position) {
        return ritList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.rit_layout, parent, false);
        }
        else
        {
            row = convertView;
        }

        TextView driver = (TextView) row.findViewById(R.id.txtDriver);
        TextView car = (TextView) row.findViewById(R.id.txtCar);
        TextView distance = (TextView) row.findViewById(R.id.txtDistance);
        TextView date = (TextView) row.findViewById(R.id.txtDate);
/*
       for(Rit r: ritList){
           System.out.println(r);
       }
*/
        driver.setText(ritList.get(position).getDriver().getUsername().toString());
        car.setText(ritList.get(position).getCar().toString());
        distance.setText(String.valueOf(ritList.get(position).getDistance()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(sdf.format(ritList.get(position).getDate()));
        return row;
    }
}
