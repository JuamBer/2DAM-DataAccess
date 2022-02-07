package juamber.com.examenmongodb;

//Mongo
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.Arrays;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

//Pojos Utilities
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

//Pojos
import pojos.*;

public class ExamenMongoDB {
    
    private static MongoClient client;
    private static MongoDatabase db;
    private static MongoCollection<Alumno> alumnos;
    
    public static void main(String[] args) {
        init();
        dropDatabase();
        insertDemoData();
        seeQuerys();
        updatesToDB();
        deleteQuerys();
    }

    public static void init(){
        System.out.println("init()");
        
        client = new MongoClient();
        db = client.getDatabase("abastos");
                
        CodecRegistry pojoCodecRegistry
                = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        db = db.withCodecRegistry(pojoCodecRegistry);
        
        alumnos = db.getCollection("alumnos", Alumno.class);
    }
    
    public static void dropDatabase(){
        System.out.println("dropDatabase()");
        
        db.drop();
    }

    private static void insertDemoData() {
        System.out.println("insertDemoData()");
        
        FCT fct1 = new FCT("Software SA", 250);
        Alumno alumno1 = new Alumno("Pepe","Garcia Andreu", "7K", Arrays.asList("ADA","DI","SGE","PSP","FCT"),fct1);
        
        FCT fct2 = new FCT("Apps SL", 120);
        Alumno alumno2 = new Alumno("Pablo","Lopez Vazquez", "7K", Arrays.asList("DI","SGE","PSP","FCT"),fct2);
        
        FCT fct3 = new FCT("Sturtup SL", 300);
        Alumno alumno3 = new Alumno("Maria","Barredes Garcia", "7J", Arrays.asList("ADA","DI","SGE","PSP","FCT"),fct3);
        
        FCT fct4 = new FCT("Currantes SA", 50);
        Alumno alumno4 = new Alumno("Ramon","Perez Garcia", "7J", Arrays.asList("ADA","DI","SGE","PSP"),fct4);
        
        alumnos.insertMany(Arrays.asList(alumno1,alumno2,alumno3,alumno4));
    }
    
    private static void seeQuerys() {
        System.out.println("seeQuerys()");
        Bson filter;
        FindIterable<Alumno> alumnosRes;
        
        System.out.println("\ta) Alumnado Matriculado en ADA y con algún apellido García");
        filter = and(eq("matricula", "ADA"), regex("apellido","Garcia","/x"));
        alumnosRes = alumnos.find(filter);
        
        for (Alumno alumno : alumnosRes) {
            System.out.println("\t\t"+alumno);
        }
        
        System.out.println("\tb) Alumnado con menos de 250h realizadas en FCT");
        filter = lt("fct.horas", 250);
        alumnosRes = alumnos.find(filter);
        for (Alumno alumno : alumnosRes) {
            System.out.println("\t\t"+alumno);
        }
        
        System.out.println("\tc) Alumno o alumna con menos horas realizadas de FCT");
        filter = null; //TODO: CREATE FILTER
        Alumno alumno = alumnos.find().first();
        System.out.println("\t\t"+alumno);
    }
    
    private static void updatesToDB() {
        System.out.println("updatesToDB()");
        Bson filter;
        Bson update;
        UpdateResult result;
        
        System.out.println("\ta) Incrementa las horas de FCT en 50 al alumnado del 7J");
        filter = eq("grupo","7J");
        update = inc("fct.horas", 50);
        result = alumnos.updateMany(filter, update);
        System.out.println(result);
        
        System.out.println("\tb) Añadir el módulo ¨Proyecto¨ a todo el alumnado de mas de 250h");
        filter = gt("fct.horas",250);
        update = push("matricula", "Proyecto");
        result = alumnos.updateMany(filter, update);
        System.out.println(result);
        
        System.out.println("\tc) Comprobar que se tiene el modulo FCT, en caso contrario, eliminar todo el FCT");
        //TODO: CREATE FILTER
        //filter = eq("matricula","FCT");
        //update = unset("fct");
        //result = alumnos.updateMany(filter, update);
        System.out.println(result);
    }
    
    private static void deleteQuerys() {
        System.out.println("deleteQuerys()");
        Bson filter;
        DeleteResult result;
        
        filter = exists("fct", false);
        result = alumnos.deleteMany(filter);
        System.out.println(result);
    }
    
}