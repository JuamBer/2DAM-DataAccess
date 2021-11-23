package act4_2;

import hibernate_resources.*;
import org.hibernate.*;
import pojos.*;
import java.util.Scanner;
import java.util.Set;


public class Act4_2 {
    
    private static Scanner sc = new Scanner(System.in);
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
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
            int id;
            int deptNum;
            String name,office;
            switch (op) {
                case 1: 
                    System.out.println("ID (Department): ");
                    id = sc.nextInt();
                    showDepartment(id); 
                break;
                case 2: 
                    System.out.println("ID (Teacher): ");
                    id = sc.nextInt();
                    showTeacher(id); 
                break;
                case 3: 
                    System.out.println("ID (Department): ");
                    id = sc.nextInt();
                    showTeachersInDepartment(id); 
                break;
                case 4: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    System.out.println("name (Department): ");
                    name = sc.nextLine();
                    System.out.println("office (Department): ");
                    office = sc.nextLine();
                    createDepartment(deptNum,name,office); 
                break;
                case 5: createTeacherAndDepartment(); break;
                case 6: createTeacherInExistingDepartment(); break;
                case 7: deleteTeacher(); break;
                case 8: deleteDepartment(); break;
                case 9: setSalaryOfDepartment(); break;
                case 10: riseSalaryOfDepartmentSeniors(); break;
                case 11: 
                    key = false; 
                    sessionFactory.close(); 
                break;
            }
        }
    }
    
    public static void showDepartment(int id) {
        System.out.println("-----showDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Departments department = (Departments) sesion.get(Departments.class, id);
        System.out.println(department);
        sesion.close();
    }
    public static void showTeacher(int id) {
        System.out.println("-----showTeacher-----");
        
        Session sesion = sessionFactory.openSession();
        Teachers teacher = (Teachers) sesion.get(Teachers.class, id);
        System.out.println(teacher);
        sesion.close();
    }
    public static void showTeachersInDepartment(int id) {
        System.out.println("-----showTeachersInDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Departments department = (Departments) sesion.get(Departments.class, id);
 
        System.out.println(department);
        department.getTeacherses().forEach(teacher -> {
            System.out.println(teacher);
        });
        sesion.close();
    }
    public static void createDepartment(int deptNum,String name,String office) {
        System.out.println("-----createDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        
        Departments department = new Departments(); 
        department.setDeptNum(deptNum);
        department.setName(name);
        department.setOffice(office);
        sesion.save(department);
        tx.commit();
        System.out.println("Department Created");
        sesion.close();
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
