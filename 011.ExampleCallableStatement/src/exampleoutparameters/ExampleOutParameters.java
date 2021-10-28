
package examplecallablestatement;

import java.sql.*;

public class ExampleCallableStatement {

    public static void main(String[] args) {
        try {
            String url = "jdbc:hsqldb:.\\lib\\database\\db\\db";
            Connection con = DriverManager.getConnection(url);
            Statement stt = con.createStatement();
            CallableStatement call;
            String sql;
            String sqlselect = "SELECT * FROM departments";
            
            sql = "DROP PROCEDURE changeOffice1 IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE changeOffice2 IF EXISTS";
            stt.executeUpdate(sql);
            sql = "DROP PROCEDURE changeOffice3 IF EXISTS";
            stt.executeUpdate(sql);
            
            
            System.out.println("ChangeOffice1");
            sql =   "CREATE PROCEDURE changeOffice1(offi VARCHAR(20))" +
                    "    modifies sql data " +
                    "    UPDATE departments SET office = offi;";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL changeOffice1(?)");
            call.setString(1,"PROCEDURE1");
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sqlselect));
            System.out.println("\n\n");
            
            
            System.out.println("ChangeOffice2");
            sql =   "CREATE PROCEDURE changeOffice2(offi VARCHAR(20))" +
                    "    modifies sql data " +
                    "    UPDATE departments SET office = offi WHERE name = 'INFORMµTICA';";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL changeOffice2(?)");
            call.setString(1,"PROCEDURE2");
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sqlselect));
            System.out.println("\n\n");
            
            
            System.out.println("ChangeOffice3");
            sql =   "CREATE PROCEDURE changeOffice3(pattern VARCHAR(20), offi VARCHAR(20))" +
                    "    modifies sql data" +
                    "    UPDATE departments SET office = offi WHERE name LIKE pattern;";
            
            stt.executeUpdate(sql);
            call = con.prepareCall("CALL changeOffice3(?,?)");
            call.setString(1,"%O");
            call.setString(2,"PROCEDURE3");
            call.execute();
            JDBChelper.showResultSet(stt.executeQuery(sqlselect));
            System.out.println("\n\n");
            
            
            call.close();
            stt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
    
}
