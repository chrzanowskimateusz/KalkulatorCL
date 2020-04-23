package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Entity
public class Score extends Model{
    @Id
    @GeneratedValue
    public long id;
    public float value;

    public static final Finder<Long,Score> FINDER = new Finder<>(Score.class);

    public static Score findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<Score> findAll() {
 
        return FINDER.query().findList();
    }
 
    public static List<Score> findByValue(float value) {
        return FINDER.query().where().eq("value",value).findList();
    }
}