package DS;

import java.io.Serializable;

public class Exceptionai extends Exception implements Serializable{
    public Exceptionai(String s, int likeBandymai){
        super(s);
        this.likeBandymai = likeBandymai;
    }
    
    private int likeBandymai = 3;

    public int getLikeBandymai() {
        return likeBandymai;
    }
    
}
