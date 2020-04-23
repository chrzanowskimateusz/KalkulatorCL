package converters;
 
import json.UserJson;
import models.User;
 
import javax.inject.Singleton;
import java.util.function.Function;
 
@Singleton
public class UserToUserJson implements Function<User, UserJson> {
    @Override
    public UserJson apply(User user) {
 
        return new UserJson(
                user.id,
                user.login );
    }
}