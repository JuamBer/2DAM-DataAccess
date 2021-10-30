package procedurescallable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ProceduresCallable {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ParseException {

        String url = "jdbc:hsqldb:.\\lib\\database\\db";
        Connection con = DriverManager.getConnection(url);
        Statement stt = con.createStatement();
        CallableStatement call = null;
        String sql;

        dropProcedures(stt);
        createProcedures(stt);

        menu(con, stt, call);

    }

    public static void dropProcedures(Statement stt) throws SQLException {
        String sql;

        System.out.println("Droping Procedures...");

        sql = "DROP PROCEDURE set_new_department IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE set_new_teacher IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE set_salary IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE rise_salary_prct IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE rise_salary_per_dept IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE get_newest_teacher IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE count_teachers IF EXISTS";
        stt.executeUpdate(sql);
    }

    public static void createProcedures(Statement stt) throws SQLException {
        String sql;

        System.out.println("Creating Procedures...");

        System.out.println(" ...Creating Procedure: set_new_department");
        sql = "CREATE PROCEDURE set_new_department(IN id INT, IN name VARCHAR(15), IN office VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " INSERT INTO departments VALUES id,name,office; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: set_new_teacher");
        sql = "CREATE PROCEDURE set_new_teacher(IN id INT, IN name VARCHAR(15), IN surname VARCHAR(15), IN email VARCHAR(15), IN start_date VARCHAR(15), IN id_department INT, IN salary INT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " INSERT INTO teachers VALUES id,name,surname,email,start_date,id_department,salary; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: set_salary");
        sql = "CREATE PROCEDURE set_salary(IN salar INT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " UPDATE teachers SET teachers.salary=salar; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: rise_salary_prct");
        sql = "CREATE PROCEDURE rise_salary_prct(IN p FLOAT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE OP INT; "
                + " SET OP = p/10; "
                + " UPDATE teachers SET teachers.salary=salary*OP; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: rise_salary_per_dept");
        sql = "CREATE PROCEDURE rise_salary_per_dept(p FLOAT, dept_nam VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE OP INT; "
                + " SET OP = p/10; "
                + " UPDATE teachers SET teachers.salary=salary*OP WHERE teachers.dept_num LIKE dept_nam; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: get_newest_teacher");
        sql = "CREATE PROCEDURE get_newest_teacher(OUT res VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERYDATE DATE; "
                + " DECLARE QUERYNAME VARCHAR(15); "
                + " SET QUERYDATE = SELECT MAX(start_date) FROM teachers;"
                + " SET QUERYNAME = SELECT name FROM teachers WHERE start_date=QUERYDATE LIMIT 1;"
                + " SET res = QUERYNAME;"
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: count_teachers");
        sql = "CREATE PROCEDURE count_teachers(IN dept_nam VARCHAR(15), OUT res INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE NUMTEACHERS INT; "
                + " SET NUMTEACHERS = SELECT COUNT(name) FROM teachers WHERE teachers.dept_num=dept_nam;"
                + " SET res = NUMTEACHERS; "
                + "END; ";
        stt.executeUpdate(sql);

    }

    public static void menu(Connection con, Statement stt, CallableStatement call) throws SQLException, ParseException {
        boolean key = true;

        while (key) {
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
            JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
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

    private static void setInsert(Connection con, Statement stt, CallableStatement call, String table) throws SQLException, ParseException {
        ArrayList<String> values = new ArrayList();
        ArrayList<String> columnsnames = new ArrayList();
        ArrayList<Integer> columnstypes = new ArrayList();
        String func = null;
        int len = 0;

        ResultSet rs = stt.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData rsmd = rs.getMetaData();
        len = rsmd.getColumnCount();
        String query = null;

        for (int i = 1; i <= len; i++) {
            columnsnames.add(rsmd.getColumnName(i));
            columnstypes.add(rsmd.getColumnType(i));
        }
        sc.nextLine();
        for (int i = 1; i <= len; i++) {
            System.out.println("Insert " + columnsnames.get(i - 1) + ":");
            String val = sc.nextLine();
            values.add(val);
        }

        if (table.equals("teachers")) {
            func = "set_new_teacher(?,?,?,?,?,?,?)";
        } else if (table.equals("departments")) {
            func = "set_new_department(?,?,?)";
        }
        call = con.prepareCall("CALL " + func);

        for (int i = 1; i <= values.size(); i++) {
            System.out.println(i);
            int s = (int) columnstypes.get(i - 1);
            String val = (String) values.get(i - 1);
            switch (s) {
                case 16: //Boolean
                    if (val.equals("true")) {
                        call.setBoolean(i, true);
                    } else {
                        call.setBoolean(i, false);
                    }
                    break;
                case 4: //Integer
                    call.setInt(i, Integer.parseInt(val));
                    break;
                case 12: //String
                    call.setString(i, val);
                    break;
                case 91: //Date
                    //System.out.println(val);
                    //String[] date = val.split("-");
                    //int year = Integer.parseInt(date[0]);
                    //int moth = Integer.parseInt(date[1]);
                    //int day = Integer.parseInt(date[2]);
                    //System.out.println(year+" "+moth+" "+day);
                    //Date d = new Date(year,moth,day);  
                    //System.out.println(d);
                    call.setString(i, val);

                default:
                    call.setString(i, null);
                    break;
            }

        }

        call.execute();

    }

    private static void setSalary(Connection con, Statement stt, CallableStatement call, int sal) throws SQLException {
        call = con.prepareCall("CALL set_salary(?)");
        call.setInt(1, sal);
        call.execute();
    }
    private static void raiseSalary(Connection con, Statement stt, CallableStatement call, int per) throws SQLException {
        call = con.prepareCall("CALL rise_salary_prct(?)");
        call.setInt(1, per);
        call.execute();
    }
    private static void raiseSalaryDepartment(Connection con, Statement stt, CallableStatement call, int per, String dep) throws SQLException {
        call = con.prepareCall("CALL rise_salary_per_dept(?,?)");
        call.setInt(1, per);
        call.setString(2, dep);
        call.execute();
    }
    private static void getNewestTeacher(Connection con, Statement stt, CallableStatement call) throws SQLException {
        call = con.prepareCall("CALL get_newest_teacher(?)");
        call.registerOutParameter(1, Types.VARCHAR);
        call.execute();
        
        System.out.println("News Teacher: "+call.getString(1));
    }
    private static void countTeachersOfDepartment(Connection con, Statement stt, CallableStatement call, String dep) throws SQLException {
        call = con.prepareCall("CALL count_teachers(?,?)");
        call.setString(1, dep);
        call.registerOutParameter(2, Types.INTEGER);
        call.execute();
        
        System.out.println("Number of teacher of "+dep+" department: "+call.getInt(2));
    }

}
