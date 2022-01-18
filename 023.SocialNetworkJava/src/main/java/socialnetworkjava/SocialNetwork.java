package socialnetworkjava;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.Arrays;
import java.util.Date;
import org.bson.Document;
import org.bson.conversions.Bson;

public class SocialNetwork {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("social_network");
        db.drop();
        db = client.getDatabase("social_network");
        MongoCollection<Document> users = db.getCollection("users");
        
        Bson filterDoc;
        Bson updateDoc;
        UpdateResult updateResult;
        FindIterable<Document> findRes;
        DeleteResult delRes;
        /* 
            1 - During login users of our social network tell us their name, surname, age and gender.We also insert the registration date at the time of registration.As identifier we want to use the Long type(use NumberLong()), not the ObjectID that Mongo gives us by
            default.Let 's say that two users are registered. Write the statements to insert them into the "user" collection, with identifiers 5 and 6 respectively. -
            Juan García Castellano, 23 years old - Beatriz Perez Solaz, 27 years old.
        */
        
        System.out.println("1 - During login users of our social network tell us their name, surname, age and gender.We also insert the registration date at the time of registration.As identifier we want to use the Long type(use NumberLong()), not the ObjectID that Mongo gives us by\n" +
        " default.Let 's say that two users are registered. Write the statements to insert them into the \"user\" collection, with identifiers 5 and 6 respectively. -\n" +
        " Juan García Castellano, 23 years old - Beatriz Perez Solaz, 27 years old.");
        
        Document doc1 = new Document()
                                .append("_id",5L)
                                .append("name","Juan")
                                .append("surname","García Castellano")
                                .append("age",23)
                                .append("gender", "Male")
                                .append("registration_date", new Date());
        
        Document doc2 = new Document()
                                .append("_id",6L)
                                .append("name","Beatriz")
                                .append("surname","Perez  Solaz")
                                .append("age",27)
                                .append("gender", "Female")
                                .append("registration_date", new Date());
        
        users.insertMany(Arrays.asList(doc1,doc2));
        
        /* 
            2 - Write the command to retrieve all documents in the "user" collection.
        */
        
        System.out.println("2 - Write the command to retrieve all documents in the \"user\" collection.");
        findRes = users.find();
        seeFindResults(findRes);
        
        /* 
            3 - Users can belong to as many groups as they want, and we save those groups in an Array field called groups in each user.
            Insert the new user Jorge Lopez Sevilla, with identifier 7, 
            who does not tell us his age, and who belongs to the groups "basketball", "kitchen"
            and "historical novel".
        */
        System.out.println("3 - Users can belong to as many groups as they want, and we save those groups in an Array field called groups in each user.\n" +
        " Insert the new user Jorge Lopez Sevilla, with identifier 7, \n" +
        " who does not tell us his age, and who belongs to the groups \"basketball\", \"kitchen\"\n" +
        " and \"historical novel\".");
        
        Document doc3 = new Document()
                                .append("_id",7L)
                                .append("name","Jorge")
                                .append("surname","Lopez Sevilla")
                                .append("gender", "Male")
                                .append("groups", Arrays.asList("basketball","kitchen","historical novel"));
        users.insertOne(doc3);
        
        /*
            4 - Juan García, with identifier 5 is unsubscribed.Write the sentence to delete it.
        */
        System.out.println("4 - Juan García, with identifier 5 is unsubscribed.Write the sentence to delete it.");
        filterDoc = eq("_id", 5L);
        delRes = users.deleteOne(filterDoc);
        seeDelResults(delRes);
        
        /*
            5 - The user Beatriz, with identifier 6, signs up
            for two groups: "historical novel"
            and "dance". Write the statement to update these fields in your document without
            reporting the rest. Remember that groups are saved as arrays.
        */
        System.out.println("5 - The user Beatriz, with identifier 6, signs up\n" +
        " for two groups: \"historical novel\"\n" +
        " and \"dance\". Write the statement to update these fields in your document without\n" +
        " reporting the rest. Remember that groups are saved as arrays.");
        
