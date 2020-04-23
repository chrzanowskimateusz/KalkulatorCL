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
    @GeneratedValue
    public long id;
    public String name;

    public static final Finder<Long,Subject> FINDER = new Finder<>(Subject.class);

    public static Subject findById(Long id) {
        return FINDER.ref(id);
    }
 
    public static List<Subject> findAll() {
 
        return FINDER.query().findList();
 
    }
 
    public static List<Subject> findByName(String name) {
        return FINDER.query().where().eq("name",name).findList();
    }
}