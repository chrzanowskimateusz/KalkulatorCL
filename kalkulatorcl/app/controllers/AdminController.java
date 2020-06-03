package controllers;

import play.data.*;
import play.mvc.*;
import javax.inject.Inject;
import play.data.FormFactory;
import play.mvc.Http;
import java.util.Map;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import models.*;

public class AdminController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public AdminController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result loginSite() {
        return ok(views.html.login.render());
    }
    
    public Result login(Http.Request request) {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest(request);
        String userLogin = dynamicForm.get("login");
        String userPassword = dynamicForm.get("password");
        ServiceUser user = ServiceUser.findByServiceLogin(userLogin);
        if(user == null)
        {   
            return ok(views.html.confirm.render("Podany użytkownik nie istnieje!", "register.html"));
        }
        if(!user.password.equals(userPassword))
        {
            return ok(views.html.confirm.render("Błędne hasło!", "register.html"));
        }
        return redirect("/panel")
            .addingToSession(request, "connected", user.login);
    }

    public Result panel(Http.Request request){
        return request
            .session()
            .get("connected")
            .map(user -> ok(views.html.panel.render(String.valueOf(avgAll()), Mark.findAll(), User.findAll(), avgMarks())))
            .orElseGet(() -> unauthorized("Unauthorized access."));
    }

    private Float avgAll()
    {
        List<Mark> marks = Mark.findAll();
        Float avg = 0.0f;
        int count = 0;
        for (Mark mark : marks)
        {
            if(mark.value == 0.0f)
                continue;
            avg += mark.value;
            ++count;
        }
        if (count > 0)
            avg /= (float)count;
        return avg;
    }
    
    private Float avgMark(String subjectId)
    {
        List<Mark> marks = Mark.findAll();
        Float avg = 0.0f;
        int count = 0;
        for (Mark mark : marks)
        {
            if (mark.subject.id == subjectId)
            {
                if(mark.value == 0.0f)
                    continue;
                avg += mark.value;
                ++count;
            }
        }
        if (count > 0)
            avg /= (float)count;
        return avg;
    }

    private List<String> avgMarks()
    {
        List<String> avg = new ArrayList<String>();
        List<Subject> subjects = Subject.findAll();
        for (Subject subject : subjects)
            avg.add(String.valueOf(avgMark(subject.id)));
        return avg;
    }

    private Float userAvgMark(String userLogin)
    {
        List<Mark> marks = Mark.findAll();
        Float avg = 0.0f;
        int count = 0;
        for (Mark mark : marks)
        {
            if (mark.user.login == userLogin)
            {
                avg += mark.value;
                ++count;
            }
        }
        if (count > 0)
            avg /= (float)count;
        return avg;
    }

    private int markCount(Float value, String subjectId)
    {
        List<Mark> marks = Mark.findAll();
        int count = 0;
        for (Mark mark : marks)
            if (mark.subject.id == subjectId && Math.abs(value - mark.value) < 0.0001f)
                ++count;
        return count;
    }

    private List<Integer> marksPerSubjectCount(String subjectId)
    {
        List<Integer> count = new ArrayList<Integer>();
        count.add(markCount(2.0f, subjectId));
        count.add(markCount(3.0f, subjectId));
        count.add(markCount(3.5f, subjectId));
        count.add(markCount(4.0f, subjectId));
        count.add(markCount(4.5f, subjectId));
        count.add(markCount(5.0f, subjectId));
        count.add(markCount(5.5f, subjectId));
        return count;
    }

    private int userMarkCount(float value, String userLogin)
    {
        List<Mark> marks = Mark.findAll();
        int count = 0;
        for (Mark mark : marks)
            if (mark.user.login == userLogin && Math.abs(value - mark.value) < 0.0001f)
                ++count;
        return count;
    }

    private List<Integer> userMarksCount(String userLogin)
    {
        List<Integer> count = new ArrayList<Integer>();
        count.add(userMarkCount(2.0f, userLogin));
        count.add(userMarkCount(3.0f, userLogin));
        count.add(userMarkCount(3.5f, userLogin));
        count.add(userMarkCount(4.0f, userLogin));
        count.add(userMarkCount(4.5f, userLogin));
        count.add(userMarkCount(5.0f, userLogin));
        count.add(userMarkCount(5.5f, userLogin));
        return count;
    }
}