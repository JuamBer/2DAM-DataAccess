package act4_3;

import static act4_3.QueryDep.showDepartment;
import hibernate_resources.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.*;

public class QueryTeach {
    
    private static final SessionFactory sf = HibernateUtil.getSessionFactory();
    
    public static void showTeacher(Teachers teacher){
        System.out.println("Teachers{" + "id=" + teacher.getId() + ", departmentName=" + teacher.getDepartments().getName() + ", name=" + teacher.getName() + ", surname=" + teacher.getSurname() + ", email=" + teacher.getEmail() + ", startDate=" + teacher.getStartDate() + ", salary=" + teacher.getSalary() + '}');
    } 

    public static Teachers[] getAllTeachers(){
        System.out.println("\n\n--------getAllTeachers--------");
        Session session = sf.openSession();
        String hql = "FROM Teachers";
        Query query = session.createQuery(hql);
        List <Teachers> teachersList = query.list();        
        Teachers[] teachersArray = new Teachers[teachersList.size()];
        teachersArray = teachersList.toArray(teachersArray);
        return teachersArray;
    } 

    public static Teachers getMostVeteranTeacher(){
        System.out.println("\n\n--------getMostVeteranTeacher--------");
        Session session = sf.openSession();
        String hql = "FROM Teachers WHERE startDate = (SELECT MIN(T.startDate) FROM Teachers T)";
        Query query = session.createQuery(hql);
        Teachers teacher = (Teachers) query.list().get(0);        
        return teacher;
    }

    public static int setSalary(int newSalary){
        System.out.println("\n\n--------setSalary--------");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        String hql = "UPDATE Teachers SET salary="+newSalary;
        Query query = session.createQuery(hql);
        int affectedRows = query.executeUpdate();  
        tx.commit();
        session.close();
        return affectedRows;
    } 

    public static int riseSalaryOfSeniors(int numOfYearsToBeSenior, int prctRise){
        System.out.println("\n\n--------riseSalaryOfSeniors--------");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction(); 
        double per = (prctRise/100.0)+1.0;
        Calendar c = Calendar.getInstance();
        Date d = new Date(System.currentTimeMillis());
        c.setTime(d);
        c.add(Calendar.YEAR, -numOfYearsToBeSenior);
        d = c.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = dateFormat.format(d).replace("/", "-");
        
        String hql = "UPDATE Teachers SET salary=(salary*:per) WHERE startDate < :date";
        Query query = session.createQuery(hql);
        query.setDouble("per",per);
        query.setString("date",date);
        int affectedRows = query.executeUpdate();  
        tx.commit();
        session.close();
        return affectedRows;
    } 
   
    public static int deleteTeachersOfDepartment(String depName){
        System.out.println("\n\n--------deleteTeachersOfDepartment--------");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        String hql = "DELETE FROM Teachers WHERE departments.name=:depName";
        Query query = session.createQuery(hql);
        query.setString("depName",depName);
        int affectedRows = query.executeUpdate();  
        tx.commit();
        session.close();
        return affectedRows;
    }

}
