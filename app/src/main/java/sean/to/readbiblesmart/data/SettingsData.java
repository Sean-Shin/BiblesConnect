package sean.to.readbiblesmart.data;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsData {
    public JSONObject settingsList;
    private static String FILE_NAME = "settings.txt";
    public SettingsData(){
        settingsList = new JSONObject();
    }
    public void setSettings(String name, boolean onoff){
        try {
            settingsList.put(name,onoff);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void saveSettingsData(Context context){
        // Convert JsonObject to String Format
        String userString = settingsList.toString();
// Define the File Path and its Name
        File file = new File(context.getFilesDir(),FILE_NAME);
        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void readSettingsData(Context context){
        File file = new File(context.getFilesDir(),FILE_NAME);
        if(file == null) return;

        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            // This responce will have Json Format String
            String responce = stringBuilder.toString();
            JSONObject jsonObject  = new JSONObject(responce);
            settingsList = jsonObject;

        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }

    }
    public boolean isSettings(String name){

        if(settingsList == null ) return true;
        if(settingsList.length() == 0) return true;

        try{

            return settingsList.has(name) ? settingsList.getBoolean(name) : (isEnglishVersion(name) ? true : false);
        }catch (JSONException e){
            e.printStackTrace();

            return isEnglishVersion(name) ? true : false;
        }
//        return false;
    }
    private boolean isEnglishVersion(String name){
        boolean ret = true;
        switch (name){
            case "esv" :
                break;
            case "kjv" :
                break;
            case "niv" :
                break;
            case "nlt" :
                break;
            default:
                ret = false;
                break;
        }
        return ret;
    }
}
