package backupfilesextension;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;


public class BackupFilesExtension {

    private static final String DBNAME = "BackupFilesExtension";
    private static final String TABLE_DEPARTMENTS = "departments";
    private static final String TABLE_TEACHERS = "teachers";

    public static void main(String[] args) {
        if(checkDataBaseExists(DBNAME)){ 
            if(checkTableExists(DBNAME,TABLE_DEPARTMENTS)){
                truncateTable(DBNAME, TABLE_DEPARTMENTS);
            }else{
                createTable(DBNAME, TABLE_DEPARTMENTS);
            }
            
            if(checkTableExists(DBNAME,TABLE_TEACHERS)){
                truncateTable(DBNAME, TABLE_TEACHERS);
            }else{
                createTable(DBNAME, TABLE_TEACHERS);
            }
        }else{
            createDataBase(DBNAME);
            createTable(DBNAME, TABLE_DEPARTMENTS);
            createTable(DBNAME, TABLE_TEACHERS);
        }
     
        fillTable(DBNAME, TABLE_DEPARTMENTS);
        showTable(DBNAME, TABLE_DEPARTMENTS);

        fillTable(DBNAME, TABLE_TEACHERS);
        showTable(DBNAME, TABLE_TEACHERS);
    }

    public static boolean checkDataBaseExists(String dbname) {
        System.out.println("\n-----CHECK DATA BASE EXISTS-----");
        Connection con = null;
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;

            con = DriverManager.getConnection(url); //Open a connection

            con.getMetaData().getCatalogs();
            con.close();

            System.out.println("DATABASE " + dbname + " EXISTS");
            return true;

        } catch (SQLException e) {
            System.out.println("DATABASE " + dbname + " NOT EXISTS");
        }

        return false;
    }
    public static boolean checkTableExists(String dbname,String table) {
        System.out.println("\n-----CHECK TABLE ("+table+") EXISTS-----");
        Connection con = null;
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;

            con = DriverManager.getConnection(url); //Open a connection
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet catalog = dbm.getColumns(null,null,table,null);      
            con.close();
            System.out.println("TABLE " + table + " EXISTS");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    public static void truncateTable(String dbname,String table) {
        System.out.println("\n-----TRUNCATE TABLE ("+table+")-----");
        Connection con = null;
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;

            con = DriverManager.getConnection(url); //Open a connection
            Statement st = con.createStatement();   
            String sql = "TRUNCATE TABLE "+table;
            System.out.println("SQL: "+sql);
            st.executeUpdate(sql);
            con.close();

            System.out.println("TABLE "+table+" TRUNCATED");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createDataBase(String dbname) {
        System.out.println("\n-----CREATE DATABASE ("+dbname+")-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname + ";create=true";
            Connection con = DriverManager.getConnection(url);
            con.close();
            System.out.println("DATABASE " + dbname + " CREATED");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createTable(String dbname, String table) {
        System.out.println("\n-----CREATE TABLE (" + table + ")-----");
        try {
            File fl = new File("lib\\files\\extension\\" + table + ".txt");
            FileReader fr = new FileReader(fl);
            BufferedReader br = new BufferedReader(fr);

            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();

            String line;
            String[] columnsnames = null;
            String[] columnstypes = null;
            String[] columnsfun = null;

            if ((line = br.readLine()) != null) {
                columnsnames = line.split(",");
            }
            if ((line = br.readLine()) != null) {
                columnstypes = line.split(",");
            }
            if ((line = br.readLine()) != null) {
                columnsfun = line.split(",");
            }

            br.close();

            String sql = "CREATE TABLE " + table + " (";
            for (int i = 0; i < 3; i++) {

                if (columnsfun[i].equals("N")) {
                    columnsfun[i] = "";
                }

                if (i == 2) {
                    sql += columnsnames[i] + " " + columnstypes[i] + " " + columnsfun[i];
                } else {
                    sql += columnsnames[i] + " " + columnstypes[i] + " " + columnsfun[i] + ",";
                }

            }
            sql += ")";

            System.out.println("SQL: " + sql);
            int rows = st.executeUpdate(sql);
            System.out.println(rows + " Affected");

            st.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void fillTable(String dbname, String table) {
        System.out.println("\n-----FILL TABLE ("+table+")-----");
        try {
            File fl = new File("lib\\files\\extension\\" + table + ".txt");
            FileReader fr = new FileReader(fl);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            PreparedStatement ps;
            ResultSet set = st.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData meta = set.getMetaData();
            int numcols = meta.getColumnCount();
            ArrayList<Integer> columnstypes = new ArrayList();

            for (int i = 1; i <= numcols; i++) {
                columnstypes.add(meta.getColumnType(i));
            }

            String sql = "INSERT INTO " + table + " VALUES (";

            for (int i = 0; i < numcols; i++) {
                if (i == numcols - 1) {
                    sql += "?)";
                } else {
                    sql += "?,";
                }
            }
            ps = con.prepareStatement(sql);

            br.readLine();
            br.readLine();
            br.readLine();

            while ((line = br.readLine()) != null) {

                //System.out.println("\nline: " + line);
                String[] data = line.split(",");

                //System.out.println("SQL: " + sql);
                for (int i = 1; i <= numcols; i++) {
                    if (data[i - 1].equals("")) {
                        data[i - 1] = null;
                    }
                    String val = data[i - 1];

                    //System.out.println("DATA "+i+":"+val);
                    //System.out.println("ColumType: " + columnstypes.get(i - 1));
                    //System.out.println("Val: " + val);
                    switch (columnstypes.get(i - 1)) {
                        case 16: //Boolean
                            if (val.equals("true")) {
                                ps.setBoolean(i, true);
                            } else {
                                ps.setBoolean(i, false);
                            }
                            break;
                        case 4: //Integer
                            ps.setInt(i, Integer.parseInt(val));
                            break;
                        case 12: //String
                            ps.setString(i, val);
                            break;
                        case 91: //Date
                            System.out.println("date");
                            ps.setDate(i, new Date(Long.parseLong(val)));
                            break;
                        default:
                            ps.setString(i, null);
                            break;
                    }

                }
                int rows = ps.executeUpdate();
                System.out.println(rows + " affected");
            }
            con.close();
            fr.close();
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | SQLException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void showTable(String dbname, String table) {
        System.out.println("\n-----SHOW TABLE ("+table+")-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM " + table;
            System.out.println("SQL: " + sql);
            ResultSet set = st.executeQuery(sql);
            JDBChelper.showResultSet(set);
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public static void deleteFilesDataBase(String dbname, String url) {
//        System.out.println("-----DELETE FILES DATA BASE-----");
//        try {
//            File fl = new File(url);
//            if (fl.exists()) {
//                if (fl.isFile()) {
//                    fl.delete();
//                }
//                if (fl.isDirectory()) {
//                    String[] listchilds = fl.list();
//
//                    if (listchilds.length == 0) {
//                        fl.delete();
//                    } else {
//                        for (int i = 0; i < listchilds.length; i++) {
//                            System.out.println(listchilds[i]);
//                            String newurl = url + "\\" + listchilds[i];
//                            deleteFilesDataBase(dbname, newurl);
//                        }
//                        deleteFilesDataBase(dbname, url);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
}


