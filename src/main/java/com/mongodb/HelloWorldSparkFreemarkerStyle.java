package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by domrachev_sv on 17.08.2015.
 */
public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) throws IOException {
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
        cfg.setDefaultEncoding("UTF-8");
       cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
       // Template temp = cfg.getTemplate("test.ftl");
      //  Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");


        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = cfg.getTemplate("hello.ftl");

                    Map<String, Object> helloMap = new HashMap<String, Object>();
                    helloMap.put("name", "Sergey");
                    helloTemplate.process(helloMap, writer);
                    System.out.println(writer);

                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }

}
