package backend;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;
import pojos_exceptions.IncorrectArticleException;

public class DataAPI {
    
    private static MongoClient client;
    private static MongoDatabase db;
    private static String dbName;
    
    public DataAPI(){
        DataAPI.dbName = "act5_3";
    }
    public DataAPI(String dbName){
        DataAPI.dbName = dbName;
    }
    
    public static void init(){
        DataAPI.client = new MongoClient();
        DataAPI.db = DataAPI.client.getDatabase(DataAPI.dbName);
        
        CodecRegistry pojoCodecRegistry = 
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),                
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        DataAPI.db = DataAPI.db.withCodecRegistry(pojoCodecRegistry);
    }
    public static void close(){
        DataAPI.client.close();
    }
    
    public static void insertArticle(Article art) throws IncorrectArticleException{
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        
        System.out.println(art.getComments().size());
        
        
        
    }
    
    public static void insertUser(User us){
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        users.insertOne(us);
    }
    
    public static Article findArticle(ObjectId id){
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("_id",id);
        Article articleRes = (Article) articles.find(filter);
        return articleRes;
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
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("address.country",country);
        FindIterable<User> usersRes = (FindIterable<User>) users.find(filter);
        return usersRes;
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
        
       // boolean usersExists = true;
        //for (int i = 0; i < art.getComments().size(); i++) {
        //    Bson filter = eq("_id",art.getComments().get(i).getId_user());
        //    //FindIterable<User> res = users.find(filter);
        //    System.out.println(res);
        //    if(res == null){
        //        usersExists=false;
        //        continue;
        //    }
        //}
        
        //if(usersExists){
        //    comm.insertOne(art);
        //}else{
        //    throw new IncorrectArticleException("At least one user of the article comments does not exist. Make sure they exist in the database");
        //}
    }
    /**
       Relete an article.
    */
    public static void deleteArticle(Article art){
        System.out.println("\n\n---deleteArticle---");
        MongoCollection<Article> articles = DataAPI.db.getCollection("users", Article.class);
        Bson filter = eq("_id",art.getId());
        articles.deleteOne(filter);
    }
    /**
       Relete a user and also all the comments whose author is the user.
    */
    public static void deleteUser(User us){
    }

}
