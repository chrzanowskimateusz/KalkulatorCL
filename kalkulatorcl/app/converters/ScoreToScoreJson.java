package converters;

import json.ScoreJson;
import models.Score;
 
import javax.inject.Singleton;
import java.util.function.Function;

@Singleton
public class ScoreToScoreJson implements Function<Score, ScoreJson> {
    @Override
    public ScoreJson apply(Score score) {

        return new ScoreJson(
                score.id,
                score.value );
    }

}