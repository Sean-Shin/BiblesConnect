package sean.to.readbiblesmart.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import sean.to.readbiblesmart.util.Util;

public class NotesData  {
    public  static JSONObject notelist;
    private static String FILE_NAME = "noteslist.txt";
    public NotesData(){

        notelist = new JSONObject();
    }
    public JSONObject putNotes(String title, String note, JSONObject list){
        try {
            JSONObject previousObject = list.has(title) ? list.getJSONObject(title) : null;

            if(previousObject != null){
                int total = previousObject.getInt("total");
                previousObject.put("total",total+1);

                JSONObject bodyObject = new JSONObject();
                bodyObject.put("body",note);
                bodyObject.put("date", new Util().getToday());
                previousObject.put(Integer.toString(total+1), bodyObject);

                list.put(title,previousObject);

                new Util().printLog("--put--", list.toString());

            }else{
                addNotes(note,title);
            }
            return list;

        }catch (JSONException e){
            addNotes(note,title);
            e.printStackTrace();
            return null;
        }
    }
    public void delNotes(String title, int index){
        try {
            JSONObject previousObject = notelist.has(title) ? notelist.getJSONObject(title) : null;

            if(previousObject != null){
                int total = previousObject.getInt("total");

                if(total > 0){
                    previousObject.put("total",total-1);
                    String keyIndex = Integer.toString(index);
                    previousObject.remove(keyIndex);

                    int newIndex = 0;
                    JSONObject newObject = new JSONObject();
                    newObject.put("total", total -1);
                    for(int i=0; i < total;i++){
                        String key= Integer.toString(i+1);
                        if(!keyIndex.equals(key)){
                            newIndex++;
                            JSONObject jobj = previousObject.getJSONObject(key);
                            newObject.put(Integer.toString(newIndex),jobj);
                        }
                    }

                    notelist.remove(title);
                    notelist.put(title,newObject);

                    new Util().printLog("--del--", notelist.toString());

                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void putNotes(String title, String note){

        try {
            JSONObject previousObject = notelist.has(title) ? notelist.getJSONObject(title) : null;

            if(previousObject != null){
                int total = previousObject.getInt("total");
                previousObject.put("total",total+1);

                JSONObject bodyObject = new JSONObject();
                bodyObject.put("body",note);
                bodyObject.put("date", new Util().getToday());
                previousObject.put(Integer.toString(total+1), bodyObject);

                notelist.put(title,previousObject);

                new Util().printLog("--put--", notelist.toString());

            }else{
                addNotes(note,title);
            }

        }catch (JSONException e){
            addNotes(note,title);
            e.printStackTrace();
        }
    }
    public int hasHowmanynotes(String title){
        try {
            new Util().printLog("-notes--how-", notelist.toString());
            JSONObject previousObject = notelist.has(title) ? notelist.getJSONObject(title) : null;
            if (previousObject != null) {
//                int total = 0;
//                Object object = previousObject.get("total");
//                if(object instanceof String)
//                    total = Integer.parseInt(object.toString());
//                else
//                total = previousObject.getInt("total");
                return previousObject.has("total") ? previousObject.getInt("total") : 0;
            }
        }catch (JSONException e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
    public String expressionNotes(int notes){
        if(notes == 0)
            return "new note";
        else if(notes == 1)
            return "" +  notes + " note";
        else
            return "" +  notes + " notes";
    }
    private void addNotes(String note, String title){
        try{
            JSONObject noteObject = new JSONObject();
            noteObject.put("total", 1);

            JSONObject bodyObject = new JSONObject();
            bodyObject.put("body",note);
            bodyObject.put("date", new Util().getToday());

            noteObject.put("1", bodyObject);

            notelist.put(title,noteObject);
            new Util().printLog("---add-", notelist.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public JSONObject getNotes(String title){
        try {
            JSONObject previousObject = notelist.has(title) ? notelist.getJSONObject(title) : null;
            return previousObject;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public void saveNoteData(Context context){
        // Convert JsonObject to String Format
        String userString = notelist.toString();
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
    public void readNoteData(Context context){
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
            notelist = jsonObject;

        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }

    }
}
