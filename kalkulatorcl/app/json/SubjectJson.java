package json;

public class SubjectJson {

    public long seq_id;
    public String id;
    public String name;
    public String teacher;
    public long weight;

    public SubjectJson(long seq_id, String id, String name, String teacher, long weight) {
        this.seq_id = seq_id;
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.weight = weight;
    }
 
    public SubjectJson() {
    }

}