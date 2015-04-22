package geert.stef.sm.beheerautokm;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Stef on 22-4-2015.
 */
public class AddDriverTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        String username = String.valueOf(params[0].toString());
        String password = String.valueOf(params[1].toString());
        String name = String.valueOf(params[2].toString());

        addDriver(username, password, name);
        return null;
    }

    public void addDriver(String username, String password, String name) {
        String url = "http://stefp.nl/addDriver.php";
        String p1 = "?username='" + username + "'";
        String p2 = "&password='" + password + "'";
        String p3 = "&name='" + name + "'";
        System.out.println(url + p1 + p2 + p3);
        this.hitUrl(url + p1 + p2 + p3);
        //this.hitUrl("http://stefp.nl/addDriver.php?username='Henkie'&password='Henkie'&name='Henk Janssen'");
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
