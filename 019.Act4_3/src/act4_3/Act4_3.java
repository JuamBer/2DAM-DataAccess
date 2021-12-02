package act4_3;

import static act4_3.QueryTeach.*;
import static act4_3.QueryDep.*;
import hibernate_resources.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
                            Departments[] departments1 = getAllDepartments();
                            for (Departments department : departments1) {
                                showDepartment(department);
                            }
                            break;
                        case 2:
                            System.out.println("Insert department pattern:");
                            String namePattern2 = sc.nextLine();
                            showDepartment(getDepartmentByName(namePattern2));
                            break;
                        case 3:
                            System.out.println("Insert department name:");
                            String depName3 = sc.nextLine();
                            System.out.println("AVG Salary: "+getAverageSalaryofDepartment(depName3));
                            break;
                        case 4:
                            HashMap<String, Double> departmentsAvgMap4 = getAverageSalaryPerDept();
                            Departments[] departments4 = getAllDepartments();
                            ArrayList<String> listKeys4 = new ArrayList();
                            for (Departments department : departments4) {
                                listKeys4.add(department.getName());
                            }
                            for (int i = 0; i < departments4.length; i++) {
                                String name = listKeys4.get(i);
                                System.out.println(name+": "+departmentsAvgMap4.get(name));
                            }
                            break;
                        case 5:
                            Teachers[] teachers5 = getAllTeachers();
                            for (Teachers teacher : teachers5) {
                                showTeacher(teacher);
                            }
                            break;
                        case 6:
                            showTeacher(getMostVeteranTeacher());
                            break;
                        case 7:

                            break;
                        case 8:

                            break;
                        case 9:
                            System.out.println("Insert teacher name:");
                            String name9 = sc.nextLine();
                            deleteTeachersOfDepartment(name9);
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
