package converters;

import json.MarkJson;
import models.Mark;
 
import javax.inject.Singleton;
import java.util.function.Function;

@Singleton
public class MarkToMarkJson implements Function<Mark, MarkJson> {
    @Override
    public MarkJson apply(Mark mark) {

        return new MarkJson(
                mark.id,
                mark.user.id,
                mark.score.id,
                mark.subject.seq_id);
    }

}
