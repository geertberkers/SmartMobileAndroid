package geert.berkers.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geert on 13-4-2015.
 */
public class MyAdapter extends BaseAdapter {
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
