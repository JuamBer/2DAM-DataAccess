package statements;
import java.sql.*;


public class JDBChelper {
    public static void showResultSet(ResultSet res){
        
        try {
            ResultSetMetaData meta = res.getMetaData();
            int numcolumns = meta.getColumnCount();
            
            String[] columnames = new String[numcolumns];
            
            for (int i = 1; i <= numcolumns; i++) {
                columnames[i-1] = meta.getColumnName(i);
                System.out.print(columnames[i-1]+ " | ");
            }
            System.out.println("");
            while(res.next()){
                for (int i = 0; i < numcolumns; i++) {
                    System.out.print(res.getString(columnames[i])+ " | ");
                }
                System.out.println("");
                
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        

    }
}
