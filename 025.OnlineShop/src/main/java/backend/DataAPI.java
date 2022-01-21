package backend;

//Mongo
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

//Pojos Utilities
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

//Pojos
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;

//Pojos Exceptions
import pojos_exceptions.IncorrectArticleException;

//Java
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DataAPI {

    private static MongoClient client;
    private static MongoDatabase db;
    private static String dbName;

    public DataAPI() {
        DataAPI.dbName = "act5_3";
    }

    public DataAPI(String dbName) {
        DataAPI.dbName = dbName;
    }

    public static void init() {
        DataAPI.client = new MongoClient();
        DataAPI.db = DataAPI.client.getDatabase(DataAPI.dbName);

        CodecRegistry pojoCodecRegistry
                = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        DataAPI.db = DataAPI.db.withCodecRegistry(pojoCodecRegistry);
    }

    public static void close() {
        DataAPI.client.close();
    }

    public static void insertArticle(Article art) throws IncorrectArticleException {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        articles.insertOne(art);

    }

    public static void insertUser(User us) {
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        users.insertOne(us);
    }

    public static Article findArticle(ObjectId id) {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("_id", id.toString());
        Article articleRes = (Article) articles.find(filter);
        return articleRes;
    }

    /**
     * Returns all the articles that are of the category cat.
     */
    public static FindIterable<Article> findArticleByCategory(String cat) {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("categories", cat);
        FindIterable<Article> articlesResult = articles.find(filter);
        return articlesResult;
    }

    /**
     * Returns all the articles that contains str in its name.
     */
    public static FindIterable<Article> findArticleByName(String str) {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Pattern strRegex = Pattern.compile("/i", Pattern.CASE_INSENSITIVE);
        Bson filter = eq("name", strRegex);
        FindIterable<Article> articlesResult = articles.find(filter);
        return articlesResult;
    }

    /**
     * Returns all the articles whose price is in the rank [low, high], both
     * inclusive.
     */
    public static FindIterable<Article> findArticleInPriceRank(double low, double high) {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = and(gte("price", low), lte("price", high));
        FindIterable<Article> articlesResult = articles.find(filter);
        return articlesResult;
    }

    public static User findUser(ObjectId id) {
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", id.toString());
        User userRes = (User) users.find(filter);
        return userRes;
    }

    /**
     * Returns all the user who live in the country specified as argument.
     */
    public static FindIterable<User> findUserByCountry(String country) {
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("address.country", country);
        FindIterable<User> usersRes = (FindIterable<User>) users.find(filter);
        return usersRes;
    }

    /**
     * Receives a FindIterable<Article> object and returns it ordered by price
     * ascending or descending as specified as argument.
     */
    public static FindIterable<Article> orderByPrice(FindIterable<Article> arts, boolean asc) {
        FindIterable<Article> artsOrdered;
        if (asc) {
            artsOrdered = arts.sort(ascending("price"));
        } else {
            artsOrdered = arts.sort(descending("price"));
        }
        return artsOrdered;
    }

    public static void updateAddress(User us, Address ad) {
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", us.getId());
        Bson update = set("address",ad);
        users.updateOne(filter,update); 
    }

    public static void updateEmail(User us, String email) {
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", us.getId());
        Bson update = set("email",email);
        users.updateOne(filter,update);
    }

    public static void addComment(Article art, Comment newCom) {
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("_id",art.getId());
        Bson update = push("comments",newCom);
        articles.updateOne(filter, update);
    }

    /**
     * Delete an article.
     */
    public static void deleteArticle(Article art) {
        System.out.println("\n\n---deleteArticle---");
        MongoCollection<Article> articles = DataAPI.db.getCollection("users", Article.class);
        Bson filter = eq("_id", art.getId());
        articles.deleteOne(filter);
    }

    /**
     * Delete a user and also all the comments whose author is the user.
     */
    public static void deleteUser(User us) {
        System.out.println("\n\n---deleteUser---");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        
        
        Bson filter = eq("comments.id_user", us.getId());
        Bson update = null;
        articles.updateMany(filter,update);
        FindIterable<Article> articlesRes = articles.find(filter);
        
        
        for (Article article : articlesRes) {
            List<Comment> comments = article.getComments();
            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i).getId_user() == us.getId()) {
                    comments.remove(i);
                }
            }
        }
        Bson filterUser = eq("_id", us.getId());
        users.deleteOne(filterUser);
    }

}
