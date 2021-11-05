package Schedule;

import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        boolean key = true;
        Scanner sc = new Scanner(System.in, "ISO-8859-1");

        Schedule schedule = null;

        try {
            File file = new File("src/Schedule/data/schedule.dat");

            FileInputStream fos = new FileInputStream(file);
            if (!isFileEmpty(file)) {
                ObjectInputStream salida = new ObjectInputStream(fos);
                schedule = (Schedule) salida.readObject();
            } else {
                ArrayList<Contact> list = new ArrayList<Contact>();
                schedule = new Schedule(list);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        do {
            System.out.println("1. Show all contacts\n" + "2. Add new contact\n" + "3. Save and exit");
            int op = sc.nextInt();

            switch (op) {
                case 1:
                    sowSchedule(schedule);
                    break;
                case 2:
                    addContact(schedule);
                    break;
                case 3:
                    saveExit(schedule);
                    key = false;
                    break;
            }
        } while (key);
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

        schedule.addContact(new Contact(name, surname, tel));
    }

    private static void saveExit(Schedule schedule) {

        try {
            FileOutputStream fos = new FileOutputStream("src/Schedule/data/schedule.dat");
            ObjectOutputStream salida = new ObjectOutputStream(fos);

            salida.writeObject(schedule);
            System.out.println("Contacts Saved");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static boolean isFileEmpty(File file) {
        return file.length() == 0;
    }
}
