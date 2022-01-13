package connectmongowithjava;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;



public class FindIterableMongo {
    public static void main(String[] args) {
        
        MongoClient client = (MongoClient) new MongoClient();
        MongoDatabase db = client.getDatabase("mflix");
        MongoCollection<Document> movies = db.getCollection("movies");
        
        Document sortDoc = new Document("year", 1);
        
        
        //find all sorted by year ascending
        FindIterable<Document> res = movies.find().sort(sortDoc);

        System.out.println("\n\n1.1 \nFirst result: " +res.first().toString());

        //show only year, title and director
        Document projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);

        //skip first result and keep only five
        res = res.skip(1).limit(5).projection(projectDoc);
        
        System.out.println("\n\n1.2");
        seeResults(res);
        
        
        Bson filterDoc = exists("year", false);
        res = movies.find(filterDoc);
        
        System.out.println("\n\n2.");
        seeResults(res);
        
        
        System.out.println("\n\n3.");
        
        
        System.out.println("Get the data for the movie “Jurassic World”\n");
        filterDoc = eq("title", "Jurassic World");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies of the year 2016\n");
        filterDoc = eq("year", 2016);
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies released between 2015 and 2016, both inclusive\n");
        filterDoc = and(gte("year", 2016), lt("year",2021));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies whose releasing year is a string ($type)\n");
        filterDoc = type("year", "string");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies that have not defined a plot ($exists)\n");
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("plot", 1);
        filterDoc = exists("plot", false);
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies filmed in Spain\n");
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("countries", 1);
        filterDoc = eq("countries", "Spain");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies with only one person in the cast and of the genre “Biography”\n");
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("cast", 1)
            .append("genres", 1);
        filterDoc = and(size("cast",1), eq("genres","Biography"));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies that have Spanish and English languages ($all)\n");
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("lenguajes", 1)
            .append("languages", 1);
        
        filterDoc = all("languages",Arrays.asList("English","Spanish"));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("Get the movies directed by Spielberg ($regex)\n");
        //?
        System.out.println("Get the movies directed by Spielberg OR Kubrick\n");
        //?
        System.out.println("Get the movies that won more than 7 awards and have at least 9 points in IMDB\n");
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("awards", 1);
        filterDoc = and(gt("awards.wins", 7),gte("imdb.rating", 9));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
    }
    
    public static void seeResults(FindIterable<Document> res){
        for (Document doc : res){
             System.out.println("\t"+doc.toString());
        }
        System.out.println("\n");
    }
}
