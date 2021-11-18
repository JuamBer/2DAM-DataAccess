
package hibernate3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Departamentos;

public class Hibernate3 {

    public static void main(String[] args) {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departamentos dep = new Departamentos (); //dep is transient
        dep.setDeptNo(35);
        dep.setDnombre ("Nuevo dep");
        dep.setLoc ("locapto");
        sesion.save (dep); //now dep becomes persistent
        tx.commit();
        sesion.close();
        //after closing sesion, dep is detached
    }
    
}
