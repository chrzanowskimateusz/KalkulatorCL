package json;

public class SubjectJson {

    public long seq_id;
    public String id;
    public String name;
    public long weight;

    public SubjectJson(long seq_id, String id, String name, long weight) {
        this.seq_id = seq_id;
        this.id = id;
        this.name = name;
        this.weight = weight;
    }
 
    public SubjectJson() {
    }

}