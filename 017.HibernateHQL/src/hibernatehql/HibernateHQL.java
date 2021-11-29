package hibernatehql;

import pojos.*;
import hibernate_resources.*;
import java.util.List;
import org.hibernate.*;

public class HibernateHQL {
    
    private static final SessionFactory sf = HibernateUtil.getSessionFactory();
    
    public static void main(String[] args) {
        Session session = sf.openSession();
        Query q;
        
        //QUERY 1
        System.out.println("\n\nQUERY 1");
        session = sf.openSession();
        q = session.createQuery("FROM Departamentos");
        List <Departamentos> listaDepartamentos = q.list();
        System.out.println("Se han obtenido "+listaDepartamentos.size()+" resultados.");
        for (Departamentos dep : listaDepartamentos) {
            System.out.println("\t"+dep.getDeptNo()+ " | " + dep.getDnombre());
        }
        session.close();
        
        
        //QUERY 2
        System.out.println("\n\nQUERY 2");
        session = sf.openSession();
        q = session.createQuery("FROM Departamentos D, Empleados E WHERE D.deptNo = E.departamentos.deptNo ORDER BY E.apellido");
        List <Object[]> listaTodo = q.list();
        
        System.out.println("Se han obtenido "+listaTodo.size()+" resultados.");
        
        for (Object[] element : listaTodo) {
            Departamentos dep = (Departamentos) element[0];
            Empleados emp = (Empleados) element[1];
            
            System.out.println("\t"+dep.getDeptNo()+ " | " + dep.getDnombre()+ " | "+ emp.getApellido());
        }
        session.close();
        
        //QUERY 3
        System.out.println("\n\nQUERY 3");
        session = sf.openSession();
        q = session.createQuery("SELECT D.deptNo, D.dnombre, E.apellido FROM Departamentos D, Empleados E WHERE D.deptNo = E.departamentos.deptNo ORDER BY E.apellido");
        List <Object[]> listaTiposIndividuales = q.list();
        
        System.out.println("Se han obtenido "+listaTiposIndividuales.size()+" resultados.");
        
        for (Object[] element : listaTiposIndividuales) {
            int deptNo = (int) element[0];
            String dNombre = (String) element[1];
            String apellido = (String) element[2];
            
            System.out.println("\t"+deptNo+ " | " + dNombre+ " | "+ apellido);
        }
        session.close();
        
        
        //SCALAR QUERYS
        System.out.println("\n\nSCALAR QUERYS");
        session = sf.openSession();
        
        System.out.println("\n\nSCALAR QUERY 1");
        q = session.createQuery("FROM Departamentos D WHERE D.deptNo=3");
        Departamentos dep = (Departamentos) q.uniqueResult();
        System.out.println(dep);
        
        System.out.println("\n\nSCALAR QUERY 2");
        q = session.createQuery("SELECT AVG(salario) FROM Empleados");
        Double avg = (Double) q.uniqueResult();
        System.out.println("Salario Medio: "+avg);
        
        System.out.println("\n\nSCALAR QUERY 3");
        q = session.createQuery("SELECT COUNT(*), SUM(salario), AVG(salario) FROM Empleados");
        Object[] result = (Object[]) q.uniqueResult();
        long totalEmpleados = (long) result[0];
        Double sumaSueldos = (Double) result[1];
        Double sueldoMedio = (Double) result[2];
        System.out.println("totalEmpleados: "+totalEmpleados+"; sumaSueldos: "+sumaSueldos+"; SueldoMedio: "+sueldoMedio);
        
        System.out.println("\n\nSCALAR QUERY 4");
        q = session.createQuery("SELECT apellido, salario FROM Empleados WHERE salario = (SELECT MIN(E.salario) FROM Empleados E)");
        Object[] result1 = (Object[]) q.uniqueResult();
        String apellido = (String) result1[0];
        Float salario = (Float) result1[1];
        System.out.println("apellido: "+apellido+"; salario: "+salario);
        
        session.close();
        sf.close();
    }
    
}
