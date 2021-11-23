package act4_1;

import hibernate_resources.HibernateUtil;
import java.util.Scanner;
import org.hibernate.*;
import pojos.Departments;
import pojos.Teachers;

public class Act4_1 {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        boolean key = true;

        while (key) {
            System.out.println("\n\n");
            System.out.println("\nChoose an option:\n"
                    + "1. Show a department by ID\n"
                    + "2. Show a teacher by ID\n"
                    + "3. Show the teachers in existing department\n"
                    + "4. Create new department\n"
                    + "5. Create new teacher with new department associated\n"
                    + "6. Create new teacher with existing department associated\n"
                    + "7. Delete a teacher\n"
                    + "8. Delete a department\n"
                    + "9. Set salary of whole department\n"
                    + "10. Rise salary for seniors of department\n"
                    + "11. Quit\n");
            int op = sc.nextInt();
            
            switch (op) {
                case 1: 
                    System.out.println("ID: ");
                    int id = sc.nextInt();
                    showDepartment(id); 
                break;
                case 2: showTeacher(); break;
                case 3: showTeachersInDepartment(); break;
                case 4: createDepartment(); break;
                case 5: createTeacherAndDepartment(); break;
                case 6: createTeacherInExistingDepartment(); break;
                case 7: deleteTeacher(); break;
                case 8: deleteDepartment(); break;
                case 9: setSalaryOfDepartment(); break;
                case 10: riseSalaryOfDepartmentSeniors(); break;
                case 11: key = false; break;
            }
        }
    }
    
    public static void showDepartment(int id) {
        System.out.println("-----showDepartment-----");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session sesion = sessionFactory.openSession();
        Departments department = (Departments) sesion.get(Departments.class, id);
        System.out.println(department);
        sesion.close();
    }
    public static void showTeacher() {
        System.out.println("-----showTeacher-----");
    }
    public static void showTeachersInDepartment() {
        System.out.println("-----showTeachersInDepartment-----");
    }
    public static void createDepartment() {
        System.out.println("-----createDepartment-----");
    }
    public static void createTeacherAndDepartment() {
        System.out.println("-----createTeacherAndDepartment-----");
    }
    public static void createTeacherInExistingDepartment() {
        System.out.println("-----createTeacherInExistingDepartment-----");
    }
    public static void deleteTeacher() {
        System.out.println("-----deleteTeacher-----");
    }
    public static void deleteDepartment() {
        System.out.println("-----deleteDepartment-----");
    }
    public static void setSalaryOfDepartment() {
        System.out.println("-----setSalaryOfDepartment-----");
    }
    public static void riseSalaryOfDepartmentSeniors() {
        System.out.println("-----riseSalaryOfDepartmentSeniors-----");
    }
    
}
