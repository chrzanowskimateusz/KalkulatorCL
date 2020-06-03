package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Mark extends Model{
    @Id
    @GeneratedValue
    public long id;

    @ManyToOne
    public User user;

    @ManyToOne
    public Subject subject;

    public float value;

    public static final Finder<Long,Mark> FINDER = new Finder<>(Mark.class);

    public static Mark findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<Mark> findAll() {
 
        return FINDER.query().findList();
    } 

    public static int deleteAllByUserLogin(String userLogin) {
 
        return FINDER.query().where().eq("user.login", userLogin).delete();
    } 

    public static Mark findByUserLogin(String userLogin) {
        return FINDER.query().where().eq("user.login", userLogin).findOne();
    }

    public static Boolean exists(String userLogin) {
        return FINDER.query().where().eq("user.login", userLogin).exists();
    }
    public static Mark findByUserLoginAndSubjectId(String userLogin, String subjectId)
    {
        return FINDER.query().where().eq("user.login", userLogin).eq("subject.id", subjectId).findOne();
    }
}
