package onlineshop;

import backend.DataAPI;
import java.util.Arrays;
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;
import pojos_exceptions.IncorrectArticleException;

public class OnlineShop {
    public static void main(String[] args) throws IncorrectArticleException {
        DataAPI api = new DataAPI();
        
        //Method Init
        api.init();
        
        //Method Insert Article
        Address addressUser1 = new Address(
                "Plaza Musico Ramon Ibars",
                5,
                "Valencia",
                "España"
        );
        Address addressUser2 = new Address(
                "Travessera Cuellar	",
                328,
                "Aragón Alta",
                "España"
        );
        
        User user1 = new User(
                "Juan Sáez García",
                "contacto@juamber.com",
                addressUser1
        );
        User user2 = new User(
                "Ivan Soriano",
                "contacto@ivanzon.com",
                addressUser2
        );
        Comment comment1 = new Comment(
                4,
                "Camera result is really good and overall a nice all rounder phone. Loving it!",
                user1.getId()
        );
        Comment comment2 = new Comment(
                2,
                "Demasiado caro",
                user2.getId()
        );
        
        Article article = new Article(
                "Google Pixel 6 Pro",
                1.180,
                Arrays.asList("Smatphone","Tech","Google")
        );
        //api.insertUser(user1);
        //api.insertUser(user2);
        
        //api.insertArticle(article);
        
        
        
        api.close();
    }
}
