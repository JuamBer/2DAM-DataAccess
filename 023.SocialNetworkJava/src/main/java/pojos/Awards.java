package pojos;

import org.bson.types.ObjectId;

public class Awards {
    
    private ObjectId id;
    private int wins;
    private int nominations;
    private String text;
    
    public Awards() {
        this.id = new ObjectId();
    }
    
    public Awards(int wins, int nominations, String text) {
        this.id = new ObjectId();
        this.wins = wins;
        this.nominations = nominations;
        this.text = text;
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getNominations() {
        return nominations;
    }

    public void setNominations(int nominations) {
        this.nominations = nominations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Awards{" + "id=" + id + ", wins=" + wins + ", nominations=" + nominations + ", text=" + text + '}';
    }

    
}
