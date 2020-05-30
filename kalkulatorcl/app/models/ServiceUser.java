package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class ServiceUser extends Model{
    @Id
    @GeneratedValue
    public long id;
    public String login;
    public String password;

    @OneToOne
    public User user;

    public static final Finder<Long,ServiceUser> FINDER = new Finder<>(ServiceUser.class);

    public static ServiceUser findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<ServiceUser> findAll() {
 
        return FINDER.query().findList();
    } 

    public static ServiceUser findByServiceLogin(String login) {
        return FINDER.query().where().eq("login", login).findOne();
    }

    public static ServiceUser findByUserLogin(String login) {
        return FINDER.query().where().eq("user.login", login).findOne();
    }

    public static Boolean exists(String login) {
        return FINDER.query().where().eq("login", login).exists();
    }
}
