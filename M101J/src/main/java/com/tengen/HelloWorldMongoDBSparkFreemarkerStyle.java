package com.tengen;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by Anand on 1/7/2015.
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {
    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldMongoDBSparkFreemarkerStyle.class,"/");

        MongoClient client = new MongoClient(new ServerAddress("localhost",27017));
        DB database = client.getDB("course");
        final DBCollection collection = database.getCollection("hello");

        final StringWriter writer = new StringWriter();
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Template helloTemp = configuration.getTemplate("hello.ftl");

                    DBObject document = collection.findOne();

                    helloTemp.process(document,writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
