package converters;

import json.SubjectJson;
import models.Subject;
 
import javax.inject.Singleton;
import java.util.function.Function;

@Singleton
public class SubjectToSubjectJson implements Function<Subject, SubjectJson> {
    @Override
    public SubjectJson apply(Subject subject) {

        return new SubjectJson(
                subject.seq_id,
                subject.id,
                subject.name,
                subject.teacher, 
                subject.weight );
    }

}
