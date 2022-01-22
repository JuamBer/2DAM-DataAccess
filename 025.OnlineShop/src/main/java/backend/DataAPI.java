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
import pojos_exceptions.IncorrectCommentException;

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
        System.out.println("insertArticle");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        articles.insertOne(art);
        System.out.println("\tArticle Inserted: "+art);

    }

    public static void insertUser(User us) {
        System.out.println("insertUser");
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        users.insertOne(us);
        System.out.println("\tUser Inserted: "+us);
    }

    public static Article findArticle(ObjectId id) {
        System.out.println("findArticle");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("_id", id);
        Article articleRes = (Article) articles.find(filter);
        System.out.println("\t"+articleRes);
        return articleRes;
    }

    /**
     * Returns all the articles that are of the category cat.
     */
    public static FindIterable<Article> findArticleByCategory(String cat) {
        System.out.println("findArticleByCategory");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("categories", cat);
        FindIterable<Article> articlesResult = articles.find(filter);
        for (Article article : articlesResult) {
            System.out.println("\t"+article);
        }
        return articlesResult;
    }

    /**
     * Returns all the articles that contains str in its name.
     */
    public static FindIterable<Article> findArticleByName(String str) {
        System.out.println("findArticleByName");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Pattern strRegex = Pattern.compile("/i", Pattern.CASE_INSENSITIVE);
        Bson filter = eq("name", strRegex);
        FindIterable<Article> articlesResult = articles.find(filter);
        for (Article article : articlesResult) {
            System.out.println("\t"+article);
        }
        return articlesResult;
    }

    /**
     * Returns all the articles whose price is in the rank [low, high], both
     * inclusive.
     */
    public static FindIterable<Article> findArticleInPriceRank(double low, double high) {
        System.out.println("findArticleInPriceRank");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = and(gte("price", low), lte("price", high));
        FindIterable<Article> articlesResult = articles.find(filter);
        for (Article article : articlesResult) {
            System.out.println("\t"+article);
        }
        return articlesResult;
    }

    public static User findUser(ObjectId id) {
        System.out.println("findUser");
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", id);
        User userRes = (User) users.find(filter);
        System.out.println("\t"+userRes);
        return userRes;
    }

    /**
     * Returns all the user who live in the country specified as argument.
     */
    public static FindIterable<User> findUserByCountry(String country) {
        System.out.println("findUserByCountry");
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("address.country", country);
        FindIterable<User> usersRes = (FindIterable<User>) users.find(filter);
        for (User user : usersRes) {
            System.out.println("\t"+user);
        }
        return usersRes;
    }

    /**
     * Receives a FindIterable<Article> object and returns it ordered by price
     * ascending or descending as specified as argument.
     */
    public static FindIterable<Article> orderByPrice(FindIterable<Article> arts, boolean asc) {
        System.out.println("orderByPrice");
        FindIterable<Article> artsOrdered;
        if (asc) {
            artsOrdered = arts.sort(ascending("price"));
        } else {
            artsOrdered = arts.sort(descending("price"));
        }
        for (Article article : artsOrdered) {
            System.out.println("\t"+article);
        }
        return artsOrdered;
    }

    public static void updateAddress(User us, Address ad) {
        System.out.println("updateAddress");
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", us.getId());
        Bson update = set("address", ad);
        users.updateOne(filter, update);
        System.out.println("\tUpdatedAddress: "+ad+" User: "+us);
    }

    public static void updateEmail(User us, String email) {
        System.out.println("updateEmail");
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);
        Bson filter = eq("_id", us.getId());
        Bson update = set("email", email);
        users.updateOne(filter, update);
        System.out.println("\tUpdatedEmail: "+email+" User: "+us);
    }

    public static void addComment(Article art, Comment newCom) {
        System.out.println("addComment");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        Bson filter = eq("_id", art.getId());
        Bson update = push("comments", newCom);
        articles.updateOne(filter, update);
        System.out.println("\tComment Added: "+newCom);           
    }

    /**
     * Delete an article.
     */
    public static void deleteArticle(Article art) {
        System.out.println("deleteArticle");
        MongoCollection<Article> articles = DataAPI.db.getCollection("users", Article.class);
        Bson filter = eq("_id", art.getId());
        articles.deleteOne(filter);
        System.out.println("\tDeleted Article: "+art);
    }

    /**
     * Delete a user and also all the comments whose author is the user.
     */
    public static void deleteUser(User us) {
        System.out.println("deleteUser");
        MongoCollection<Article> articles = DataAPI.db.getCollection("articles", Article.class);
        MongoCollection<User> users = DataAPI.db.getCollection("users", User.class);

        Bson filterComment = eq("comments.id_user", us.getId());
        FindIterable<Article> articlesRes = articles.find(filterComment);

        for (Article article : articlesRes) {
            System.out.println("\tArticles");
            List<Comment> comments = article.getComments();
            for (int i = 0; i < comments.size() - 1; i++) {
                System.out.println("\tComments");
                if (comments.get(i).getId_user().equals(us.getId())) {
                    comments.remove(i);
                    System.out.println("\tDeleted Comment: " + comments.get(i));
                }
            }
        }

        Bson filterUser = eq("_id", us.getId());
        users.deleteOne(filterUser);
        System.out.println("\tDeleted User: " + us);
    }

}
