package DataBaseConnection;
import java.sql.*;


public class DataBaseConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:..\\003.SQLite\\DB.db");
            
            String databaseproductname = connection.getMetaData().getDatabaseProductName();
            String drivername = connection.getMetaData().getDriverName();
            String driverversion = connection.getMetaData().getDriverVersion();
            String url = connection.getMetaData().getURL();
            String username = connection.getMetaData().getUserName();
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println(databaseproductname+"\n"+drivername+"\n"+driverversion+"\n"+url+"\n"+username);        
            
            ResultSet resul = dbmd.getTables(null, null, null, null); //all tables
            while (resul.next()) {
                String catalogo = resul.getString(1); // column 1: TABLE_CAT
                String esquema = resul.getString(2); // column 2: TABLE_SCHEM
                String tabla = resul.getString(3); // column 3: TABLE_NAME
                String tipo = resul.getString(4); // column 4: TABLE_TYPE
                
                System.out.println("Catalog: " + catalogo + " Schema: " + esquema + " Type: " + tipo + "; Table name: " + tabla);
                
                ResultSet columnas = dbmd.getColumns(null, null, tabla, null);
                
                while (columnas.next()){
                    String nombreCol = columnas.getString("COLUMN_NAME");
                    String tipoCol = columnas.getString("TYPE_NAME");
                    String tamCol = columnas.getString("COLUMN_SIZE");
                    String nula = columnas.getString("IS_NULLABLE");

                    System.out.println("Columna: " + nombreCol + " tipo: " + tipoCol + " tamaño: " + tamCol + " Admite null: " + nula);
                }

               
            }

            
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
    }
}
