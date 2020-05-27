package controllers;
 
import converters.MarkToMarkJson;
import json.MarkJson;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.stream.Collectors;
import play.mvc.Http;
 
@Singleton
public class MarkController extends Controller {
 
 
    @Inject
    private MarkToMarkJson markToMarkJson;
 
    public Result findById(long id) {
        return Optional.ofNullable(Mark.findById(id)).map(markToMarkJson).map(json -> ok(Json.toJson(json))).orElse(notFound());
    }
 
    public Result save(Http.Request request) {
 
        MarkJson markJson = Json.fromJson(request.body().asJson(),MarkJson.class);
 
        Mark mark = new Mark();
        User user = new User();
        Subject subject = new Subject();
        mark.subject = Subject.findById(markJson.subject_id);
        mark.user = User.findById(markJson.user_id);
        mark.value = markJson.value;
        mark.save();
        return ok(Json.toJson(markToMarkJson.apply(mark)));
 
    }
 
    public Result findAll() {
        return ok(Json.toJson(Mark.findAll().stream().map(markToMarkJson).collect(Collectors.toList())));
    }
 
}
