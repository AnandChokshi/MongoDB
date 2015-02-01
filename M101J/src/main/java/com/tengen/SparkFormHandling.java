package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Anand on 1/8/2015.
 */
public class SparkFormHandling {
    public static void main(String[] args) {
        //freemarker configuration
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        //Spark Route for Get
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    StringWriter writer = new StringWriter();
                    Map<String, List<String>> fruits = new HashMap<String, List<String>>();
                    fruits.put("fruits", Arrays.asList("Apple", "Orange", "Banana", "Grapes"));

                    //freemarker template
                    Template fruitForm = configuration.getTemplate("fruitPicker.ftl");
                    fruitForm.process(fruits, writer);
                    return writer;
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                    return null;
                }
            }
        });

        Spark.post(new Route("/favourite_fruit") {
            @Override
            public Object handle(Request request, Response response) {
                String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why didn't you select any one?";
                } else {
                    return "Your favourite fruit is " + fruit;
                }
            }
        });
    }
}
