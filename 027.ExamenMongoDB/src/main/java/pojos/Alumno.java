package pojos;

import java.util.List;
import org.bson.types.ObjectId;


public class Alumno {
    private ObjectId id;
    private String nombre;
    private String apellido;
    private String grupo;
    private List<String> matricula;
    private FCT fct;
    
    
    public Alumno(){
        this.id = new ObjectId();
    }
    
    public Alumno(String nombre, String apellido, String grupo, List<String> matricula, FCT fct) {
        this.id = new ObjectId();
        this.nombre = nombre;
        this.apellido = apellido;
        this.grupo = grupo;
        this.matricula = matricula;
        this.fct = fct;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<String> getMatricula() {
        return matricula;
    }

    public void setMatricula(List<String> matricula) {
        this.matricula = matricula;
    }

    public FCT getFct() {
        return fct;
    }

    public void setFct(FCT fct) {
        this.fct = fct;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", grupo=" + grupo + ", matricula=" + matricula + ", fct=" + fct + '}';
    }
    
    
}
