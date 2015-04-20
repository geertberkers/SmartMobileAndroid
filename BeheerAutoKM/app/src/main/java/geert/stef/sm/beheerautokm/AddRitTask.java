package geert.stef.sm.beheerautokm;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Stef on 9-4-2015.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class AddRitTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        String distance = String.valueOf(params[0].toString());
        String license = String.valueOf(params[1].toString());
        Driver d = (Driver)params[2];
        String driver = d.getName();
        //String driver = String.valueOf(params[2].toString());
        System.out.println(distance + license + driver);
        addRit(distance, license, driver);
        return null;
    }

    public void addRit(String distance, String license, String driver) {
        String url = "http://stefp.nl/addRit.php";
        String p1 = "?car='" + license + "'";
        String p2 = "&distance=" + distance;
        String p3 = "&driver='" + driver + "'";
        System.out.println(url + p1 + p2 + p3);
        this.hitUrl(url + p1 + p2 + p3);
        //this.hitUrl("http://stefp.nl/addRit.php?car='XH-FJ-99'&distance=11.11&driver='Stef'");
    }

    public static HttpResponse hitUrl(String url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            return response;
        } catch (Exception e) {
            //Log.("[GET REQUEST]", "Network exception", e);
            return null;
        }
    }
}
