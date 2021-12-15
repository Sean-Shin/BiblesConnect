package sean.to.readbiblesmart.data;

public class NoteModel {
    String body;
    String date;
    String id;
    public NoteModel(String body, String date, String id){
        this.body = body;
        this.date = date;
        this.id = id;
    }
    public String getBody(){
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
