package json;

public class MarkJson {

    public long id;
    public long weight;
    public long user_id;
    public long score_id;
    public long subject_id;

    public MarkJson(long id, long weight, long user_id, long score_id, long subject_id) {
        this.id = id;
        this.weight = weight;
        this.user_id = user_id;
        this.score_id = score_id;
        this.subject_id = subject_id;
    }
 
    public MarkJson() {
    }
}