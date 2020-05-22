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

    @ManyToOne
    public Score score;

    public static final Finder<Long,Mark> FINDER = new Finder<>(Mark.class);

    public static Mark findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<Mark> findAll() {
 
        return FINDER.query().findList();
    }
 
    public static List<Mark> findByWeight(float weight) {
        return FINDER.query().where().eq("weight",weight).findList();
    }

   

}
