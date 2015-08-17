package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;


public class SparkFormHandling {
    public static void main(String[] args) {
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) throws Exception {

                try {
                    Map<String, Object> fruitsMap = new HashMap<String, Object>();
                    fruitsMap.put("fruits", Arrays.asList("apple", "banana", "orange", "peach"));

                    Template helloTemplate = cfg.getTemplate("fruitPicker.ftl");

                    StringWriter writer = new StringWriter();
                    helloTemplate.process(fruitsMap, writer);
                    return writer;

                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                    return null;
                }

            }
        });

        Spark.post("/favorite_fruit", new Route() {

            public Object handle(Request request, Response response) throws Exception {
                final String fruit = request.queryParams("fruit");

                if(fruit == null) {
                    return "Why don't you pivk one?";
                }
                else {
                    return "You favorite fruit: " + fruit;
                }

            }
        });
    }

}
