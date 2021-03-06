package pojos;
// Generated 7 feb. 2022 8:47:41 by Hibernate Tools 4.3.1



/**
 * Alumnos generated by hbm2java
 */
public class Alumnos  implements java.io.Serializable {


     private int nia;
     private Municipios municipios;
     private String nombre;
     private String apellidos;

    public Alumnos() {
    }

	
    public Alumnos(int nia) {
        this.nia = nia;
    }
    public Alumnos(int nia, Municipios municipios, String nombre, String apellidos) {
       this.nia = nia;
       this.municipios = municipios;
       this.nombre = nombre;
       this.apellidos = apellidos;
    }
   
    public int getNia() {
        return this.nia;
    }
    
    public void setNia(int nia) {
        this.nia = nia;
    }
    public Municipios getMunicipios() {
        return this.municipios;
    }
    
    public void setMunicipios(Municipios municipios) {
        this.municipios = municipios;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Alumnos{" + "nia=" + nia + ", municipios=" + municipios + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
    }




}


