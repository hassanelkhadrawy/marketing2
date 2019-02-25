package www.myapp.com.marketing.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Hassan_Abo_Ali on 22/02/2018.
 */

public class checkInternet {
    private Context context;

    public checkInternet(Context context) {
        this.context = context;
    }
    public boolean isconnectToInternet(){

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
   if (connectivityManager!=null){

       NetworkInfo info=connectivityManager.getActiveNetworkInfo();
       if (info!=null&&info.isConnected()){
           return true;

       }

   }
   return false;
    }

}
