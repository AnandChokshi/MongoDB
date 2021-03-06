package com.tengen;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Anand on 1/15/2015.
 */
public class DocumentRepresentationTest {
    public static void main(String[] args) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("username", "Anand");
        doc.put("birthdate", new Date(1212));
        doc.put("programmer", true);
        doc.put("age", 22);
        doc.put("languages", Arrays.asList("java", "C++"));
        doc.put("address", new BasicDBObject("street", "4720 E Atherton St")
                .append("Apt", 3)
                .append("city", "Long Beach, CA - 90815"));
    }
}
