package statements;
import java.sql.*;


public class Statements {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib\\db\\DB");
            
            DatabaseMetaData dbmd = connection.getMetaData();
            
            ResultSet resul = dbmd.getTables(null, null, null, null); //all tables
            Statement st = connection.createStatement();
            resul=st.executeQuery("SELECT * FROM teachers");
            JDBChelper.showResultSet(resul);
            
            resul.close();
            connection.close();
//            while (resul.next()) {
//                String catalogo = resul.getString(1); // column 1: TABLE_CAT
//                String esquema = resul.getString(2); // column 2: TABLE_SCHEM
//                String tabla = resul.getString(3); // column 3: TABLE_NAME
//                String tipo = resul.getString(4); // column 4: TABLE_TYPE
//                
//                System.out.println("Catalog: " + catalogo + " Schema: " + esquema + " Type: " + tipo + "; Table name: " + tabla);
//                
//                ResultSet columnas = dbmd.getColumns(null, null, tabla, null);
//                
//                while (columnas.next()){
//                    String nombreCol = columnas.getString("COLUMN_NAME");
//                    String tipoCol = columnas.getString("TYPE_NAME");
//                    String tamCol = columnas.getString("COLUMN_SIZE");
//                    String nula = columnas.getString("IS_NULLABLE");
//
//                    System.out.println("Columna: " + nombreCol + " tipo: " + tipoCol + " tamaño: " + tamCol + " Admite null: " + nula);
//                }
//
//               
//            }

            
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
    }
}
