package act4_3;

import static act4_3.QueryTeach.*;
import static act4_3.QueryDep.*;
import hibernate_resources.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import pojos.*;

public class Act4_3 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void menu() throws Exception {
        boolean key = true;

        do {
            System.out.println("\n\n\nChoose an option:\n"
                    + "1. Show all departments\n"
                    + "2. Show department whose name matches a pattern\n"
                    + "3. Get average salary of a department (by name)\n"
                    + "4. Show average salary of each department\n"
                    + "5. Show all teachers\n"
                    + "6. Show most veteran teacher\n"
                    + "7. Set salary\n"
                    + "8. Rise salary of senior teachers\n"
                    + "9. Delete teachers of a department\n"
                    + "10. Quit\n");

            try {
                int op = Integer.parseInt(sc.nextLine());
                if (op > 0 && op < 11) {
                    switch (op) {
                        case 1:
                            Departments[] departments = getAllDepartments();
                            for (Departments department : departments) {
                                showDepartment(department);
                            }
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:
                            Teachers[] teachers = getAllTeachers();
                            for (Teachers teacher : teachers) {
                                showTeacher(teacher);
                            }
                            break;
                        case 6:

                            break;
                        case 7:

                            break;
                        case 8:

                            break;
                        case 9:
                            System.out.println("Insert teacher id");
                            int id = Integer.parseInt(sc.nextLine());
                            deleteTeachersOfDepartment(id);
                            break;
                        case 10:
                            key = false;
                            break;
                    }
                } else {
                    System.out.println("You have to enter a number from 1 to 11");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect Format");
            }
        } while (key);
    }

}
