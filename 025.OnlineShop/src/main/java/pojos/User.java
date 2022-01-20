package pojos;

import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    private String name;
    private String email;
    private Address address;
    
    public User(){
        this.id = new ObjectId();
    }
    
    public User(String name, String email, Address address) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + '}';
    }
}
