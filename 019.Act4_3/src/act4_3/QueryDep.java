package act4_3;

import java.util.HashMap;
import hibernate_resources.*;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.*;

public class QueryDep {
    
    private static final Scanner sc = new Scanner(System.in);
    private static final SessionFactory sf = HibernateUtil.getSessionFactory();
    
    public static void showDepartment(Departments dep){
        System.out.println("Departments{" + "deptNum=" + dep.getDeptNum() + ", name=" + dep.getName() + ", office=" + dep.getOffice() + ", numTeachers="+dep.getTeacherses().size()+'}');
    } 
            
    public static Departments[] getAllDepartments(){
        Session session = sf.openSession();
        String hql = "FROM Departments";
        Query query = session.createQuery(hql);
        List <Departments> departmentsList = query.list();        
        Departments[] departmentsArray = new Departments[departmentsList.size()];
        departmentsArray = departmentsList.toArray(departmentsArray);
        return departmentsArray;
    }

    public static Departments getDepartmentByName(String patternName){
        return null;
    }

    public static double getAverageSalaryofDepartment(String depName){
        return 0;
    }

    public static HashMap<String, Double> getAverageSalaryPerDept(){
        return null;
    }

}
