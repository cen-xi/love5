package util;

public class Photo {
    private String name;
    private int Imageid;

    public Photo(String name,int imageid){
        this.name = name;
        this.Imageid = imageid;

    }

    public String getName() {
        return name;
    }

    public int getImageid() {
        return Imageid;
    }
}
