package geert.stef.sm.beheerautokm;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Stef on 13-4-2015.
 */
public class LogInTask extends AsyncTask {
    private String json;
    Driver d;

    @Override
    protected Object doInBackground(Object[] params) {
        String username = params[0].toString();
        String password = params[1].toString();
        getJson(username, password);
        return json;
    }

    public String test(){
        return json;
    }

    public void getJson(String username, String password)
    {
        //http://stefp.nl/LogIn3.php?username=%27Stef%27&password=%27Hallo%27
        //username = Stef
        //password = Hallo
        String url = "http://stefp.nl/LogIn3.php";
        String p1 = "?username='" + username + "'";
        String p2 = "&password='" + password + "'";
        String totalURL = url + p1 + p2;
        //String totalURL = "http://stefp.nl/LogIn3.php?username='Stef'&password='Hallo'";
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(totalURL);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        json = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            json = sb.toString();

        } catch (Exception e) {
            // Oops
        }
        finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception squish) {
            }
        }
    }
}