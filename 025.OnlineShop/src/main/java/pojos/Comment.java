package pojos;

import org.bson.types.ObjectId;
import pojos_exceptions.IncorrectCommentException;

public class Comment {
    private int score;
    private String text;
    private ObjectId id_user;
    
    public Comment(){
    }
    
    public Comment(int score, String text, ObjectId id_user) {
        if(score > 5 || score < 0){
            new IncorrectCommentException("The score of the comment must be a value between 0 and 5");
        }else{
            this.score = score;
        }
        
        this.text = text;
        this.id_user = id_user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score > 5 || score < 0){
            new IncorrectCommentException("The score of the comment must be a value between 0 and 5");
        }else{
            this.score = score;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ObjectId getId_user() {
        return id_user;
    }

    public void setId_user(ObjectId id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Comments{ score=" + score + ", text=" + text + ", id_user=" + id_user + '}';
    }
    
    
}
