package procedurescallable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

public class ProceduresCallable {
    
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:hsqldb:.\\lib\\database\\db";
        Connection con = DriverManager.getConnection(url);
        Statement stt = con.createStatement();
        CallableStatement call = null;
        String sql;

        

        dropProcedures(stt);
        createProcedures(stt);
        
        menu(con,stt,call);

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
        sql = "CREATE PROCEDURE rise_salary_per_dept(p FLOAT, dept_name VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE OP INT; "
                + " SET OP = p/10; "
                + " UPDATE teachers SET teachers.salary=salary*OP WHERE teachers.dept_num LIKE dept_name; "
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
        sql = "CREATE PROCEDURE count_teachers(IN dept_name VARCHAR(15), OUT res INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE NUMTEACHERS INT; "
                + " SET NUMTEACHERS = SELECT COUNT(name) FROM teachers WHERE teachers.dept_num=dept_name;"
                + " SET res = NUMTEACHERS; "
                + "END; ";
        stt.executeUpdate(sql);

    }

    public static void menu(Connection con,Statement stt,CallableStatement call) throws SQLException {
        boolean key = true;
        
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM teachers"));
        JDBChelper.showResultSet(stt.executeQuery("SELECT * FROM departments"));
        System.out.println("\n\n");
        while (key) {
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
                    setInsert(con,stt,call,"departments");
                    break;
                case 2:
                    setInsert(con,stt,call,"teachers");
                    break;
                case 3:
                   
                    break;
                case 4:
                   
                    break;
                case 5:
                   
                    break;
                case 6:
                  
                    break;
                case 7:
                  
                    break;
                case 8:
                    key = false;
                    break;
            }

        }
    }

    private static void setInsert(Connection con,Statement stt,CallableStatement call, String table) throws SQLException {
        ArrayList values = new ArrayList();
        ArrayList columnsnames = new ArrayList();
        String func = null;
        int len = 0;

        
            ResultSet rs = stt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            len = rsmd.getColumnCount();
            String query = null;

            for (int i = 1; i <= len; i++) {
                columnsnames.add(rsmd.getColumnName(i));
            }
            sc.nextLine();
            for (int i = 1; i <= len; i++) {
                System.out.println("Insert " + columnsnames.get(i - 1) + ":");
                String val = sc.nextLine();
                values.add(val);
            }
            
            if(table.equals("teachers")){
                func = "set_new_department(?,?,?)";
            }else if(table.equals("departments")){
                func = "set_new_teacher(?,?,?,?,?,?,?)";
            }
            call = con.prepareCall("CALL "+func);
            
            for (int i = 1; i <= len; i++) {
                call.setString(i, (String) values.get(i-1));
            }
            
            call.execute();
       
    }

}
