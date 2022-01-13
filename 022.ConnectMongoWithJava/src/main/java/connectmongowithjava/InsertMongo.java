/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectmongowithjava;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import org.bson.Document;

/**
 *
 * @author 7JDAM
 */
public class InsertMongo {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("new_mflix");
        MongoCollection<Document> movies = db.getCollection("movies");
        
        Document doc1 = new Document()
                .append("title","Lord Of The Rings")
                .append("genres", Arrays.asList(
                        "Adventure",
                        "Fantasy"
                    ))
                .append("type","Movie")
                .append("rated","+13")
                .append("year","2001")
                .append("director","Peter Jackson")
                .append("cast", Arrays.asList(
                        "Elijah Wood",
                        "Ian McKellen",
                        "Liv Tyler",
                        "Viggo Mortensen"
                    ));
        
        Document doc2 = new Document()
                .append("title","Matrix")
                .append("genres", Arrays.asList(
                        "Sci-fi",
                        "Cyberpunk"
                    ))
                .append("type","Movie")
                .append("rated","+18")
                .append("year","1999")
                .append("director","The Wachowskis")
                .append("cast", Arrays.asList(
                        "Keanu Reeves",
                        "Laurence Fishburne",
                        "Carrie-Anne Moss"
                    ));
        
        Document doc3 = new Document()
                .append("title","Game of thrones")
                .append("genres", Arrays.asList(
                        "Fantasy",
                        "Intrigue"
                    ))
                .append("type","Series")
                .append("rated","+18")
                .append("year","2011")
                .append("cast", Arrays.asList(
                        "Peter Dinklage",
                        "Lena Headey",
                        "Kit Harington",
                        "Emilia Clarke"
                    ));
        
        Document doc4 = new Document()
                .append("title","Data Access: the Movie")
                .append("genres", Arrays.asList(
                        "Drama",
                        "Horror"
                    ))
                .append("type","Movie")
                .append("rated","+18")
                .append("year","2021")
                .append("director","Xavier Ibáñez");
        
        movies.insertOne(doc1);
        movies.insertMany(Arrays.asList(doc2,doc3,doc4));
    }
}
