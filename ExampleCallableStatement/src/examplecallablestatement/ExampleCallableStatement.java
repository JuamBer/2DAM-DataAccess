
package examplecallablestatement;

import java.sql.*;

public class ExampleCallableStatement {

    public static void main(String[] args) {
        try {
            String url = "jdbc:hsqldb:.\\lib\\database\\db\\db";
            Connection con = DriverManager.getConnection(url);
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE changeOffice IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE changeOffice1 IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE changeOffice2 IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE changeOffice3 IF EXISTS";
            stt.executeUpdate(sql);
            
            /*sql = "CREATE PROCEDURE changeOffice()" +
                    "    modifies sql data " +
                    "    UPDATE departments SET office = 'OFFICE'" +
                    ";";
            
            stt.executeUpdate(sql);
            
            sql = "SELECT * FROM departments";
            
            JDBChelper.showResultSet(stt.executeQuery(sql));
            
            CallableStatement call = con.prepareCall("CALL changeOffice()");
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sql));*/
            
            sql = "CREATE PROCEDURE changeOffice1(offi VARCHAR(20))" +
                    "    modifies sql data " +
                    "    UPDATE departments SET office = offi;";
            
            stt.executeUpdate(sql);
            CallableStatement call = con.prepareCall("CALL changeOffice1(?)");
            String variable = "PROCEDURE1";
            call.setString(1,variable);
            call.execute();
            sql = "SELECT * FROM departments";
            JDBChelper.showResultSet(stt.executeQuery(sql));
            
            /*sql = "CREATE PROCEDURE changeOffice2(offi VARCHAR(20))" +
                    "    modifies sql data " +
                    "    UPDATE departments SET office = offi WHERE name = 'INFORMÁTICA';";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL changeOffice2(?)");
            call.setString('PROCEDURE2');
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sql));
            
            stt = con.createStatement();
            sql = "CREATE PROCEDURE changeOffice3(pattern VARCHAR(20), offi VARCHAR(20))" +
                    "    modifies sql data" +
                    "    UPDATE departments SET office = offi WHERE name LIKE pattern;";
            
            call = con.prepareCall("CALL changeOffice3(?,?)");
            call.setString('%O');
            call.setString('PROCEDURE3');
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sql));*/
            
            
            stt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
    
}
