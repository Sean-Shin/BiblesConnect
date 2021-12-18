package sean.to.readbiblesmart.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public String getToday(){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }
    public void printLog(String tag,String log){
//        Log.d("*****bc**** " + tag, log);
    }
    public void toastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
