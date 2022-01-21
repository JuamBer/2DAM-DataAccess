package pojos;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Article {
    private ObjectId id;
    private String name;
    private Double price;
    private List<String> categories;
    private List<Comment> comments;
    
    public Article(){
        this.id = new ObjectId();
    }
    
    public Article(String name, Double price, List<String> categories) {
        this.id = new ObjectId();
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.comments = null;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Articles{" + "id=" + id + ", name=" + name + ", price=" + price + ", categories=" + categories + ", comments=" + comments + '}';
    }
}
