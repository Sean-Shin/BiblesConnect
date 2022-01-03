package sean.to.readbiblesmart.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;

import sean.to.readbiblesmart.MainActivity;
import sean.to.readbiblesmart.R;
import sean.to.readbiblesmart.data.BibleModel;
//
// Pattern : book chapters:verses
//<br>'Genesis 1-4' or 'Genesis 3:5-10'
public class BibleUtil {
    public String[] parseQuery(String query){

        if(query.length() > 0 && query.charAt(0) == '1'){
            query = query.replaceFirst("1 ","1_");
            new Util().printLog("new query", query);
        }
        else if(query.length() > 0 && query.charAt(0) == '2'){
            query = query.replaceFirst("2 ","2_");
        }
        else if(query.length() > 0 && query.charAt(0) == '3'){
            query = query.replaceFirst("3 ","3_");
        }
        String[] splitStr = query.split("\\s+");

        String[] newsplitStr = new String[3];
        if(query.length() > 0)
            splitStr[0] = splitStr[0].replace('_',' ');

        if(splitStr == null)
            return null;

        switch(splitStr.length){
            case 3 :
                newsplitStr[0] =  splitStr[0];
                newsplitStr[1] =  splitStr[1];
                newsplitStr[2] =  splitStr[2];
                break;
            case 2 :
                newsplitStr[0] =  splitStr[0];
                newsplitStr[1] =  splitStr[1];
                newsplitStr[2] =  "1";
                break;
            case 1 :
                newsplitStr[0] =  splitStr[0];
                newsplitStr[1] =  "1";
                newsplitStr[2] =  "1";
                break;

        }
        return newsplitStr;
    }
    //        bible : item.bible,
//        chapter : item.chapter,
//        verse : item.verse,
//        body : item.body

