package pojos;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.Arrays;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

public class MainPojos {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("mflix");
        
        CodecRegistry pojoCodecRegistry = 
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),                
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        db = db.withCodecRegistry(pojoCodecRegistry);
       
        MongoCollection<Movie> movies = db.getCollection("movies", Movie.class);
        
        Bson filter = eq("title","Matrix");
        System.out.println(movies.find().first());
        
        Awards awards = new Awards(1,1,"text");
        Movie movie = new Movie("Fake Movie",2022,Arrays.asList("Josn Smith"), Arrays.asList("Romantic","Western"),awards);
        movies.insertOne(movie);
        System.out.println(movies.find(eq("year",2022)).first());
        
        client.close();
    }
}
