
package Schedule;

import java.util.Scanner;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    
    
    public static void main(String[] args) throws FileNotFoundException {
        boolean key = true;
        Scanner sc = new Scanner(System.in, "ISO-8859-1");
        
        
//        ArrayList<Contact> schedule = new ArrayList<Contact>();
//        schedule.add(new Contact("Juan","Sáez García","665020636"));
//        schedule.add(new Contact("Jose","Sáez García","543214567"));
//        
          Schedule schedule = null;
//        XStream s = new XStream();
//        s.toXML(schedule,new FileOutputStream("/src/Schedule/data/schedule.xml"));
        
//        try{
//            XStream x = new XStream();
//            FileInputStream fs = new FileInputStream("/src/Schedule/data/schedule.xml"); 
//            schedule = (Schedule) x.fromXML(fs); 
//        }catch(FileNotFoundException e){
//            System.out.println(e.getMessage());
//        }
                
        
        do{
            System.out.println("1. Show all contacts\n" +"2. Add new contact\n" +"3. Save and exit");
            int op = sc.nextInt();
            
            switch (op) {
                case 1: sowSchedule(schedule); break;
                case 2: addContact(schedule); break;
                case 3: saveExit(schedule); key=false; break;
            }
        }while(key);
    }

    private static void sowSchedule(Schedule schedule) {
        schedule.getSchedule().forEach(contact -> {
            System.out.println(contact);
        });
    }

    private static void addContact(Schedule schedule) {
        Scanner sc = new Scanner(System.in, "ISO-8859-1");
        
        System.out.println("Insert Name:");
        String name = sc.nextLine();
        System.out.println("Insert Surname:");
        String surname = sc.nextLine();
        System.out.println("Insert Phone Numer:");
        String tel = sc.nextLine();
        
        schedule.addContact(new Contact(name,surname,tel));
    }

    private static void saveExit(Schedule schedule) {
        XStream x = new XStream(); 
        
        try{
            x.toXML(schedule,new FileOutputStream("/src/Schedule/data/schedule.xml"));
            System.out.println("Contacts Saved");
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
    }
}
