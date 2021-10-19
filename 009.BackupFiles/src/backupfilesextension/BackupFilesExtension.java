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
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {

        //Check if the database path exists. If so, delete it. This way we create the database and the tables each time we execute the program without problems. Note: File only removes a directory when it is empty, so all contained files and directories should be deleted first. This is a recursive problem.
        //Create the database in the directory .\database    
        deleteFilesDataBase(DBNAME, "lib\\database\\" + DBNAME);
        createDataBase(DBNAME);

        //Create departments table and fill it with the data contained in .\files\departments.txt. Print the number of modified rows.
        createDepartments(DBNAME);
        fillTable(DBNAME, TABLE_DEPARTMENTS);
        //Show the contents of departments table using a SELECT query.
        showTable(DBNAME, TABLE_DEPARTMENTS);

        //Create teachers table and fill it with the data contained in .\files\teachers.txt. Print the number of modified rows.
        createTeachers(DBNAME);
        fillTable(DBNAME, TABLE_TEACHERS);
        //Show the contents of teachers table using a SELECT query.
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

    public static void createDataBase(String dbname) {
        System.out.println("\n" + GREEN + "-----CREATE DATABSE-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname + ";create=true";
            Connection con = DriverManager.getConnection(url);
            con.close();
            System.out.println(GREEN + "DATABASE " + GREEN + dbname + GREEN + " CREATED");
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
                            System.out.println(RED + listchilds[i]);
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
    public static void createTable(String dbname,String table) {
        System.out.println("\n-----CREATE TABLE ("+table+")-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM " + table;
            System.out.println("SQL: " + sql);
            ResultSet set = st.executeQuery(sql);
            
            File fl = new File("lib\\extension\\files\\" + table + ".txt");
            FileReader fr = new FileReader(fl);
            BufferedReader br = new BufferedReader(fr);
            String line;
            PreparedStatement ps;
            
            
            ArrayList<Integer> columnstypes = new ArrayList();

            while ((line = br.readLine()) != null) {}
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BackupFilesExtension.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BackupFilesExtension.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void createDepartments(String dbname) {
        System.out.println("\n" + GREEN + "-----CREATE DEPARTMENTS-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            String sql = "CREATE TABLE departments ("
                    + "dept_num INT PRIMARY KEY,"
                    + "name VARCHAR(20),"
                    + "office VARCHAR(20)"
                    + ")";
            System.out.println(GREEN + "SQL: " + sql);
            int rows = st.executeUpdate(sql);
            System.out.println(GREEN + rows + GREEN + " Affected");
            st.close();
            con.close();
            System.out.println(GREEN + "TABLE departments FROM " + dbname + " DATABASE CREATED");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createTeachers(String dbname) {
        System.out.println("\n" + GREEN + "-----CREATE TEACHERS-----");
        try {
            String url = "jdbc:derby:lib\\database\\" + dbname;
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            String sql = "CREATE TABLE Teachers ("
                    + "    id INT PRIMARY KEY,"
                    + "    name VARCHAR(15),"
                    + "    surname VARCHAR(40),"
                    + "    email VARCHAR(50),"
                    + "    start_date DATE,"
                    + "    dept_num INT,"
                    + "    FOREIGN KEY (dept_num) REFERENCES departments (dept_num) "
                    + ")";
            System.out.println(GREEN + "SQL: " + sql);
            int rows = st.executeUpdate(sql);
            System.out.println(GREEN + rows + GREEN + " Affected");
            st.close();
            con.close();
            System.out.println(GREEN + "TABLE teachers FROM " + dbname + " DATABASE CREATED");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void fillTable(String dbname, String table) {
        System.out.println("\n" + BLUE + "-----FILL TABLE-----");
        try {
            File fl = new File("lib\\files\\" + table + ".txt");
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
                System.out.println(BLUE + rows + BLUE + " affected");
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

//    public static void deleteDataBase(String dbname) {
//        System.out.println("\n-----DELETE DATA BASE-----");
//        try {
//            String url = "jdbc:derby:lib\\database\\" + dbname;
//            Connection con;
//            con = DriverManager.getConnection(url); //Open a connection
//
//            Statement st = con.createStatement();
//            String sql = "DROP DATABASE "+dbname;
//            System.out.println("SQL: " + sql);
//            st.execute(sql);
//            con.close();
//
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }

}
