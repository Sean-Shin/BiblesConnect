package sean.to.readbiblesmart.data;

public class BibleModel {
    String name;
    String version;
    String title;
    String body;
    int id_;
//    int image;
    BibleData data;
    public BibleModel(String name, String version, int id_) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
//        this.image=image;
    }
    public BibleModel(BibleData data){
        this.data = data;
    }
    public BibleModel(String title, String body){
        this.title = title;
        this.body = body;
    }
    public BibleModel(){
//        this.data = new BibleData();
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

//    public int getImage() {
//        return image;
//    }

    public int getId() {
        return id_;
    }

    public int getBibleIndex(String bible, int flag){
        if(bible == null)
            return -1;
        for(int i=0; i < 66; i++){
            if(bible.toLowerCase().equals(data.bibleNames[i].toLowerCase()))
                return i;
        }
        return -1;
    }
    public String getBibleIndex(String bible){
        int res = getBibleIndex(bible,0);
        if(res >= 0){
            return data.bibleNames[res];
        }
        return null;
    }
    public int getLastChapter(int index){
        if(data.bibleKeys[index].no == index )
            return data.bibleKeys[index].ch;
        else
            return -1;
    }
    public boolean isValidBibleName(String name){
        int res = getBibleIndex(name,0);
        if(res >= 0 && res < 66){
            return true;
        }
        return false;
    }
    public String getTitle(){
        return title;
    }
    public String getBody(){
        return body;
    }
}
