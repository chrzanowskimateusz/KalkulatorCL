package controllers;
 
import converters.UserToUserJson;
import json.UserJson;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.stream.Collectors;
import play.mvc.Http;
 
@Singleton
public class UserController extends Controller {
 
 
    @Inject
    private UserToUserJson userToUserJson;
 
    public Result findById(long id) {
        return Optional.ofNullable(User.findById(id)).map(userToUserJson).map(json -> ok(Json.toJson(json))).orElse(notFound());
    }
 
    public Result save(Http.Request request) {
 
        UserJson userJson = Json.fromJson(request.body().asJson(),UserJson.class);
 
        User user = new User();
        user.login = userJson.login;
        user.save();
        return ok(Json.toJson(userToUserJson.apply(user)));
 
    }
 
    public Result findAll() {
        return ok(Json.toJson(User.findAll().stream().map(userToUserJson).collect(Collectors.toList())));
    }
 
}