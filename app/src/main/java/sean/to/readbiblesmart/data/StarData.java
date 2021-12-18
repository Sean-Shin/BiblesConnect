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

public class StarData {
    public static JSONObject starList;
    private static String FILE_NAME = "starlist.txt";
    public StarData(){
        starList = new JSONObject();
    }

    public void setFileName(String name){
        FILE_NAME = name;
    }
    public void setStar(String title, boolean onoff){
        try {
            starList.put(title,onoff);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void setStar(String title){
        setStar(title,true);
    }
    public void setStar(String query, String bible){
        try{
            int result = starList.getInt(query);
            if(bible.toLowerCase().equals("esv")){
                // 0001 = 1
               result = result | 1 ;
            }
            if(bible.toLowerCase().equals("kjv")){
                // 0010 = 2
                result = result | 2 ;
            }
            if(bible.toLowerCase().equals("niv")){
                // 0100 = 4
                result = result | 4 ;
            }
            if(bible.toLowerCase().equals("nlt")){
                // 1000 = 8
                result = result | 8 ;
            }
            starList.put(query,result);
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
    public void saveStarData(Context context){
        // Convert JsonObject to String Format
        String userString = starList.toString();
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
    public void readStarData(Context context){
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
            starList = jsonObject;

        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }

    }
    public boolean isStar(String title){

        if(starList == null ) return false;
        if(starList.length() == 0) return false;

        try{
            return starList.has(title) ? starList.getBoolean(title) : false;
//            Object object = starList.get(title);
//            if(object instanceof String){
//                if(object.toString().toLowerCase().equals("false")){
//                    return false;
//                }else return true;
//            }else{
//                return starList.getBoolean(title);
//            }

        }catch (JSONException e){
            e.printStackTrace();
            return false;
        }
//        return false;
    }
    public boolean isStar(String query, String bible){
        try {
            int result = starList.getInt(query);
            if(bible.toLowerCase().equals("esv")){
                //1111 = 8 + 4 + 2 + 1 = 15
                if((result & 1) == 1) // 0001 = 1
                    return true;
            }
            if(bible.toLowerCase().equals("kjv")){
                //1111 = 8 + 4 + 2 + 1 = 15
                if((result & 2) == 2) // 0010 = 2
                    return true;
            }
            if(bible.toLowerCase().equals("niv")){
                //1111 = 8 + 4 + 2 + 1 = 15
                if((result & 4) == 4) // 0100 = 4
                    return true;
            }
            if(bible.toLowerCase().equals("nlt")){
                //1111 = 8 + 4 + 2 + 1 = 15
                if((result & 8) == 8) // 1000 = 8
                    return true;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return false;
    }
}
