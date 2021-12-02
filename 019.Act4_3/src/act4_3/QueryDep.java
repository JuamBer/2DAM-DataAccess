package act4_3;

import java.util.HashMap;
import hibernate_resources.*;
import static java.util.Collections.list;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        Session session = sf.openSession();
        String hql = "FROM Departments WHERE name LIKE '"+patternName+"'";
        Query query = session.createQuery(hql);
        Departments department = (Departments) query.list().get(0);       
        return department;
    }

    public static double getAverageSalaryofDepartment(String depName){
        Session session = sf.openSession();
        String hql = "SELECT AVG(salary) FROM Teachers WHERE departments.name = '"+depName+"'";
        Query query = session.createQuery(hql);
        
        if(query == null){
            return 0;
        }else{
            double avgSalary = (double) query.uniqueResult();
            return avgSalary;
        }
    }

    public static HashMap<String, Double> getAverageSalaryPerDept(){
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
