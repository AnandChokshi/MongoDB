package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anand on 1/7/2015.
 */
public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldSparkFreemarkerStyle.class,"/");
        final StringWriter writer = new StringWriter();
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Template helloTemp = configuration.getTemplate("hello.ftl");

                    Map<String, String> helloMap = new HashMap<String, String>();
                    helloMap.put("name","Spark Freemarker Hello");

                    helloTemp.process(helloMap,writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
            return writer;
            }
        });
    }
}
