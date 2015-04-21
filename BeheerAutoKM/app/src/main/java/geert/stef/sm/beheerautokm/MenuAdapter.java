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
public class MenuAdapter extends BaseAdapter {
    private Context context;

    private List<Car> carList = new ArrayList<>();
    public void  setCarList(List<Car> carList){ this.carList = carList; notifyDataSetChanged();}

    public MenuAdapter(Context context, List<Car> carList){
        this.context = context;
        this.carList = carList;
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


        for(CarBrand cb : CarBrand.values())
        {
            if(carList.get(position).getBrand().toString().equals(cb.toString())){
                switch (cb.toInt()){
                    case 0:titleImageView.setImageResource(R.mipmap.audi); break;
                    case 1:titleImageView.setImageResource(R.mipmap.bmw); break;
                    case 2:titleImageView.setImageResource(R.mipmap.opel); break;
                    case 3:titleImageView.setImageResource(R.mipmap.peugeot); break;
                    case 4:titleImageView.setImageResource(R.mipmap.vw); break;
                }
            }
        }

        if(carList.get(position).isFavorite()) { favoriteImageView.setImageResource(R.drawable.favorite);} else { favoriteImageView.setImageResource(0);}

        return row;
    }
}
