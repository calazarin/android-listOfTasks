package androidfactory.lazarinapps.com.listoftasks.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void createToastAndShowIt(Context context, String message, int duration){
        Toast.makeText(context,message, duration).show();
    }

}
