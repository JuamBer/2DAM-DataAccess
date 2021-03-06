package act4_3;

import java.util.HashMap;
import hibernate_resources.*;
import java.util.List;
import java.util.Scanner;
import org.hibernate.NonUniqueResultException;
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
        System.out.println("\n\n--------getAllDepartments--------");
        Session session = sf.openSession();
        String hql = "FROM Departments";
        Query query = session.createQuery(hql);
        List <Departments> departmentsList = query.list();        
        Departments[] departmentsArray = new Departments[departmentsList.size()];
        departmentsArray = departmentsList.toArray(departmentsArray);
        return departmentsArray;
    }

    public static Departments getDepartmentByName(String patternName){
        System.out.println("\n\n--------getDepartmentByName--------");
        Session session = sf.openSession();
        String hql = "FROM Departments WHERE name LIKE ?";
        Query query = session.createQuery(hql);
        query.setString(0,patternName);
        Departments department = null;
        try{
            department = (Departments) query.uniqueResult();       
        }catch(NonUniqueResultException ex){
            System.out.println("There is more than one result that matches the pattern");
        }
        
        return department;
    }

    public static double getAverageSalaryofDepartment(String depName){
        System.out.println("\n\n--------getAverageSalaryofDepartment--------");
        Session session = sf.openSession();
        String hql = "SELECT AVG(salary) FROM Teachers WHERE departments.name = :depName";
        Query query = session.createQuery(hql);
        
        query.setString("depName",depName);
        
        if(query == null){
            return 0;
        }else{
            try{
                double avgSalary = (double) query.uniqueResult();
                return avgSalary;
            }catch(Exception ex){
                return 0;
            }
            
            
        }
    }

    public static HashMap<String, Double> getAverageSalaryPerDept(){
        System.out.println("\n\n--------getAverageSalaryPerDept--------");
        Session session = sf.openSession();
        String hql = "SELECT departments.name, AVG(salary) FROM Teachers GROUP BY departments";
        Query query = session.createQuery(hql);
        
        List <Object[]> departmentsAvgList = query.list();
        HashMap<String, Double> departmentsAvgMap = new HashMap();
        departmentsAvgList.forEach(department -> {
            departmentsAvgMap.put( (String) department[0], (Double) department[1]);
        });
        return departmentsAvgMap;
    }

}
