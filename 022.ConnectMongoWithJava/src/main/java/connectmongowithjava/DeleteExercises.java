
package connectmongowithjava;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteExercises {
    public static void main(String[] args) {
        MongoClient client = (MongoClient) new MongoClient();
        MongoDatabase db = client.getDatabase("mflix");
        MongoCollection<Document> movies = db.getCollection("movies");
        Bson filterDoc;
        DeleteResult res;
        
        //1. Delete all the movies without the year file
        filterDoc = exists("year", false);
        res  = movies.deleteMany(filterDoc);
        System.out.println("\nres: "+res);
        
        client.close();
        client = (MongoClient) new MongoClient();
        db = client.getDatabase("new_mflix");
        movies = db.getCollection("movies");
        
        //2. Delete one of all the movies of Fantasy genre
        filterDoc = eq("genres", "Fantasy");
        res = movies.deleteOne(filterDoc);
        System.out.println("\nres: "+res);
        
        //3. Delete all the movies rated “+18”
        filterDoc = eq("rated", "+18");
        res = movies.deleteOne(filterDoc);
        System.out.println("\nres: "+res);
        
        //4. Completely delete the collection
        movies.drop();
    }
}
