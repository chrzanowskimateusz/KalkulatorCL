package controllers;
 
import converters.ScoreToScoreJson;
import json.ScoreJson;
import models.Score;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.stream.Collectors;
import play.mvc.Http;
 
@Singleton
public class ScoreController extends Controller {
 
 
    @Inject
    private ScoreToScoreJson scoreToScoreJson;
 
    public Result findById(long id) {
        return Optional.ofNullable(Score.findById(id)).map(scoreToScoreJson).map(json -> ok(Json.toJson(json))).orElse(notFound());
    }
 
    public Result save(Http.Request request) {
 
        ScoreJson scoreJson = Json.fromJson(request.body().asJson(),ScoreJson.class);
 
        Score score = new Score();
        score.value = scoreJson.value;
        score.save();
        return ok(Json.toJson(scoreToScoreJson.apply(score)));
 
    }
 
    public Result findAll() {
        return ok(Json.toJson(Score.findAll().stream().map(scoreToScoreJson).collect(Collectors.toList())));
    }
 
}