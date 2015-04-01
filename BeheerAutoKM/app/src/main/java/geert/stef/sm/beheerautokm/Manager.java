package geert.stef.sm.beheerautokm;

import android.content.Context;
import android.content.Intent;

public class Manager {

    public boolean Login(Context c, String username, String password){
        if(username.equals("henk") && password.equals("hallo"))
        {
            Intent intent = new Intent(c, Overview.class);
            c.startActivity(intent);
            return true;
        }
        else
        {
            return false;
        }
    }
}
