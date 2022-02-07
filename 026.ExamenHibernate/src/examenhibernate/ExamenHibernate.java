package examenhibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.hibernate.*;
import pojos.*;

public class ExamenHibernate {
    
    private static final SessionFactory sf = NewHibernateUtil.getSessionFactory();
    private static Scanner sc = new Scanner(System.in, "ISO-8859-1");
     
    public static void main(String[] args) {
        getIdAndName();
        getMunicipio();
        setUser();
    }
    
    public static void getIdAndName(){
        System.out.println("getIdAndName()");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction(); 
        String hql = "SELECT id,nombre FROM Municipios";
        Query query = session.createQuery(hql);
        List<Object[]> municipios = query.list();
        
        for (Object[] municipio : municipios) {
            String id = (String) municipio[0];
            String nombre = (String) municipio[1];
            
            System.out.println("\tID: "+id+"; Nombre: "+nombre);
        }
        tx.commit();
        session.close();
    }
    
    public static void getMunicipio(){
        System.out.println("getMunicipio()");
        Session session = sf.openSession();
        
            System.out.println("Insert id: ");
            String id = sc.nextLine();
            
            //TODO: Implements Code
        session.close();
    }
    
    public static void setUser(){
        System.out.println("setUser()");
        Session session = sf.openSession();
        //TODO: Implements Code
        session.close();
    }
    
}
