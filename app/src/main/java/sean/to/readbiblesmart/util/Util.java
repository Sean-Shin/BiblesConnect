package sean.to.readbiblesmart.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sean.to.readbiblesmart.BuildConfig;
import sean.to.readbiblesmart.R;

public class Util {
    public String getToday(){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }
    public void printLog(String tag,String log){
        if( BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            Log.d("*****bc**** " + tag, log);
        }
    }
    public void toastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
//    public void saveBibleRead(String bible, Activity activity){
//
//        savePrefData(activity.getString(R.string.previousBible),bible,activity);
//    }
//    public String readBibleLast(Activity activity){
//
//        return readPrefData(activity.getString(R.string.previousBible), "Genesis 1 1", activity);
//    }
    public void savePrefData(String key, String value, Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String readPrefData(String key, String defalutValue, Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        return sharedPref.getString(key,defalutValue);
    }

}
