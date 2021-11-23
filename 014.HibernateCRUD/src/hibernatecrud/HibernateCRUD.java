package hibernatecrud;

import hibernate_resources.HibernateUtil;
import org.hibernate.*;
import pojos.*;

public class HibernateCRUD {
    
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    public static void main(String[] args) {
        /*
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
        */
        /*
        Session sesion = sessionFactory.openSession();
        System.out.println("===================================");
        System.out.println("DATOS DEL DEPARTAMENTO 1");
        Departamentos dep;
        dep = (Departamentos) sesion.get(Departamentos.class, 1);
        System.out.println("Nombre: " + dep.getDnombre());
        System.out.println("Localidad: " + dep.getLoc());
        System.out.println("===================================");
        System.out.println("EMPLEADOS DEL DEPARTAMENTO 1");
        Set<Empleados> listaemple = dep.getEmpleadoses(); // Obtenemos sus empleados System.out.println("N° de empleados: " + listaemple.size());
         Como Set es iterable utilizaremos el bucle for "each"
        for (Empleados emple : listaemple){
            System.out.println("Apellido: " +emple.getApellido () +"; Salario: " + emple.getSalario ());
        }
        System.out.println("Fin de empleados");
        sesion.close();
        */
        //SELECT (CON CLAVES AJENAS)
        /*
        SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
        Session sesion = sesionf.openSession();
        System.out.println("===================================");
        System.out.println("DATOS DEL DEPARTAMENTO 1");
        Departamentos dep;
        dep = (Departamentos) sesion.get(Departamentos.class, 1);
        System.out.println("Nombre: " + dep.getDnombre());
        System.out.println("Localidad: " + dep.getLoc());
        System.out.println("===================================");
        System.out.println("EMPLEADOS DEL DEPARTAMENTO 1");
        Set<Empleados> listaemple = dep.getEmpleadoses(); // Obtenemos sus empleados System.out.println("N° de empleados: " + listaemple.size());
        for (Empleados emple : listaemple){
            System.out.println("Apellido: " +emple.getApellido () +"; Salario: " + emple.getSalario ());
        }
        System.out.println("Fin de empleados");
        sesion.close();
        */
        
        //INSERT (CON CLAVE AJENA)
        /*
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        dep = (Departamentos) sesion.get(Departamentos.class, 1);
        Date d = DateFormat.getDateInstance(DateFormat.SHORT).parse("02/02/2002");
        Empleados e = new Empleados(99,dep,"Saez", "Informatico", 19, d,(float) 2000.0, (float) 200.0);
        sesion.save(e);
        tx.commit();
        sesion.close();
        */
        
        //DELETE
        /*
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Empleados emp = (Empleados) sesion.get(Empleados.class,99);
        sesion.delete(emp);
        tx.commit();
        sesion.close();
        */
        
        //UPDATE IMPLÍCITO
        /*
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Empleados emp = (Empleados) sesion.get(Empleados.class,2);
        System.out.println("El salario antiguo era: "+emp.getSalario());
        System.out.println("La comisión antigua era: "+emp.getComision());
        emp.setSalario((float) 300);
        emp.setComision((float) 1);
        tx.commit();
        sesion.close();
        sesion = sessionFactory.openSession();
        emp = (Empleados) sesion.get(Empleados.class,2);
        System.out.println("El salariget(Empleados.class,2);o antiguo era: "+emp.getSalario());
        System.out.println("La comisión antigua era: "+emp.getComision());
        sesion.close();
        */
        
        //UPDATE EXPLÍCITO
        /*
        Session sesion = sessionFactory.openSession(); 
        Empleados emp = (Empleados) sesion.get(Empleados.class, 2);
        sesion.close(); //emp is detached
        
        emp.setSalario((float) 3000); 
        emp.setComision((float) 10);
        
        sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update (emp);//emp is now persistent
        tx.commit();//amp is modified in the database
        emp = (Empleados) sesion.get(Empleados.class, 2);
        sesion.close();
        */
        
        sessionFactory.close();
    }
    
}