        filterDoc = eq("_id", 6L);
        updateDoc = set("groups",Arrays.asList("dance","historical novel"));
        updateResult = users.updateOne(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        /*
            6- In our social network you can also register companies, which we keep in 
            the collection "company", also with Long identifier, and
            for which at the moment we only store the name of the company.
            Write the command to insert the company "Gardening Gardenia"
            with identifier 10.
        */
        
        System.out.println("6- In our social network you can also register companies, which we keep in \n" +
        " the collection \"company\", also with Long identifier, and\n" +
        " for which at the moment we only store the name of the company.\n" +
        " Write the command to insert the company \"Gardening Gardenia\"\n" +
        " with identifier 10.");
        
        Document doc4 = new Document()
                                .append("_id",10L)
                                .append("name","Gardening Gardenia");
        users.insertOne(doc4);
        
        /*
            7 - Now you must update the data of the company "Gardening Gardenia"
            adding the following fields:
                -Address.It must be an embedded document with street Palmeras, 
                number 8 and town Torrente.
                -Sector: services.
                -Web: http: //www.gardeninggardenia.com
        */
        filterDoc = eq("_id",10L);
        
        Document address = new Document()
                .append("street","Palmeras")
                .append("number",8)
                .append("town","Torrente");
        
        Document setDoc = new Document()
                .append("address", address)
                .append("sector","services")
                .append("web","http://www.gardeninggardenia.com");
                
        
        updateDoc = and(
                set("address", (Document) setDoc.get("address")), 
                set("sector", (String) setDoc.get("sector")),
                set("web", (String) setDoc.get("web"))
        );
        
        updateResult = users.updateMany(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        /*
            8 - We are going to count the followers of the companies of the social network, 
            using a field "followers" in the collection "company".
            Five users have marked FOLLOW the company "Gardening Gardenia".

                - Type the command to create the "followers" field being equal to 5.
                - Then two people have followed the company.Type the command to increment it.
                - Finally, one has unfollowed.Also type the command to decrease it.
        */
        
        System.out.println("8 - We are going to count the followers of the companies of the social network, \n" +
        "using a field \"followers\" in the collection \"company\".\n" +
        "Five users have marked FOLLOW the company \"Gardening Gardenia\".");
        filterDoc = eq("_id",10L);
        updateDoc = set("followers", 5);
        updateResult = users.updateMany(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        updateDoc = inc("followers", 2);
        updateResult = users.updateMany(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        updateDoc = inc("followers", -1);
        updateResult = users.updateMany(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        /*
            9 - Update the address of the company "Gardening Gardenia", 
            add the postal code 46009.
        */
        System.out.println("9 - Update the address of the company \"Gardening Gardenia\", \n" +
        "add the postal code 46009.\n");
        filterDoc = eq("_id",10L);
        updateDoc = set("address.postal_code", 46009);
        updateResult = users.updateMany(filterDoc, updateDoc);
        seeUpdateResults(updateResult);
        
        /*
            10 - Eliminate the field "sector" of the company "Gardening Gardenia", 
            leaving intact the rest of the fields.
        */
        System.out.println("10 - Eliminate the field \"sector\" of the company \"Gardening Gardenia\", \n" +
        " leaving intact the rest of the fields.");
        
        client.close();
    }
    
    public static void seeFindResults(FindIterable<Document> res){
        System.out.println("FIND RESULT");
        for (Document doc : res){
             System.out.println("\t"+doc.toString());
        }
        System.out.println("\n");
    }
    public static void seeDelResults(DeleteResult res){
        System.out.println("DELETE RESULT");
        System.out.println(res);
        System.out.println("\n");
    }
    public static void seeUpdateResults(UpdateResult res){
        System.out.println("UPDATE RESULT");
        System.out.println(res);
        System.out.println("\n");
    }
}
