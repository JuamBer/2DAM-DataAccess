package act4_2;

import hibernate_resources.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.*;
import pojos.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Act4_2 {
    
    private static Scanner sc = new Scanner(System.in);
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    public static void main(String[] args) throws ParseException {
        menu();
    }

    public static void menu() throws ParseException {
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
            int deptNum,salary,per,years;
            String name,office;
            String namet,surnamet,emailt,d;
            Date startDatet;
            
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
                case 5: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    System.out.println("name (Department): ");
                    name = sc.nextLine();
                    System.out.println("office (Department): ");
                    office = sc.nextLine();
                    
                    System.out.println("id (Teacher): ");
                    id = sc.nextInt();sc.nextLine();
                    System.out.println("name (Teacher): ");
                    namet = sc.nextLine();
                    System.out.println("surname (Teacher): ");
                    surnamet = sc.nextLine();
                    System.out.println("email (Teacher): ");
                    emailt = sc.nextLine();
                    System.out.println("start date (dd/MM/yyyy) (Teacher): ");
                    d = sc.nextLine();
                    startDatet = new SimpleDateFormat("dd/MM/yyyy").parse(d);  
                    System.out.println("salary (Teacher): ");
                    salary = sc.nextInt();
                    createTeacherAndDepartment(deptNum,name,office,id,namet,surnamet,emailt,startDatet,salary); 
                break;
                case 6: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    System.out.println("id (Teacher): ");
                    id = sc.nextInt();sc.nextLine();
                    System.out.println("name (Teacher): ");
                    namet = sc.nextLine();
                    System.out.println("surname (Teacher): ");
                    surnamet = sc.nextLine();
                    System.out.println("email (Teacher): ");
                    emailt = sc.nextLine();
                    System.out.println("start date (dd/MM/yyyy) (Teacher): ");
                    d = sc.nextLine();
                    startDatet = new SimpleDateFormat("dd/MM/yyyy").parse(d);  
                    System.out.println("salary (Teacher): ");
                    salary = sc.nextInt();
                    
                    createTeacherInExistingDepartment(deptNum,id,namet,surnamet,emailt,startDatet,salary);
                    break;
                case 7: 
                    System.out.println("id (Teacher): ");
                    id = sc.nextInt();sc.nextLine();
                    deleteTeacher(id); 
                break;
                case 8: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    deleteDepartment(deptNum); 
                break;
                case 9: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    System.out.println("salary (Of all teachers of the Department "+deptNum+"): ");
                    salary = sc.nextInt();sc.nextLine();
                    setSalaryOfDepartment(deptNum,salary); 
                break;
                case 10: 
                    System.out.println("deptNum (Department): ");
                    deptNum = sc.nextInt();sc.nextLine();
                    System.out.println("Percentage to raise salary (Of Seniors teachers of the Department "+deptNum+"): ");
                    per = sc.nextInt();sc.nextLine();
                    System.out.println("Years to be considered Senior: ");
                    years = sc.nextInt();sc.nextLine();
                    riseSalaryOfDepartmentSeniors(deptNum,per,years); 
                break;
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
    public static void createTeacherAndDepartment(int deptNum,String name,String office,int id,String namet, String surnamet, String emailt, Date startDatet, Integer salary) {
        System.out.println("-----createTeacherAndDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Transaction tx;
        
        tx = sesion.beginTransaction();
        Departments department = new Departments(); 
        department.setDeptNum(deptNum);
        department.setName(name);
        department.setOffice(office);
        sesion.save(department);
        tx.commit();
        System.out.println("Department Created");
        
        tx = sesion.beginTransaction();
        Teachers teacher = new Teachers(); 
        teacher.setId(id);
        teacher.setName(namet);
        teacher.setSurname(surnamet);
        teacher.setEmail(emailt);
        teacher.setStartDate(startDatet);
        teacher.setSalary(salary);
        teacher.setDepartments(department);
        sesion.save(teacher);
        tx.commit();
        System.out.println("Teacher Created");
        sesion.close();
    }
    public static void createTeacherInExistingDepartment(int deptNum,int id,String namet, String surnamet, String emailt, Date startDatet, Integer salary) {
        System.out.println("-----createTeacherInExistingDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Teachers teacher = new Teachers(); 
        teacher.setId(id);
        teacher.setName(namet);
        teacher.setSurname(surnamet);
        teacher.setEmail(emailt);
        teacher.setStartDate(startDatet);
        teacher.setSalary(salary);
        teacher.setDepartments((Departments) sesion.get(Departments.class, deptNum));
        sesion.save(teacher);
        tx.commit();
        System.out.println("Teacher Created");
        sesion.close();
    }
    public static void deleteTeacher(int id) {
        System.out.println("-----deleteTeacher-----");
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(sesion.get(Teachers.class, id));
        tx.commit();
        System.out.println("Teacher Deleted");
        sesion.close();
    }
    public static void deleteDepartment(int deptNum) {
        System.out.println("-----deleteDepartment-----");
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);
        
        if(department.getTeacherses().size()>0){
            System.out.println(department.getName()+" has associate professors. Do you want to delete them together with the department? (Y/N)");
                String res = sc.nextLine();
                if(res.toUpperCase().equals("Y")){
                    department.getTeacherses().forEach(teacherObject -> {
                        Teachers teacher = (Teachers) teacherObject;
                        System.out.println("Deleting Teacher '"+ teacher.getName() + "'");
                        sesion.delete(teacher);
                    });
                    System.out.println("Deleting Department " + department.getName());
                    sesion.delete(department);
                    tx.commit();

                }else if(res.toUpperCase().equals("N")){
                    System.out.println("No data has been deleted");
                }else{
                    deleteDepartment(deptNum);
                }
        }else{
            sesion.delete(department);
            tx.commit();
        }
        sesion.close();
    }
    public static void setSalaryOfDepartment(int deptNum, int salary) {
        System.out.println("-----setSalaryOfDepartment-----");
        
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);
        
        if(department.getTeacherses().size()>0){
            department.getTeacherses().forEach(teacherObject -> {
                Teachers teacher = (Teachers) teacherObject;
                System.out.println("Updating Salary Of Teacher '"+ teacher.getName() + "'");
                sesion.delete(teacher);
                teacher.setSalary(salary);
            });
            tx.commit();
        }else{
            System.out.println(department.getName()+" does not have teachers");
        }
    }
    public static void riseSalaryOfDepartmentSeniors(int deptNum, int per, int years) throws ParseException{
        System.out.println("-----riseSalaryOfDepartmentSeniors-----");
        
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);
        
        if(department.getTeacherses().size()>0){
            department.getTeacherses().forEach(teacherObject -> {
                Teachers teacher = (Teachers) teacherObject;
                
                Date start_date = teacher.getStartDate();
                LocalDate date = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Clock cl = Clock.systemUTC();
                LocalDate today = LocalDate.now(cl);    
                
                Period p = Period.between();
                
               
            });
            tx.commit();
        }else{
            System.out.println(department.getName()+" does not have teachers");
        }
    }
    
}
