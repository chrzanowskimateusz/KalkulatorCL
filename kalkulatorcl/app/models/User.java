package models; 

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Entity
public class User extends Model{
    @Id
    @GeneratedValue
    public long id;
    public String login;

    public static final Finder<Long,User> FINDER = new Finder<>(User.class);

    public static User findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<User> findAll() {
 
        return FINDER.query().findList();
    }


}