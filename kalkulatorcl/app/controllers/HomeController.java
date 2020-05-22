package controllers;

import play.data.*;
import play.mvc.*;
import javax.inject.Inject;
import play.data.FormFactory;
import play.mvc.Http;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public HomeController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.main.render());
    }

    public Result register() {
        return ok(views.html.register.render());
    }

    public Result calculator() {
        return ok(views.html.calculator.render());
    }

    public Result calculatorSubmit(Http.Request request){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest(request);
        String script = dynamicForm.get("script");
        Document doc = Jsoup.parse(script);
        String output = "";
        List<String> id = new ArrayList<String>();
        List<String> name = new ArrayList<String>();
        List<Integer> weight = new ArrayList<Integer>();
        List<Float> value = new ArrayList<Float>();
        for (Element table : doc.select("table.KOLOROWA")) {
        for (Element row : table.select("tr")) {
            Elements tds = row.select("td tr");
           int i = 0;
           for (Element element : tds)
           {
                if(i == 0)
                {
                    i ++;
                    continue;
                }
                // output += element.text();
                // output += "\n";
                output = element.text();
                String[] array = output.split(" ");
                id.add(array[0]);
                name.add(array[1]);
                if(isNumeric(array[array.length-2]))
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
        return ok(value.toString());
    }

}
