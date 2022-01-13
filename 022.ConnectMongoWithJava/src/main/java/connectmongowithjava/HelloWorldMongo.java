
package connectmongowithjava;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.Arrays;
import org.bson.Document;


public class HelloWorldMongo {
    public static void main(String[] args) {
        
        MongoClient client = new MongoClient();

        MongoIterable<String> names = client.listDatabaseNames(); 
        String res="Available Databases"; 
        for (String string : names) {
            res += string+"; ";
        }
        
        System.out.println("\n\n");
        System.out.println("listDatabaseNames: "+res);
        
        MongoDatabase db = client.getDatabase("mflix");
        System.out.println("\n\n");
        System.out.println("Database Name: "+db.getName());
        MongoIterable<String> listCollectionsNames = db.listCollectionNames();
        for(String collectionName : listCollectionsNames){
            System.out.println(collectionName);
        }
        
        MongoCollection<Document> movies = db.getCollection("movies");
        
        Document doc1 = new Document()
                .append("title","Lord Of The Rings (NEW)")
                .append("genres", Arrays.asList("Adventure","Fantasy"))
                .append("type","Movie")
                .append("rated","+13")
                .append("year",2001)
                .append("director","Peter Jackson");
        
        System.out.println("Num docs: "+movies.countDocuments());    
        System.out.println("INSERT ONE");
        //movies.insertOne(doc1);
        System.out.println("Num docs: "+movies.countDocuments());   
        
        Document doc2 = new Document()
                .append("title","Data Access")
                .append("genres","Drama");
        
        Document doc3 = new Document()
                .append("title","Fake Movie")
                .append("genres","Comedy");
        
        
        System.out.println("INSERT MANY");
        //movies.insertMany(Arrays.asList(doc2,doc3));
        System.out.println("Num docs: "+movies.countDocuments());   
        
        client.close();

    }
}
