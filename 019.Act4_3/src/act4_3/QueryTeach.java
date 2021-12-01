package act4_3;

import static act4_3.QueryDep.showDepartment;
import hibernate_resources.*;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.*;

public class QueryTeach {
    
    private static final Scanner sc = new Scanner(System.in);
    private static final SessionFactory sf = HibernateUtil.getSessionFactory();
    
    public static void showTeacher(Teachers teacher){
        System.out.println("Teachers{" + "id=" + teacher.getId() + ", departmentName=" + teacher.getDepartments().getName() + ", name=" + teacher.getName() + ", surname=" + teacher.getSurname() + ", email=" + teacher.getEmail() + ", startDate=" + teacher.getStartDate() + ", salary=" + teacher.getSalary() + '}');
    } 

    public static Teachers[] getAllTeachers(){
        Session session = sf.openSession();
        String hql = "FROM Teachers";
        Query query = session.createQuery(hql);
        List <Teachers> teachersList = query.list();        
        Teachers[] teachersArray = new Teachers[teachersList.size()];
        teachersArray = teachersList.toArray(teachersArray);
        return teachersArray;
    } 

    public static Teachers getMostVeteranTeacher(){
        return null;
        
    }

    public static int setSalary(int newSalary){
        return 0;
        
    } 

    public static int riseSalaryOfSeniors(int numOfYearsToBeSenior, int prctRise){
        return 0;
        
    } 
   
    public static int deleteTeachersOfDepartment(String depName){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        String hql = "DELETE Teachers WHERE departments.deptNum="+depName;
        Query query = session.createQuery(hql);
        int affectedRows = query.executeUpdate();  
        tx.commit();
        session.close();
        return affectedRows;
    }

}
