package backend;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;

public class DataAPI {
    
    private static MongoClient client;
    private static MongoDatabase db;
    
    public DataAPI(){
    }
    
    public static void init(){
        
    }
    public static void close(){
        
    }
    
    public static void insertArticle(Article art){
    
    }
    
    public static void insertUser(User us){
        
    }
    
    public static Article findArticle(ObjectId id){
        return null;
    }
    /**
        Returns all the articles that are of the category cat.
    */
    public static FindIterable<Article> findArticleByCategory(String cat){
        return null;
    }
    /**
       Returns all the articles that contains str in its name.
    */
    public static FindIterable<Article> findArticleByName(String str){
        return null;
    }
    /**
       Returns all the articles whose price is in the rank [low, high], both inclusive.
    */
    public static FindIterable<Article> findArticleInPriceRank(double low, double high){
        return null;
    }
    public static User findUser(ObjectId id){
        return null;
    }
    /**
       Returns all the user who live in the country specified as argument.
    */
    public static FindIterable<User> findUserByCountry(String country){
        return null;
    }
    /**
       Receives a FindIterable<Article> object and returns it ordered by price ascending or descending as specified as argument.
    */
    public static FindIterable<Article> orderByPrice(FindIterable<Article> arts, boolean asc){
        return null;
        
    }
    public static void updateAddress (User us, Address ad){
        
    }
    public static void updateEmail(User us, String email){
    }
    public static void addComment(Article art, Comment newCom){
    }
    /**
       Relete an article.
    */
    public static void deleteArticle(Article art){
    }
    /**
       Relete a user and also all the comments whose author is the user.
    */
    public static void deleteUser(User us){
    }

}
