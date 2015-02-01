package com.tengen;


import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anand on 1/7/2015.
 */
public class HelloWorldFreemarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldFreemarkerStyle.class,"/");

        try {
            Template helloTemp = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();

            Map<String, String> helloMap = new HashMap<String, String>();
            helloMap.put("name","Hello Freemarker");

            helloTemp.process(helloMap,writer);
            System.out.println(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
