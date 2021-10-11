
package metadataandstatements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class MetadataAndStatements {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
        
        System.out.println("");
    }

    public static void menu() {
        boolean key = true;
        while (key) {
            System.out.println("\n1. Show all teachers\n"
                    + "2. Show all departments\n"
                    + "3. Add new teacher\n"
                    + "4. Add new department\n"
                    + "5. Add salary column to teachers\n"
                    + "6. Evaluate custom query\n"
                    + "7. Quit \n");
            int op = sc.nextInt();
            switch (op) {
                case 1:
                    showTable("teachers");
                    break;
                case 2:
                    showTable("departments");
                    break;
                case 3:
                    addNewRow("teachers");
                    break;
                case 4:
                    addNewRow("departments");
                    break;
                case 5:
                    addNewColumToTable("teachers");
                    break;
                case 6:
                    evaluateQuery();
                    break;
                case 7:
                    key = false;
                    break;
            }
        }
    }

    private static void showTable(String tablename) {
        Connection connection;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM " + tablename);
            JDBChelper.showResultSet(set);
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    private static void addNewTeacher() {
//        Connection connection;
//
//        int id;
//        String name;
//        String surname;
//        String email;
//        String start_date;
//        int dept_num;
//
//        try {
//            System.out.println("Id: ");
//            id = sc.nextInt();
//            sc.nextLine();
//            System.out.println("name: ");
//            name = sc.nextLine();
//            System.out.println("surname: ");
//            surname = sc.nextLine();
//            System.out.println("email: ");
//            email = sc.nextLine();
//            System.out.println("start_date: ");
//            start_date = sc.nextLine();
//            System.out.println("dep_num: ");
//            dept_num = sc.nextInt();
//
//            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
//            Statement st = connection.createStatement();
//
//            int result = st.executeUpdate("INSERT INTO teachers VALUES ('" + id + "','" + name + "','" + surname + "','" + email + "','" + start_date + "','" + dept_num + "')");
//            System.out.println(result + " rows affecteds");
//            connection.close();
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    private static void addNewDepartment() {
//        Connection connection;
//
//        int dep_num;
//        String name;
//        String office;
//
//        try {
//            System.out.println("dep_num: ");
//            dep_num = sc.nextInt();
//            System.out.println("name: ");
//            sc.nextLine();
//            name = sc.nextLine();
//            System.out.println("office: ");
//            office = sc.nextLine();
//
//            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
//            DatabaseMetaData dbmd = connection.getMetaData();
//            Statement st = connection.createStatement();
//
//            int result = st.executeUpdate("INSERT INTO departments VALUES ('" + dep_num + "','" + name + "','" + office + "')");
//            System.out.println(result + " rows affecteds");
//            connection.close();
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
    
    private static void addNewRow(String tablename) {
        Connection connection;
        ArrayList values = new ArrayList();
        ArrayList columnsnames = new ArrayList();

        int len = 0;
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+tablename);
            ResultSetMetaData rsmd = rs.getMetaData();
            len = rsmd.getColumnCount();
            String query = null;
            for (int i = 1; i <= len; i++) {
                columnsnames.add(rsmd.getColumnName(i));
            }
            sc.nextLine();
            for (int i = 1; i <= len; i++) {
                System.out.println("Insert "+columnsnames.get(i-1)+":");
                String val = sc.nextLine();
                values.add(val);
            }

            query = "INSERT INTO "+tablename+" VALUES (";
            
            for (int i = 1; i <= len; i++) {
                if(i == len){
                    query += "'"+values.get(i-1)+"')";
                }else{
                    query += "'"+values.get(i-1)+"',";
                }
            }
          
            System.out.println("query: "+query);
            
            int result = st.executeUpdate(query);
            
            System.out.println(result + " rows affecteds");
            
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void addNewColumToTable(String tablename) {
        Connection connection;
        String newcolumname = "salary";
        String newcolumtype = "INT";

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
            Statement st = connection.createStatement();
            int result = st.executeUpdate("ALTER TABLE " + tablename + " ADD " + newcolumname + " " + newcolumtype);
            System.out.println(result + " columns affecteds");
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void evaluateQuery() {
        Connection connection;
        String query;

        try {
            System.out.println("Query to Execute:");
            sc.nextLine();
            query = sc.nextLine();
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\DB");
            Statement st = connection.createStatement();
            ResultSet set = null;
            int result = 0;
            if ((query.substring(0, 6).toUpperCase()).equals("SELECT")) {
                set = st.executeQuery(query);
                JDBChelper.showResultSet(set);
            } else {
                result = st.executeUpdate(query);
                System.out.println(result + " rows affecteds");
            }
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
