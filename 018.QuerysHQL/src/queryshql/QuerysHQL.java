
package queryshql;

import pojos.*;
import hibernate_resources.*;
import java.util.List;
import org.hibernate.*;


public class QuerysHQL {

    private static final SessionFactory sf = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        Session session = sf.openSession();
        Query q;
        
        System.out.println("\n\n1. Get the information of the employees of the ‘Ventas’ department ");
        session = sf.openSession();
        q = session.createQuery("FROM Empleados E WHERE E.departamentos.dnombre='Ventas'");
        List <Empleados> listaEmpelados = q.list();
        System.out.println("Se han obtenido "+listaEmpelados.size()+" resultados.");
        for (Empleados emp : listaEmpelados) {
            System.out.println(emp);
        }
        session.close();
        
        System.out.println("\n\n2. Get a list of the surname and the city of work of each employee ");
        session = sf.openSession();
        q = session.createQuery("SELECT apellido, departamentos.loc FROM Empleados");
        List <Object[]> listaEmpelados2 = q.list();
        System.out.println("Se han obtenido "+listaEmpelados2.size()+" resultados.");
        for (Object[] emp : listaEmpelados2) {
            String ape = (String) emp[0];
            String loc = (String) emp[1];
            
            System.out.println("Apellido: "+ape+"; Loc: "+loc);
        }
        session.close();
        
        
        System.out.println("\n\n3. Get the most veteran employee");
        session = sf.openSession();
        q = session.createQuery("FROM Empleados E WHERE E.fechaAlta = (SELECT MIN(EE.fechaAlta) FROM Empleados EE)");
        List <Empleados> listaEmpelados3 = q.list();
        System.out.println("Se han obtenido "+listaEmpelados3.size()+" resultados.");
        
        for (Empleados emp : listaEmpelados3) {
            System.out.println(emp);
        }
        session.close();
        
        System.out.println("\n\n4. Get the number of employees in each department");
        session = sf.openSession();
        q = session.createQuery("FROM Departamentos");
        List <Departamentos> listaEmpelados4 = q.list();
        System.out.println("Se han obtenido "+listaEmpelados4.size()+" resultados.");
        for (Departamentos dep : listaEmpelados4) {
            System.out.println("Nombre: "+dep.getDnombre()+"; NumEmpleados: "+dep.getEmpleadoses().size());
        }
        session.close();
        
        System.out.println("\n\n5. ParameterList");
        session = sf.openSession();
        q = session.createQuery("FROM Empleados WHERE apellido IN :empNombres");
        q.setParameterList("empNombres", new String[]{"Martinez","Garcia","Torres"});
        List <Empleados> listaEmpelados5 = q.list();
        System.out.println("Se han obtenido "+listaEmpelados5.size()+" resultados.");
        for (Empleados emp : listaEmpelados5) {
            System.out.println(emp);
        }
        session.close();
        
        sf.close();
    }
    
}
