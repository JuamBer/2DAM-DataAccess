package hibernate3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Departamentos;
import pojos.Empleados;

public class Hibernate3 {

    public static void main(String[] args) {
        //SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        //Session sesion = sessionFactory.openSession();
        //Transaction tx = sesion.beginTransaction();
        //Departamentos dep = new Departamentos (); //dep is transient
        //dep.setDeptNo(35);
        //dep.setDnombre ("Nuevo dep");
        //dep.setLoc ("locapto");
        //sesion.save (dep); //now dep becomes persistent
        //tx.commit();
        //sesion.close();
        //after closing sesion, dep is detached
        
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session sesion = sessionFactory.openSession();
        
        Transaction tx = sesion.beginTransaction();
        Departamentos dep = new Departamentos(); //dop Ls transient

        dep.setDeptNo((byte) 36);

        dep.setDnombre("Nuevo dep");
        dep.setLoc("locdpto");

        Empleados emp = new Empleados(17); //emp is transient
        emp.setEmpNo(117);
        emp.setApellido("EZE");
        emp.setDir(7369);
        emp.setSalario((float) 1500);
        emp.setComision((float) 191);

        emp.setDepartamentos(dep);

        sesion.save(dep);
        sesion.save(emp); //nov trp becomes perszezent

        tx.commit();
        sesion.close();
    }
    
}
