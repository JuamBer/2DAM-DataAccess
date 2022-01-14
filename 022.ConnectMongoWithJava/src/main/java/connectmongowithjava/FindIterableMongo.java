package connectmongowithjava;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import java.util.Arrays;
import java.util.regex.Pattern;

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
        
        
        System.out.println("1. Get the data for the movie “Jurassic World”\n");
        //{title: {$eq: "Jurassic Park"}}
	//{title: "Jurassic Park"}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        filterDoc = eq("title", "Jurassic World");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("2. Get the movies of the year 2016\n");
        //{year: {$eq:2016}}
	//{year: 2016}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        filterDoc = eq("year", 2016);
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("3. Get the movies released between 2015 and 2016, both inclusive\n");
        //{year: {$gte: 2016, $lte: 2021}}
	//{year: {$in:[2015, 2016]}}
	//{$and:[{year: {$gte: 2015}}, {year:{$lte: 2016}}]}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        filterDoc = and(gte("year", 2016), lt("year",2021));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("4. Get the movies whose releasing year is a string ($type)\n");
        //{year: {$type: "string"}}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        filterDoc = type("year", "string");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("5. Get the movies that have not defined a plot ($exists)\n");
        //{plot: {$exists: false}}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("plot", 1);
        filterDoc = exists("plot", false);
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("6. Get the movies filmed in Spain\n");
        //{countries: "Spain"}
	//{countries: {$elemMatch: {$eq: "Spain"}}}
	//{countries: {$elemMatch: {$regex: "Spain"}}}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("countries", 1);
        filterDoc = eq("countries", "Spain");
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("7. Get the movies with only one person in the cast and of the genre “Biography”\n");
        //{cast: {$size: 1},genres: 'Biography'}
        //{cast:{$size:1}, genres:{$elemMatch: {$eq: "Biography"}}}
        //{$and:[{cast:{$size: 1}}, {genres:"Biography"}]}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("cast", 1)
            .append("genres", 1);
        filterDoc = and(size("cast",1), eq("genres","Biography"));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("8. Get the movies that have Spanish and English languages ($all)\n");
        //{languages: {$all:["English", "Spanish"]}}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("lenguajes", 1)
            .append("languages", 1);
        filterDoc = all("languages",Arrays.asList("English","Spanish"));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("9. Get the movies directed by Spielberg ($regex)\n");
        //{directors:{$elemMatch: {$regex: "Spielberg"}}} 
        //{directors: {$regex:/spielberg/i}}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        filterDoc = Filters.regex("directors", Pattern.compile("spielberg", Pattern.CASE_INSENSITIVE));;
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("10. Get the movies directed by Spielberg OR Kubrick\n");
        //{$or: [{directors:{$elemMatch: {$regex: "Spielberg"}}},
        //{directors:{$elemMatch: {$regex: "Kubrick"}}}]}
        //{$or: [{directors: {$regex:/spielberg/i}}, {directors: {$regex:/kubrick/i}}]}
        projectDoc = new Document()
            .append("_id", 0)
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1);
        
        filterDoc = or(Filters.regex("directors", Pattern.compile("spielberg", Pattern.CASE_INSENSITIVE)),Filters.regex("directors", Pattern.compile("kubrick", Pattern.CASE_INSENSITIVE)));
        res = movies.find(filterDoc).projection(projectDoc).limit(10);
        seeResults(res);
        
        System.out.println("11. Get the movies that won more than 7 awards and have at least 9 points in IMDB\n");
        //{"imdb.rating":{$gte:9}, "awards.wins":{$gt:7}}
	//{$and:[{"awards.wins": {$gt:7}},{ "imdb.rating":{$gte:9}}]}
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
