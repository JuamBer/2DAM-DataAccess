package procedurescallable;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;


public class ProceduresCallable {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ParseException {

        String url = "jdbc:hsqldb:.\\lib\\database\\db";
        
        Connection con = DriverManager.getConnection(url);
        Statement stt = con.createStatement();
        CallableStatement call = null;
            
        Procedures.dropProcedures(stt);
        Procedures.createProcedures(stt);
            
        menu(con, stt, call);
        
        stt.close();
        con.close();
    }
    
    
    
    
    /** Show the menu to choose an option*/
    public static void menu(Connection con, Statement stt, CallableStatement call) throws SQLException, ParseException {
        boolean key = true;

        while (key) {
            
            System.out.println("\n\n");

            System.out.println("\nChoose an option:\n"
                    + "1. Set new department\n"
                    + "2. Set new teacher\n"
                    + "3. Set salary to...\n"
                    + "4. Rise salary a %\n"
                    + "5. Rise salary a % to a department like...\n"
                    + "6. Get newest teacher surname\n"
                    + "7. Count teachers of department...\n"
                    + "8. Quit\n");
            int op = sc.nextInt();

            int salary;
            String department;
            switch (op) {
                case 1:
                    System.out.println("-----Departments Table Before-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
                    System.out.println("");
            
                    setInsert(con, stt, call, "departments");
                    break;
                case 2:
                    System.out.println("-----Teachers Table Before-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
                    System.out.println("");
                    
                    setInsert(con, stt, call, "teachers");
                    break;
                case 3:
                    System.out.println("-----Teachers Table Before Updating Salary-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
                    System.out.println("");
                    
                    System.out.println("Set salary to: ");
                    salary = sc.nextInt();
                    setSalary(con, stt, call, salary);
                    break;
                case 4:
                    System.out.println("-----Teachers Table Before Updating Salary-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
                    System.out.println("");
                    
                    System.out.println("Raise salary: ");
                    salary = sc.nextInt();
                    raiseSalary(con, stt, call, salary);    
                    break;
                case 5:
                    System.out.println("-----Teachers Table Before Updating Salary-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
                    System.out.println("");
        
                    System.out.println("Raise salary: ");
                    salary = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println("-----Departments Table To Consult-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
                    System.out.println("");
                    
                    System.out.println("Department: ");
                    department = sc.nextLine();
                    raiseSalaryDepartment(con, stt, call, salary, department);       
                    break;
                case 6:
                    getNewestTeacher(con, stt, call);
                    break;
                case 7:
                    System.out.println("-----Departments Table To Consult-----");
                    JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
                    System.out.println("");

                    sc.nextLine();
                    System.out.println("Department: ");
                    department = sc.nextLine();
                    countTeachersOfDepartment(con, stt, call, department);
                    break;
                case 8:
                    key = false;
                    break;
            }

        }
    }
    /**Uses a procedure to insert data in tables of the database. */
    private static void setInsert(Connection con, Statement stt, CallableStatement call, String table) throws SQLException, ParseException {
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> columnsNames = new ArrayList<>();
        ArrayList<Integer> columnsTypes = new ArrayList<>();
        String procedure = null;
        int numColumns;

        ResultSet rs = stt.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData rsmd = rs.getMetaData();
        numColumns = rsmd.getColumnCount();

        for (int i = 1; i <= numColumns; i++) {
            columnsNames.add(rsmd.getColumnName(i));
            columnsTypes.add(rsmd.getColumnType(i));
        }
        sc.nextLine();
        for (int i = 1; i <= numColumns; i++) {
            System.out.println("Insert " + columnsNames.get(i - 1) + ":");
            String val = sc.nextLine();
            values.add(val);
        }

        if (table.equals("teachers")) {
            procedure = "set_new_teacher(?,?,?,?,?,?,?)";
        } else if (table.equals("departments")) {
            procedure = "set_new_department(?,?,?)";
        }
       
        call = con.prepareCall("CALL " + procedure);
            
        for (int i = 1; i <= values.size(); i++) {
            int columType = (int) columnsTypes.get(i - 1);
            String val = values.get(i - 1);
            switch (columType) {
                case 4: //Integer
                    call.setInt(i, Integer.parseInt(val));
                    break;
                case 12: //String
                    call.setString(i, val);
                    break;
                case 91: //Date
                    Date date = Date.valueOf(val);
                    call.setDate(i, date);
                    break;
                default:
                    call.setString(i, null);
                    break;
            }

        }
         
        call.execute();
        
        if (table.equals("teachers")) {
            System.out.println("-----Teachers Table After-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
            System.out.println("");
        } else if (table.equals("departments")) {
            System.out.println("-----Departments Table After-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
            System.out.println("");
        }
    }
    /**Uses a procedure to set salary. */
    private static void setSalary(Connection con, Statement stt, CallableStatement call, int salary) throws SQLException {
        call = con.prepareCall("CALL set_salary(?)");
        call.setInt(1, salary);
        call.execute();
        
        System.out.println("-----Teachers Table After Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
            
    }
    /**Uses a procedure to raise salary. */
    private static void raiseSalary(Connection con, Statement stt, CallableStatement call, int salary) throws SQLException {
        call = con.prepareCall("CALL rise_salary_prct(?)");
        call.setInt(1, salary);
        call.execute();
        
        System.out.println("-----Teachers Table After Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }
    /**Uses a procedure to raise the salary in a specific department. */
    private static void raiseSalaryDepartment(Connection con, Statement stt, CallableStatement call, int salary, String department) throws SQLException {
        call = con.prepareCall("CALL rise_salary_per_dept(?,?)");
        call.setInt(1, salary);
        call.setString(2, department);
        call.execute();
        
        System.out.println("-----Teachers Table After Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }
    /**Uses a procedure to get the newest teacher and show it. */
    private static void getNewestTeacher(Connection con, Statement stt, CallableStatement call) throws SQLException {
        call = con.prepareCall("CALL get_newest_teacher(?)");
        call.registerOutParameter(1, Types.VARCHAR);
        call.execute();
        
        System.out.println("News Teacher: "+call.getString(1));
        
        System.out.println("\n-----Teachers Table-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }
    /**Uses a procedure to get the number of teachers in a specific department and show it. */
    private static void countTeachersOfDepartment(Connection con, Statement stt, CallableStatement call, String department) throws SQLException {
        call = con.prepareCall("CALL count_teachers(?,?)");
        call.setString(1, department);
        call.registerOutParameter(2, Types.INTEGER);
        call.execute();
        
        System.out.println("Number of teacher of "+department+" department: "+call.getInt(2));
        
        System.out.println("\n-----Teachers Table-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }

}
