
package Schedule;

import java.io.Serializable;


public class Contact implements Serializable{
    private String name;
    private String surname;
    private String tel;

    public Contact(String name, String surname, String tel) {
        this.name = name;
        this.surname = surname;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Contact{" + "name=" + name + ", surname=" + surname + ", tel=" + tel + '}';
    }

    

   
}