    public BibleModel readJsonFile(String bibleType, String bible, String chapter, String verse){
        String lChapter = "1";
        String lVerse = "1";
        try {
//            new Util().printLog("json read", bible + " " + chapter + " " + verse);

            String validBible = new BibleModel().getBibleIndex(bible);
            // get JSONObject from JSON file
//            JSONObject obj = new JSONObject(loadJSONFromAsset(bibleType));
            JSONObject obj = loadBilble(bibleType);

            // fetch JSONArray named users
            if(obj == null) return null;

            JSONObject objBible = obj.getJSONObject(validBible);
            if(objBible == null) return null;

            if(chapter != null){
                lChapter = chapter;

            }
            JSONObject objChapter = objBible.getJSONObject(lChapter);
            if(objChapter == null){
                return null;
            }


            if(verse != null){
                lVerse = verse;
            }

            String body = objChapter.getString(lVerse);
            new Util().printLog("json body", body);

            if(body != null){
                return new BibleModel("["+bibleType.toUpperCase() + "] " +bible +" "+ lChapter + " "+ lVerse , body);
            }

//            Iterator x = obj.keys();
//            JSONArray jsonArray = new JSONArray();
//
//            while (x.hasNext()){
//                String key = (String) x.next();
//                jsonArray.put(obj.get(key));
//            }


//            JSONArray userArray = obj.toJSONArray(obj.names());
//            new Util().printLog("json names", userArray.toString());
//            JSONArray songsArray = songsObject.toJSONArray(songsObject.names());


            // implement for loop for getting users list data
//            for (int i = 0; i < jsonArray.length(); i++) {
//                // create a JSONObject for fetching single user data
//                JSONObject userDetail = jsonArray.getJSONObject(i);
//                if(userDetail != null)
//                    new Util().printLog("json", userDetail.getString("1"));
//                // fetch email and name and store it in arraylist
////                personNames.add(userDetail.getString("name"));
////                emailIds.add(userDetail.getString("email"));
//                // create a object for getting contact data from JSONObject
////                JSONObject contact = userDetail.getJSONObject("contact");
//                // fetch mobile number and store it in arraylist
////                mobileNumbers.add(contact.getString("mobile"));
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
    public String loadJSONFromAsset(String bibleType){
        String result = "";
        switch(bibleType.toLowerCase()){
            case "esv":
                result = loadJSONFromAsset(R.raw.esv2_upload);
                break;
            case "kjv":
                result = loadJSONFromAsset(R.raw.kjv2_upload);
                break;
            case "niv":
                result = loadJSONFromAsset(R.raw.niv2_upload);
                break;
            case "nlt":
                result = loadJSONFromAsset(R.raw.nlt2_upload);
                break;
            case "rvr":
                result = loadJSONFromAsset(R.raw.es2f_upload);
                break;
            case "ost":
                result = loadJSONFromAsset(R.raw.fr2f_upload);
                break;
            default:
                break;
        }
        return result;
    }
    public String loadJSONFromAsset(int rid) {
        String json = null;
        try {
//            InputStream is = getAssets().open("users_list.json");
            InputStream is =  MainActivity.context.getResources().openRawResource(rid);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public ArrayList<BibleModel> readBible(String bible, String chapter, String verse, ArrayList<BibleModel> data) {
        BibleModel  esv = readJsonFile("esv", bible, chapter,verse);
        data.add(esv);
        BibleModel  kjv = readJsonFile("kjv", bible, chapter,verse);
        data.add(kjv);
        BibleModel  niv = readJsonFile("niv", bible, chapter,verse);
        data.add(niv);
        BibleModel  nlt = readJsonFile("nlt", bible, chapter,verse);
        data.add(nlt);
        return data;
    }
    public ArrayList<BibleModel> readBible(String bible, String chapter, String verse, ArrayList<BibleModel> data, ArrayList<String> books) {

        int length = books.size();

        if(length >= 4) {
            data.add(readJsonFile(books.get(0), bible, chapter, verse));
            data.add(readJsonFile(books.get(1), bible, chapter, verse));
            data.add(readJsonFile(books.get(2), bible, chapter, verse));
            data.add(readJsonFile(books.get(3), bible, chapter, verse));
        }
        if(length >= 6) {
            data.add(readJsonFile(books.get(4), bible, chapter, verse));
            data.add(readJsonFile(books.get(5), bible, chapter, verse));
        }

        return data;
    }
    public ArrayList<String> reorderBible(ArrayList<String> books, int oldIndex, int newIndex){
        String bm = books.get(oldIndex);
        books.remove(oldIndex);
        books.add(newIndex, bm);
        return books;
    }
    public String readBible(String book, String bible, String chapter, String verse){
        BibleModel  result = readJsonFile(book, bible, chapter,verse);
        return result.getBody();
    }
    public JSONObject loadBilble(String bibleType){
        JSONObject obj = null;
        switch (bibleType){
            case "esv":
                obj = MainActivity.esvBibleObject;
                break;
            case "kjv":
                obj = MainActivity.kjvBibleObject;
                break;
            case "niv":
                obj = MainActivity.nivBibleObject;
                break;
            case "nlt":
                obj = MainActivity.nltBibleObject;
                break;
            case "rvr": //es
                obj = MainActivity.esBibleObject;
                break;
            case "ost": //fr
                obj = MainActivity.frBibleObject;
                break;
        }
        return obj;
    }
    public String readBody(String query){
        return readBody(query, false);
    }
    public String readBody(String query, boolean hasTitle){
        String book = "esv";
        String title = query;

        if(query == null)
            return "";

        int index = 6;
        if(query.startsWith("[ESV]")){
            query = query.substring(index);
            new Util().printLog("=========",query);
        }
        if(query.startsWith("[KJV]")){
            query = query.substring(index);
            book = "kjv";
            new Util().printLog("=========",query);
        }
        if(query.startsWith("[NIV]")){
            query = query.substring(index);
            book = "niv";
            new Util().printLog("=========",query);
        }
        if(query.startsWith("[NLT]")){
            query = query.substring(index);
            book = "nlt";
            new Util().printLog("=========",query);
        }
        if(query.startsWith("[RVR]")){
            query = query.substring(index);
            book = "rvr";
            new Util().printLog("=========",query);
        }
        if(query.startsWith("[OST]")){
            query = query.substring(index);
            book = "ost";
            new Util().printLog("=========",query);
        }
        String[] result = new BibleUtil().parseQuery(query);
        String body = new BibleUtil().readBible(book,result[0], result[1], result[2]);

        if(hasTitle)
            return title + "\n\n"+ body;
        else
            return body;
    }
    public String readVerse(String query){
        return  readBody(query, true);
    }

}
