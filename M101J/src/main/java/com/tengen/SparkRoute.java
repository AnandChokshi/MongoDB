package com.tengen;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by Anand on 1/8/2015.
 */
public class SparkRoute {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Return from default";
            }
        });

        Spark.get(new Route("/test") {
            @Override
            public Object handle(Request request, Response response) {
                return "Return from test";
            }
        });

        Spark.get(new Route("/echo/:thing") {
            @Override
            public Object handle(Request request, Response response) {
                return request.params(":thing");
            }
        });
    }
}
