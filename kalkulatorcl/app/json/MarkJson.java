package json;

public class MarkJson {

    public long id;
    public long user_id;
    public String subject_id;
    public float value;

    public MarkJson(long id, long user_id, String subject_id, float value) {
        this.id = id;
        this.user_id = user_id;
        this.subject_id = subject_id;
        this.value = value;
    }
 
    public MarkJson() {
    }
}
