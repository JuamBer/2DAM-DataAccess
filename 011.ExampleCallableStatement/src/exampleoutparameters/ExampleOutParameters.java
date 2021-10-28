
package exampleoutparameters;

import java.sql.*;

public class ExampleOutParameters {

    public static void main(String[] args) {
        try {
            String url = "jdbc:hsqldb:.\\lib\\database\\db\\db";
            Connection con = DriverManager.getConnection(url);
            Statement stt = con.createStatement();
            CallableStatement call;
            String sql;
            
            sql = "DROP PROCEDURE suma IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE nextDeptNum IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE totalNumberDepartments IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE getDepartments IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE mostVeteranTeacher IF EXISTS";
            stt.executeUpdate(sql);
            
            
            sql = "CREATE PROCEDURE suma(IN a INT,IN b INT,OUT res INT) "
                + "SET res=a+b;";
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL suma(?,?,?)");
            call.setInt(1,1);
            call.setInt(2,1);
            call.registerOutParameter(3, Types.INTEGER);
            call.execute();
            System.out.println("SUMA: "+call.getInt(3));
            call.close();
            stt.close();
            
            stt = con.createStatement();
            sql = "CREATE PROCEDURE nextDeptNum(OUT id INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERY INT; "
                + " SET QUERY = SELECT MAX(dept_num) FROM departments; "
                + " SET id=1+QUERY;"
                + "END; ";
               
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL nextDeptNum(?)");
            call.registerOutParameter(1, Types.INTEGER);
            call.execute();
            System.out.println("NEXT_DEPT_NUM: "+call.getInt(1));
            call.close();
            stt.close();
            
                        
            stt = con.createStatement();
            sql = "CREATE PROCEDURE totalNumberDepartments(OUT res INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERY INT; "
                + " SET QUERY = SELECT COUNT(dept_num) FROM departments; "
                + " SET res=QUERY; "
                + "END; ";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL totalNumberDepartments(?)");
            call.registerOutParameter(1, Types.INTEGER);
            call.execute();
            System.out.println("TOTAL NUMBER DEPARTMENTS: "+call.getInt(1));
            call.close();
            stt.close();
            
            
            stt = con.createStatement();
            sql = "CREATE PROCEDURE getDepartments(IN name_dept VARCHAR(20), OUT res INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERY INT; "
                + " SET query = SELECT dept_num FROM departments WHERE departments.name=name_dept; "
                + " SET res =QUERY; "
                + "END;";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL getDepartments(?,?)");
            call.setString(1, "COMERCIO");
            call.registerOutParameter(2, Types.INTEGER);
            call.execute();
            System.out.println("GET DEPARMENT: "+call.getInt(2));
            call.close();
            stt.close();
            
            stt = con.createStatement();
            sql = "CREATE PROCEDURE mostVeteranTeacher(OUT res VARCHAR(15)) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERY VARCHAR(15); "
                + " SET QUERY = SELECT name FROM teachers WHERE start_date; "
                + "SET res = QUERY;";
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL mostVeteranTeacher(?)");
            call.registerOutParameter(1, Types.VARCHAR);
            call.execute();
            System.out.println("MOST VETERAN TEACHER: "+call.getString(1));
            call.close();
            stt.close();
            con.close();
            
        } catch (SQLException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
    
}
