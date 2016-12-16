package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class HelloWorldController extends Controller {

    public Result helloWorld() {
        return notFound();
    }


    public Result helloMattias() {
        return ok("Hello Mattias");
    }

    public Result hello(String name) {
        return ok("Hello " + name + "!!!!");
    }

    public Result hellos(String name, Integer count) {

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("Hello " + name + "!\n");
        }

        final String outputString = sb.toString();

        return ok(outputString);
    }

    public Result helloWithFullName() {

        /*
        {
            "first_name": "Mattias",
            "last_name": "Levin"
        }

         */

        Logger.error("This is a debug log");



        final JsonNode json = request().body().asJson();
        final String firstName = json.get("first_name").asText();

        final String lastName = json.get("last_name").asText();

        Logger.debug("name {}, {}", firstName, lastName);

        return ok("Hello " + firstName + " " + lastName + "!!!");
    }

}
