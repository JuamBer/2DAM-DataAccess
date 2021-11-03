package procedurescallable;

import java.sql.*;
import java.util.*;
import java.text.ParseException;

public class ProceduresCallable {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ParseException {

        String url = "jdbc:hsqldb:.\\lib\\database\\db";
        Connection con = DriverManager.getConnection(url);
        Statement stt = con.createStatement();
        CallableStatement call = null;
        String sql;

        Procedures.dropProcedures(stt);
        Procedures.createProcedures(stt);

        menu(con, stt, call);

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

            switch (op) {
                case 1:
                    setInsert(con, stt, call, "departments");
                    break;
                case 2:
                    setInsert(con, stt, call, "teachers");
                    break;
                case 3:
                    System.out.println("Set salary to: ");
                    int sal = sc.nextInt();
                    setSalary(con, stt, call, sal);
                    break;
                case 4:
                    System.out.println("Raise salary: ");
                    int per = sc.nextInt();
                    raiseSalary(con, stt, call, per);    
                    break;
                case 5:
                    System.out.println("Raise salary: ");
                    int sala = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Department: ");
                    String dep = sc.nextLine();
                    raiseSalaryDepartment(con, stt, call, sala, dep);       
                    break;
                case 6:
                    getNewestTeacher(con, stt, call);
                    break;
                case 7:
                    System.out.println("Department: ");
                    sc.nextLine();
                    String department = sc.nextLine();
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
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<String> columnsNames = new ArrayList<String>();
        ArrayList<Integer> columnsTypes = new ArrayList<Integer>();
        String procedure = null;
        int numColumns = 0;

        ResultSet rs = stt.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData rsmd = rs.getMetaData();
        numColumns = rsmd.getColumnCount();
        String query = null;

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
            System.out.println("-----Teachers Table Before Updating Salary-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
            System.out.println("");
            
            procedure = "set_new_teacher(?,?,?,?,?,?,?)";
        } else if (table.equals("departments")) {
            System.out.println("-----Departments Table Before Updating Salary-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
            System.out.println("");
            
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
                    call.setDate(i, java.sql.Date.valueOf(val));
                    break;
                default:
                    call.setString(i, null);
                    break;
            }

        }
         
        call.execute();
        
        if (table.equals("teachers")) {
            System.out.println("-----Teachers Table After Updating Salary-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
            System.out.println("");
        } else if (table.equals("departments")) {
            System.out.println("-----Departments Table After Updating Salary-----");
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
            System.out.println("");
        }
    }
    /**Uses a procedure to set salary. */
    private static void setSalary(Connection con, Statement stt, CallableStatement call, int sal) throws SQLException {
        System.out.println("-----Teachers Table Before Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
        
        call = con.prepareCall("CALL set_salary(?)");
        call.setInt(1, sal);
        call.execute();
        
        System.out.println("-----Teachers Table After Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
            
    }
    /**Uses a procedure to raise salary. */
    private static void raiseSalary(Connection con, Statement stt, CallableStatement call, int per) throws SQLException {
        System.out.println("-----Teachers Table Before Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
        
        call = con.prepareCall("CALL rise_salary_prct(?)");
        call.setInt(1, per);
        call.execute();
        
        System.out.println("-----Teachers Table After Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }
    /**Uses a procedure to raise the salary in a specific department. */
    private static void raiseSalaryDepartment(Connection con, Statement stt, CallableStatement call, int per, String dep) throws SQLException {
        System.out.println("-----Teachers Table Before Updating Salary-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
        
        call = con.prepareCall("CALL rise_salary_per_dept(?,?)");
        call.setInt(1, per);
        call.setString(2, dep);
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
    private static void countTeachersOfDepartment(Connection con, Statement stt, CallableStatement call, String dep) throws SQLException {
        call = con.prepareCall("CALL count_teachers(?,?)");
        call.setString(1, dep);
        call.registerOutParameter(2, Types.INTEGER);
        call.execute();
        
        System.out.println("Number of teacher of "+dep+" department: "+call.getInt(2));
        
        System.out.println("\n-----Teachers Table-----");
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        System.out.println("");
    }

}
