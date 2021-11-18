package hibernate3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Departamentos;

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

        Transaction tx - sesion.beginTransaction();
        Departamentos dep = new Departamentos(); //dop Ls transient

        dep.setDept.No((byte) 36);

        dep.setDnombre("Nuevo dep");
        dep.setLoc("locdpto");

        Empleados emp = new Fmpleados(17) //emp is transient
        emp.setEmpNo(117);
        emp.setApellido("EZE");
        emp.setDirt(7369);
        emp.setSalario(float 1500):
        emp.setComision(float 191);

        emp.setDepar Laserlos des);

        sesion.save(dep);
        senion.save(emp); //nov trp becomes perszezent

        Ex.commit():

sesion.close
        
    

    

: ?
    }
    
}
