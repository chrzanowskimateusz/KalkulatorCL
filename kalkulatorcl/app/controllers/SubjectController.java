package controllers;
 
import converters.SubjectToSubjectJson;
import json.SubjectJson;
import models.Subject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.stream.Collectors;
import play.mvc.Http;
 
@Singleton
public class SubjectController extends Controller {
 
 
    @Inject
    private SubjectToSubjectJson subjectToSubjectJson;
 
    public Result findById(long id) {
        return Optional.ofNullable(Subject.findById(id)).map(subjectToSubjectJson).map(json -> ok(Json.toJson(json))).orElse(notFound());
    }
 
    public Result save(Http.Request request) {
 
        SubjectJson subjectJson = Json.fromJson(request.body().asJson(),SubjectJson.class);
 
        Subject subject = new Subject();
        subject.name= subjectJson.name;
        subject.save();
        return ok(Json.toJson(subjectToSubjectJson.apply(subject)));
 
    }
 
    public Result findAll() {
        return ok(Json.toJson(Subject.findAll().stream().map(subjectToSubjectJson).collect(Collectors.toList())));
    }
 
}