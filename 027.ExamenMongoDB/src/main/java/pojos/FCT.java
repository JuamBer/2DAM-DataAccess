package pojos;

import org.bson.types.ObjectId;

public class FCT {
    private String empresa;
    private Integer horas;
    
    public FCT(){
    }
    
    public FCT(String empresa, Integer horas) {
        this.empresa = empresa;
        this.horas = horas;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "FCT{" + "empresa=" + empresa + ", horas=" + horas + '}';
    }
    
    
}
