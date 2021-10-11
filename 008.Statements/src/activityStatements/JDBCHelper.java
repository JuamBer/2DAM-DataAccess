package activityStatements;

import java.sql.*;
import java.util.ArrayList;


public class JDBCHelper {
    public static void showResultSet(ResultSet set){
        try {
            ResultSetMetaData meta = set.getMetaData();
            int n = meta.getColumnCount();
            ArrayList columnsnames = new ArrayList();
            
            for (int i = 1; i <= n; i++) {
                columnsnames.add(meta.getColumnName(n));
                System.out.print(columnsnames.get(i-1)+" | ");
            }
            System.out.println("");
            
            while(set.next()){
                for (int i = 1; i <= n; i++) {
                    System.out.print(set.getString(i)+" | ");
                }
                System.out.println("|");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    public static void addNewRow(ResultSet set){
        try {
            ResultSetMetaData meta = set.getMetaData();
            int n = meta.getColumnCount();
            ArrayList columnsnames = new ArrayList();
            
            for (int i = 1; i <= n; i++) {
                columnsnames.add(meta.getColumnName(n));
                System.out.print(columnsnames.get(i-1)+" | ");
            }
            System.out.println("");
            
            while(set.next()){
                for (int i = 1; i <= n; i++) {
                    System.out.print(set.getString(i)+" | ");
                }
                System.out.println("|");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
