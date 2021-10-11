package activityStatements;
import java.sql.*;
import java.util.ArrayList;
import statements.JDBChelper;
import java.util.Scanner;

public class activityStatements {
    
    public static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        menu();
        
    }
    
    public static void menu(){
        System.out.println("1. Show all teachers\n"
                            + "2. Show all departments\n"
                            + "3. Add new teacher\n"
                            + "4. Add new department\n"
                            + "5. Add salary column to teachers\n"
                            + "6. Evaluate custom query\n"
                            + "7. Quit");
        int op = sc.nextInt();
        switch(op){
                    case 1: showTable("teachers"); break;
                    case 2: showTable("departments"); break;
                    case 3: addNewTeacher(); break;
                    case 4: addNewDepartment(); break;
                    case 5: addNewColumToTable("teachers"); break;
                    case 6: evaluateQuery(); break;
                    case 7: break;
                }
    }

    private static void showTable(String tablename) {
        Connection connection;    
        try {
            
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            DatabaseMetaData dbmd = connection.getMetaData();
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM "+tablename);
            JDBChelper.showResultSet(set);
            connection.close();
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void addNewTeacher() {
        Connection connection;  
        
        int id;
        String name;
        String surname;
        String email;
        String start_date;
        int dep_num;
        
        try {
            System.out.println("Id: ");
            id = sc.nextInt();
            System.out.println("name: ");
            name = sc.nextLine();
            System.out.println("surname: ");
            surname = sc.nextLine();
            System.out.println("email: ");
            email = sc.nextLine();
            System.out.println("start_date: ");
            start_date = sc.nextLine();
            System.out.println("dep_num: ");
            dep_num = sc.nextInt();
            
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            DatabaseMetaData dbmd = connection.getMetaData();
            Statement st = connection.createStatement();
        
            int result = st.executeUpdate("INSERT INTO teachers VALUES ("+id+","+name+","+surname+","+email+","+start_date+","+dep_num+")");
            System.out.println(result + "rows affecteds");
            connection.close();
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void addNewDepartment() {
        Connection connection;  
        
        int id;
        String name;
        String surname;
        String email;
        String start_date;
        int dep_num;
        
        try {
            System.out.println("Id: ");
            id = sc.nextInt();
            System.out.println("name: ");
            name = sc.nextLine();
            System.out.println("surname: ");
            surname = sc.nextLine();
            System.out.println("email: ");
            email = sc.nextLine();
            System.out.println("start_date: ");
            start_date = sc.nextLine();
            System.out.println("dep_num: ");
            dep_num = sc.nextInt();
            
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            DatabaseMetaData dbmd = connection.getMetaData();
            Statement st = connection.createStatement();
        
            int result = st.executeUpdate("INSERT INTO teachers VALUES ("+id+","+name+","+surname+","+email+","+start_date+","+dep_num+")");
            System.out.println(result + "rows affecteds");
            connection.close();
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void addNewRow(String tablename) {
        Connection connection;     
      
    }

    private static void addNewColumToTable(String tablename) {
        Connection connection;  
        String newcolumname;
        String newcolumtype;
        
        try {
            System.out.println("New Colum Name:");
            newcolumname = sc.nextLine();
            System.out.println("New Colum Type:");
            newcolumtype = sc.nextLine();
            
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            Statement st = connection.createStatement();
            int result = st.executeUpdate("ALTER TABLE "+tablename+" ADD " +newcolumtype);
            System.out.println(result+" columns affecteds");
            connection.close();
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
    }

    private static void evaluateQuery() {
        Connection connection;  
        String query;
        
        try {
            System.out.println("Query to Execute:");
            query = sc.nextLine();
            
            connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            
            
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);

                JDBCHelper.showResultSet(set);
          
            
            connection.close();
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      
    }
}
