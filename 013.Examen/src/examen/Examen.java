package examen;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class Examen {

    public static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws SQLException, ParseException {
        chooseDataBase();
    }
    
    /** Choose the database system to connec
     * @throws java.sql.SQLException
     * @throws java.text.ParseException*/
    public static void chooseDataBase() throws SQLException, ParseException {
        System.out.println("\n¿Con qué SGBD quiere conectar? \n1. SQLite\n2. Derby\n3. HSQLDB\n4. Exit");
        int op = sc.nextInt();
        String url;
        Connection con;
        switch (op) {
            case 1:
                url = "jdbc:sqlite:lib\\examen\\sqlite\\database";
                con = DriverManager.getConnection(url);
                menu(con,"SQLITE");
                break;
            case 2:
                url = "jdbc:derby:lib\\examen\\derby\\database";
                con = DriverManager.getConnection(url);
                menu(con,"DERBY");
                break;
            case 3:
                url = "jdbc:hsqldb:lib\\examen\\hsqldb\\database";
                con = DriverManager.getConnection(url);
                menu(con,"HSQLDB");
                break;
            case 4:
                break;
        }
        System.out.println("");
    }
    
    
    /** Show the menu to choose an optio
     * @param con
     * @throws java.sql.SQLException
     * @throws java.text.ParseException*/
    public static void menu(Connection con,String dbtype) throws SQLException, ParseException {
        System.out.println("\n\n------"+dbtype+" DataBase------");
        boolean key = true;

        while (key) {
            
            System.out.println("\n\n¿Qué operación desea realizar?\n"
                    + "1. Mostrar un listado de alumnos\n"
                    + "2. Mostrar un litado de municipios\n"
                    + "3. Introducir un nuevo alumno (PreparedStatement)\n"
                    + "4. Introducir un nuevo municipio (Procedimiento)\n"
                    + "5. Exit");
            int op = sc.nextInt();
            sc.nextLine();
            
            switch (op) {
                case 1:
                    mostrar_alumnos(con);
                    break;
                case 2:
                    mostrar_municipios(con);
                    break;
                case 3:
                    introduce_alumno(con);
                    break;
                case 4:
                    introduce_municipio(con);
                    break;
                case 5:
                    key = false;
                    break;
            }
        }
    }
    
    /** Muestra el nombre, apllidos y municipio de todos los alumnos*/
    private static void mostrar_alumnos(Connection con) throws SQLException {
        System.out.println("\n\n------Mostrar Alumnos------");
        Statement st = con.createStatement();
         
        ResultSet set = st.executeQuery("SELECT nombre,apellidos,id_municipio FROM alumnos");
        JDBChelper.showResultSet(set);
        st.close();
    }
    
    /** Muestra toda la información de todos los municipios*/
    private static void mostrar_municipios(Connection con) throws SQLException {
        System.out.println("\n\n------Mostrar Municipios------");
        Statement st = con.createStatement();
         
        ResultSet set = st.executeQuery("SELECT * FROM municipios");
        JDBChelper.showResultSet(set);
        st.close();
    }
    
    /** Pide unos datos para introducir un nuevo alumno*/
    private static void introduce_alumno(Connection con) throws SQLException {
        System.out.println("\n\n------Tabla Alumnos Antes------");
        Statement st = con.createStatement();
        ResultSet set = st.executeQuery("SELECT * FROM alumnos");
        JDBChelper.showResultSet(set);
        st.close();
            
        System.out.println("\n\n------Introduce Alumno------");
        System.out.println("Introduce NIA:");
        String nia = sc.nextLine();
        System.out.println("Introduce nombre:");
        String nombre = sc.nextLine();
        System.out.println("Introduce appelidos:");
        String apellidos = sc.nextLine();
        System.out.println("Introduce id del municipio:");
        String id_municipio = sc.nextLine();
        
        String sql="INSERT INTO alumnos VALUES(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, nia);
        ps.setString(2, nombre);
        ps.setString(3, apellidos);
        ps.setString(4, id_municipio);
        
        int rows = ps.executeUpdate();
        System.out.println(rows + " affected");
        ps.close();
        
        System.out.println("\n\n------Tabla Alumnos Despues------");
        st = con.createStatement();
        set = st.executeQuery("SELECT * FROM alumnos");
        JDBChelper.showResultSet(set);
        st.close();
    }
    
    /** Pide unos datos para introducir un nuevo municipio*/
    private static void introduce_municipio(Connection con) throws SQLException {
        String drivername = con.getMetaData().getDriverName();
        
        if(drivername.substring(0,4).equals("HSQL")){
            System.out.println("\n\n------Tabla Municipios Antes------");
            Statement st = con.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM municipios");
            JDBChelper.showResultSet(set);

            System.out.println("\n\n------Introduce Municipio------");

            String sql;
            sql = "DROP PROCEDURE nuevo_municipio IF EXISTS";
            st.executeUpdate(sql);
            sql = "CREATE PROCEDURE nuevo_municipio(IN cod VARCHAR(5), IN nombre VARCHAR(10), IN n_habitantes INT) "
                    + "MODIFIES SQL DATA "
                    + "BEGIN ATOMIC "
                    + " INSERT INTO municipios VALUES cod,nombre,n_habitantes; "
                    + "END; ";
            st.executeUpdate(sql);
            st.close();

            System.out.println("Introduce id_municipio:");
            String id_municipio = sc.nextLine();
            System.out.println("Introduce nombre:");
            String nombre = sc.nextLine();
            System.out.println("Introduce n_habitantes:");
            int n_habitantes = sc.nextInt();
            sc.nextLine();

            CallableStatement call = con.prepareCall("CALL nuevo_municipio(?,?,?)");
            call.setString(1,id_municipio);
            call.setString(2,nombre);
            call.setInt(3,n_habitantes);
            call.execute();
            call.close();

            System.out.println("\n\n------Tabla Municipios Despues------");
            st = con.createStatement();
            set = st.executeQuery("SELECT * FROM municipios");
            JDBChelper.showResultSet(set);
            st.close();
        }else{
            System.out.println(drivername+" No Soporta Procedimientos");
        }
    }
}
