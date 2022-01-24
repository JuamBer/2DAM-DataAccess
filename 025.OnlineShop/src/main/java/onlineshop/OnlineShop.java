package onlineshop;

//Mongo
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
//API
import backend.DataAPI;

//Exceptions
import pojos_exceptions.IncorrectCommentException;
import java.io.IOException;

//Pojos
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;

//Java
import java.util.Arrays;
import org.bson.types.ObjectId;

public class OnlineShop {
    
    private static DataAPI api;
    
    public static void main(String[] args) throws IncorrectCommentException, IOException {
        OnlineShop.dropDatabase();
        OnlineShop.insertDemoData();
        
        api = new DataAPI();
        
        //init
        api.init();
        
        //insertArticle
        Article newArticle = new Article("Test Article Title",95.0,Arrays.asList("CategoryTest1","CategoryTest2"));
        Article newArticle2 = new Article("Other Test Article Title",300.60,Arrays.asList("CategoryTest1","CategoryTest2"));
        api.insertArticle(newArticle);
        api.insertArticle(newArticle2);
        
        //insertUser
        User newUser = new User("Test User Name","email@test.com",new Address("Test Direcction",1,"TestCity","TextCountry"));
        User newUser2 = new User("Other Test User Name","email@test.com",new Address("Other Test Direcction",4,"TestCity","TextCountry"));
        api.insertUser(newUser);
        api.insertUser(newUser2);
       
        //findArticle
        Article recoveredArticle = api.findArticle(newArticle.getId());
        
        //findArticleByCategory
        FindIterable<Article> articlesByCategory = api.findArticleByCategory("Smartphone");
        
        //findArticleByName
        FindIterable<Article> articlesByName = api.findArticleByName("oo");
        
        //findArticleInPriceRank
        FindIterable<Article> articlesByPriceRank = api.findArticleInPriceRank(500,1500);
        
        //findUser
        System.out.println("findUser");
        User recoveredUser = api.findUser(newUser.getId());
        System.out.println("\tUser: "+recoveredUser);
        
        //findUserByCountry
        FindIterable<User> usersByCountry = api.findUserByCountry("España");
        
        //orderByPrice
        FindIterable<Article> ArticlesOrderedByPrice = api.orderByPrice(articlesByCategory,false);
        
        //updateAddress
        Address updatedAddress = new Address("Update Address!",1,"TestCity","TextCountry");
        api.updateAddress(newUser,updatedAddress);
        
        //updateEmail
        String updatedEmail = "updated@email.com";
        api.updateEmail(newUser,updatedEmail);
        
        //addComment
        try{  
            Comment newComment = new Comment(4,"New Comment !", newUser.getId());
            api.addComment(newArticle2,newComment);
        }catch(IncorrectCommentException ex){
            System.out.println("\tIncorrectCommentException: "+ex.getMessage());
        }
        try{
            Comment incorrectComment1 = new Comment(6,"New Comment !", newUser.getId());
            api.addComment(newArticle2,incorrectComment1);
        }catch(IncorrectCommentException ex){
            System.out.println("\tIncorrectCommentException: "+ex.getMessage());
        }    
        try{
            Comment incorrectComment2 = new Comment(3,"New Comment !", new ObjectId());
            api.addComment(newArticle2,incorrectComment2);
        }catch(IncorrectCommentException ex){
            System.out.println("\tIncorrectCommentException: "+ex.getMessage());
        }
        
        //deleteArticle
        api.deleteArticle(newArticle);
        
        //deleteUser
        api.deleteUser(newUser);
        
        //close
        api.close();
    }
    
    
    public static void insertDemoData() throws IOException{
        
        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("act5_3");
        MongoCollection<Document> users = database.getCollection("users");
        MongoCollection<Document> articles = database.getCollection("articles");
        
        Document user1 = Document.parse("{\n" +
        "        \"email\": \"contacto@juamber.com\",\n" +
        "        \"name\": \"Juan Sáez García\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Valencia\",\n" +
        "            \"country\": \"España\",\n" +
        "            \"number\": 5,\n" +
        "            \"street\": \"Plaza Musico Ramon Ibars\"\n" +
        "        }\n" +
        "    }");
        Document user2 = Document.parse("{\n" +
        "        \"email\": \"franciscojavier.gamez@salgado.es\",\n" +
        "        \"name\": \"Francisco Javier\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Madrid\",\n" +
        "            \"country\": \"España\",\n" +
        "            \"number\": 8,\n" +
        "            \"street\": \"Paseo Galindo\"\n" +
        "        }\n" +
        "    }");
        Document user3 = Document.parse("{\n" +
        "        \"email\": \"corlina.velaz2@gmail.com\",\n" +
        "        \"name\": \"Carolina Velázquez Segundo\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Valencia\",\n" +
        "            \"country\": \"España\",\n" +
        "            \"number\": 92,\n" +
        "            \"street\": \"Ronda Jordi\"\n" +
        "        }\n" +
        "    }");
        Document user4 = Document.parse("{\n" +
        "        \"email\": \"fat_palacios@hotmail.com\",\n" +
        "        \"name\": \"Fátima Palacios\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Barcelona\",\n" +
        "            \"country\": \"España\",\n" +
        "            \"number\": 64,\n" +
        "            \"street\": \"Carrer Vega\"\n" +
        "        }\n" +
        "    }");
        Document user5 = Document.parse("{\n" +
        "        \"email\": \"cristian45@vega.com\",\n" +
        "        \"name\": \"Cristian García\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Touluse\",\n" +
        "            \"country\": \"Francia\",\n" +
        "            \"number\": 23,\n" +
        "            \"street\": \"Avenida Ariadna\"\n" +
        "        }\n" +
        "    }");
        Document user6 = Document.parse("{\n" +
        "        \"email\": \"carla59@live.com\",\n" +
        "        \"name\": \"Carla Delao\",\n" +
        "        \"address\": {\n" +
        "            \"city\": \"Madrid\",\n" +
        "            \"country\": \"España\",\n" +
        "            \"number\": 3,\n" +
        "            \"street\": \"Travessera Raquel\"\n" +
        "        }\n" +
        "    }");
        
        users.insertMany(Arrays.asList(user1,user2,user3,user4,user5,user6));
        
        Document article1 = Document.parse("{\n" +
        "        \"name\": \"Google Pixel 6 Pro\",\n" +
        "        \"price\": 1180.23,\n" +
        "        \"categories\": [\"Smartphone\", \"Electronic\", \"Google\"]\n" +
        "    }");
        Document article2 = Document.parse("{\n" +
        "        \"name\": \"MacBook Pro 13' 2020\",\n" +
        "        \"price\": 1602.62,\n" +
        "        \"categories\": [\"Laptop\", \"Work\" ,\"Electronic\", \"Apple\"]\n" +
        "    }");
        Document article3 = Document.parse("{\n" +
        "        \"name\": \"Iphone 11 128GB\",\n" +
        "        \"price\": 553.0,\n" +
        "        \"categories\": [\"Smartphone\", \"Electronic\", \"Apple\"]\n" +
        "    }");
        Document article4 = Document.parse("{\n" +
        "        \"name\": \"Sony WH1000XM3\",\n" +
        "        \"price\": 221.96,\n" +
        "        \"categories\": [\"Audio\", \"Professional\", \"Electronic\", \"Sony\"]\n" +
        "    }");
        
        articles.insertMany(Arrays.asList(article1,article2,article3,article4));
    }
    public static void dropDatabase(){
        MongoClient client = new MongoClient();
        client.getDatabase("act5_3").drop();
    }
}
