package geert.stef.sm.beheerautokm;
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

    public void setCarList(){ this.carList = carList;}

    public MyAdapter(Context context, List<Car> carList){
        this.context = context;
        this.carList = carList;
/*
        cars = context.getResources().getStringArray(R.array.cars);

        for(int i = 0; i<cars.length;i++)
        {
            carList.add(new Car(cars[i],images[i]));
        }
        */
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
        ImageView favoriteImageView = (ImageView) row.findViewById(R.id.favoriteCar);

        titleMenuItem.setText(carList.get(position).getCar());
        titleImageView.setImageResource(carList.get(position).getImage());
        if(carList.get(position).isFavorite()) { favoriteImageView.setImageResource(R.drawable.favorite);}

        return row;
    }
}
