package ConnectionDB;

import java.util.Scanner;
import java.sql.*;

public class ConnectionDB {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        System.out.println("\n\nChoose the database: \n1. SQLite Database\n2. Derby Database\n3. HSQLDB Database\n4. Exit\n\n");
        int op = sc.nextInt();
        switch (op) {
            case 1:
                connectDataBase(1);
                menu();
                break;
            case 2:
                connectDataBase(2);
                menu();
                break;
            case 3:
                connectDataBase(3);
                menu();
                break;
            case 4:
                break;
        }
        System.out.println("");
    }

    private static void connectDataBase(int dbtype) {
        String url = null;
        try {
            switch (dbtype) {
                case 1:
                    url = "jdbc:sqlite:lib\\db\\sqlite\\act2.1";
                    break;
                case 2:
                    url = "jdbc:derby:lib\\db\\derby\\act2.2";
                    break;
                case 3:
                    url = "jdbc:hsqldb:lib\\db\\hsqldb\\act2.3";
                    break;

            }
            
            Connection connection = DriverManager.getConnection(url);
            
            showDBInformation(connection,dbtype);
            
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    } 

    private static void showDBInformation(Connection connection,int dbtype) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            
            String databaseproductname = connection.getMetaData().getDatabaseProductName();
            String drivername = connection.getMetaData().getDriverName();
            String url = connection.getMetaData().getURL();
            String username = connection.getMetaData().getUserName();
            
            System.out.println(
            "----------------------------------\n" +
            "DATABASE INFORMATION\n" +
            "----------------------------------\n" +
            "Name: SQLite\n" +
            "Driver: SQLite JDBC\n" +
            "URL: jdbc:sqlite:D:\\Sources\\DataAccess\\DBs\\sqlite\\act2.1\n" +
            "User: null\n" +
            "----------------------------------\n"
            + "TABLES INFORMATION");
            
            ResultSet resul = null;
            switch (dbtype) {
                case 1:
                    resul = dbmd.getTables(null, null, null, null); //all tables
                    break;
                case 2:
                    resul = dbmd.getTables(null,"APP", null, null); //all tables
                    break;
                case 3:
                    resul = dbmd.getTables(null,"PUBLIC", null, null); //all tables
                    break;

            }
            
            while (resul.next()) {
                String catalogo = resul.getString(1); // column 1: TABLE_CAT
                String esquema = resul.getString(2); // column 2: TABLE_SCHEM
                String tabla = resul.getString(3); // column 3: TABLE_NAME
                String tipo = resul.getString(4); // column 4: TABLE_TYPE

                System.out.println("----------------------------------\n"+
                "TABLE NAME: "+tabla+"; Catalog: "+catalogo+"; Schema: "+esquema+"; Type:"+tipo+"\n"+
                "*** COLUMNS of TABLE "+tabla+" ***");
                
                ResultSet columnas = dbmd.getColumns(null, null, tabla, null);

                while (columnas.next()) {
                    String nombreCol = columnas.getString("COLUMN_NAME");
                    String tipoCol = columnas.getString("TYPE_NAME");
                    String tamCol = columnas.getString("COLUMN_SIZE");
                    String nula = columnas.getString("IS_NULLABLE");

                    System.out.println("\tColumn name: "+nombreCol+"; Type: "+tipoCol+"; IsNullable: "+nula);
                }

            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
