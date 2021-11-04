
import java.util.Date; // por la fecha de alta que necesitaremos
import java.sql.*;

public class Actividad10 {

    public static void main(String[] args) {
        try {
            //
            Class.forName("com.mysql.jdbc.Driver");
            //
            Connection miconexion
                    = DriverManager.getConnection("jdbc.mysql://localhost/ejemplo", "ejemplo",
                            "ejemplo");
            //
            String emp_no = args[0];
            String apellido = args[1];
            String oficio = args[2];
            String dir = args[3];
            String salario = args[4];
            String comision = args[5];
            String dept_no = args[6];
            Date fecha_alt = new Date();
            //
            Boolean verdad = false;
            //
            //
            Statement sentencia = miconexion.createStatement();
            //
            ResultSet rs1 = sentencia.executeQuery(
            "SELECT dept_no FROM
empleados WHERE dept_no = " + dept_no +"
            ");
 Boolean existeDepartamento = rs1.next();
            //
            ResultSet rs2 = sentencia.executeQuery(
            "SELECT emp_no FROM empleados
WHERE emp_no = " + emp_no+ "
            ");
 Boolean existeEmpleado = rs2.next();
            //
            //
            int valor = Integer.parseInt(args[4]);
            if (valor <= 0) {
                verdad = true;
            } else {
                verdad = false;
            }
            //
            ResultSet rs3 = sentencia.executeQuery(
            "SELECT emp_no FROM empleados
WHERE emp_no = " + dir+"
            ");
 Boolean existeDir = rs3.next();
            if (existeDepartamento == false || existeEmpleado == true
                    || existeDir == false || verdad == true) {
                System.out.println("No se va a introducir el empleado ,hay
Página 1
U2_Corrige_Codigo error
                !");
 if (existeDepartamento == false) {
                    System.out.println("El departamento no existe");
                }
                if (existeEmpleado == true) {
                    System.out.println("El empleado ya existe!!");
                }
                if (existeDir == false) {
                    System.out.println("El director no existe como empleado!");
                }
                if (verdad == true) {
                    System.out.println("No le pagas a tu empleado???");
                }
            } else {
                //
                String sql = "INSERT INTO empleados VALUES(" + emp_no + ",'"
                        + apellido + "','" + oficio + "'," + dir + "," + salario + "," + comision + ","
                        + dept_no + "," + fecha_alt + ")";
                sentencia.executeQuery(sql);
                System.out.println(sql);
            }
            sentencia.close(); //
            miconexion.close(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException es) {
            es.printStackTrace();
        }
    }
}
