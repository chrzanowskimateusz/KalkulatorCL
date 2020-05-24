package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Subject extends Model{
    @Id
    public String id;
    public String name;
    public long weight;

    public static final Finder<Long,Subject> FINDER = new Finder<>(Subject.class);

    public static List<Subject> findAll() {
        return FINDER.query().findList();
    }
 
    public static List<Subject> findByName(String name) {
        return FINDER.query().where().eq("name",name).findList();
    }

     public static Subject findById(String code) {
        return FINDER.query().where().eq("id", code).findOne();
    }

     public static Boolean exists(String id) {
        return FINDER.query().where().eq("id", id).exists();
    }
}
