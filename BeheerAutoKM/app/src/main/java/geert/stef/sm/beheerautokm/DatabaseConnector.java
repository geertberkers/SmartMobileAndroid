package geert.stef.sm.beheerautokm;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Stef on 9-4-2015.
 */
public class DatabaseConnector extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        double d = Double.valueOf(params[0].toString());
        int i = Integer.valueOf(params[1].toString());
        addRit(d, i);
        return null;
    }

    public void addRit(double distance, int carid) {
        String url = "http://stefp.nl/addRit.php";
        String p1 = "?carid=" + carid;
        String p2 = "&distance=" + distance;
        this.hitUrl(url + p1 + p2);
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
