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
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import models.*;

public class HomeController extends Controller {

    private final FormFactory formFactory;

    private Float avgMark(String subjectId)
    {
        List<Mark> marks = Mark.findAll();
        Float avg = 0.0f;
        int count = 0;
        for (Mark mark : marks)
        {
            if (mark.subject.id == subjectId)
            {
                avg += mark.value;
                ++count;
            }
        }
        if (count > 0)
            avg /= (float)count;
        return avg;
    }

    private List<Float> avgMarks()
    {
        List<Float> avg = new ArrayList<Float>();
        List<Subject> subjects = Subject.findAll();
        for (Subject subject : subjects)
            avg.add(avgMark(subject.id));
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

    @Inject
    public HomeController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    public Result index() {
        return ok(views.html.main.render());
    }

    public Result register() {
        return ok(views.html.register.render());
    }

    public Result calculator() {
        return ok(views.html.calculator.render());
    }

   

    private AvgAndListofMarks parseHtml(String script, boolean saveToDatabase, String userLogin)
    {
        Document doc = Jsoup.parse(script);
        String output = "";
        List<String> id = new ArrayList<String>();
        List<String> name = new ArrayList<String>();
        List<Integer> weight = new ArrayList<Integer>();
        List<Float> value = new ArrayList<Float>();
        for (Element table : doc.select("table.KOLOROWA"))
        {
            for (Element row : table.select("tr"))
            {
                Elements tds = row.select("td tr");
                int i = 0;
                for (Element element : tds)
                {
                    if(i == 0)
                    {
                        i++;
                        continue;
                    }
                        // output += element.text();
                        // output += "\n";
                        output = element.text();
                        String[] array = output.split(" ");
                        id.add(array[0]);
                        String fullName = "";
                        for(int a = 1; a < array.length; a++)
                        {
                            if(isNumeric(array[a]))
                                break;
                            fullName += array[a] + " ";
                            
                        }
                        name.add(fullName);
                    if (isNumeric(array[array.length-2]))
                    {
                        weight.add(Integer.parseInt(array[array.length-2]));
                        value.add(Float.parseFloat(array[array.length-1]));
                    }
                    else
                    {
                        weight.add(Integer.parseInt(array[array.length-1]));
                        value.add(0.0f);
                    }
                    i++;
                }
            }
        }
        float avg = 0.0f;
        int ects = 0;
        List<Mark> marks = new ArrayList<Mark>();
        for (int i = 0; i < id.size(); ++i)
        {
            if(value.get(i) == 0.0)
                continue;
            avg += value.get(i) * (float)weight.get(i);
            ects += weight.get(i);



            Subject subject = new Subject();
            subject.id= id.get(i);
            subject.name = name.get(i);
            subject.weight = weight.get(i);


            if(saveToDatabase == true)
            {
                if(Subject.findById(id.get(i)) == null)
                {
                    subject.save();
                }

                if(Mark.findByUserLoginAndSubjectId(userLogin, id.get(i)) != null){
                    
                    Mark mark = Mark.findByUserLoginAndSubjectId(userLogin, id.get(i));
                    mark.user = User.findByLogin(userLogin);
                    mark.subject = Subject.findById(id.get(i));
                    mark.value = value.get(i);
                    mark.update();
                }
                else{

                    Mark mark = new Mark();
                    mark.user = User.findByLogin(userLogin);
                    mark.subject = Subject.findById(id.get(i));
                    mark.value = value.get(i);
                    mark.save();
                }
            }
            Mark mark = new Mark();
            mark.user = User.findByLogin(userLogin);
            mark.subject = Subject.findById(id.get(i));
            mark.value = value.get(i);
            marks.add(mark);

        }

        AvgAndListofMarks result = new AvgAndListofMarks(String.valueOf(avg/(float)ects), marks);
        

        return result;
    }

    public Result loginSubmit(Http.Request request){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest(request);
        String userLogin = dynamicForm.get("login");
        String userPassword = dynamicForm.get("password");
        String response;
        WebClient webClient = new WebClient();
         // save user to database
        if(!User.exists(userLogin))
        {   
            User user = new User();
            user.login = userLogin;
            user.save();
        }
        try{
            HtmlPage page = (HtmlPage) webClient
                .getPage("https://edukacja.pwr.wroc.pl/EdukacjaWeb/studia.do");
            HtmlForm form = page.getForms().get(0);
            form.getInputByName("login").setValueAttribute(userLogin); 
            form.getInputByName("password").setValueAttribute(userPassword); 
            page = form.getInputByValue(" ").click();
            page = page.getAnchorByText("Indeks").click();
            WebResponse wynik = page.getWebResponse();
            response = wynik.getContentAsString();
           
        }catch(IOException ioe){
            response = "notok";
        }

        AvgAndListofMarks result = parseHtml(response, true, userLogin);

       
        String login = userLogin;

        return ok(views.html.view.render(login, result.avg, result.marks));
    }

    public Result calculatorSubmit(Http.Request request){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest(request);
        String script = dynamicForm.get("script");
        AvgAndListofMarks result = parseHtml(script, false, "");
        String login = "";
        return ok(views.html.view.render(login, result.avg, result.marks));
    }




    class AvgAndListofMarks{
        String avg;
        List<Mark> marks;
        AvgAndListofMarks(String avg, List<Mark> marks)
        {
            this.avg = avg;
            this.marks = marks;
        }
    }
}
