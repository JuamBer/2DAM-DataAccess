package backupfilesextension;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackupFilesExtension {

    private static final String DBNAME = "BackupFilesExtension";
    private static final String TABLE_DEPARTMENTS = "departments";
    private static final String TABLE_TEACHERS = "teachers";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        //Check if the database path exists. If so, delete it. This way we create the database and the tables each time we execute the program without problems. Note: File only removes a directory when it is empty, so all contained files and directories should be deleted first. This is a recursive problem.
        //Create the database in the directory .\database    
        deleteFilesDataBase(DBNAME, "lib\\database\\" + DBNAME);
        createDataBase(DBNAME);

        //Create departments table and fill it with the data contained in .\files\departments.txt. Print the number of modified rows.
        createTable(DBNAME, TABLE_DEPARTMENTS);
        fillTable(DBNAME, TABLE_DEPARTMENTS);
        //Show the contents of departments table using a SELECT query.
        showTable(DBNAME, TABLE_DEPARTMENTS);

        //Create teachers table and fill it with the data contained in .\files\teachers.txt. Print the number of modified rows.
        createTable(DBNAME, TABLE_TEACHERS);
        fillTable(DBNAME, TABLE_TEACHERS);
        //Show the contents of teachers table using a SELECT query.
        showTable(DBNAME, TABLE_TEACHERS);

    }

    public static void createDataBase(String dbname) {
        System.out.println("\n" + GREEN + "-----CREATE DATABSE-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname + ";create=true";
            Connection con = DriverManager.getConnection(url);
            con.close();
            System.out.println("DATABASE " + dbname + " CREATED");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFilesDataBase(String dbname, String url) {
        System.out.println(RED + "-----DELETE FILES DATA BASE-----");
        try {
            File fl = new File(url);
            if (fl.exists()) {
                if (fl.isFile()) {
                    fl.delete();
                }
                if (fl.isDirectory()) {
                    String[] listchilds = fl.list();

                    if (listchilds.length == 0) {
                        fl.delete();
                    } else {
                        for (int i = 0; i < listchilds.length; i++) {
                            System.out.println(listchilds[i]);
                            String newurl = url + "\\" + listchilds[i];
                            deleteFilesDataBase(dbname, newurl);
                        }
                        deleteFilesDataBase(dbname, url);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createTable(String dbname, String table) {
        System.out.println("\n" + GREEN + "-----CREATE TABLE (" + table + ")-----");
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
        System.out.println("\n" + BLUE + "-----FILL TABLE-----");
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
                            ps.setString(i, val);
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
        System.out.println("\n-----SHOW TABLE-----");
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

}
