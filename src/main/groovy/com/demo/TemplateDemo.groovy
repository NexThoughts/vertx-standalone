package com.demo

import io.vertx.core.Vertx
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

public class TemplateDemo extends AbstractVerticle {

    public static String thymeleaf() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        println "----thymeleaf---- Hello -------------"

        final Router router = Router.router(vertx);

        // In order to use a Thymeleaf template we first need to create an engine
        final ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();

        // Entry point to the application, this will render a custom JADE template.
        router.get().handler({ ctx ->

            // we define a hardcoded title for our application
            ctx.put("welcome", "Hi there!");

            // and now delegate to the engine to render it.
            engine.render(ctx, "/webapp/template/thymeleaf/index.html", { res ->
                if (res.succeeded()) {
                    ctx.response().end(res.result());
                } else {
                    ctx.fail(res.cause());
                }
            });
        });

        // start a HTTP web server on port 8080
        vertx.createHttpServer().requestHandler(router.&accept).listen(8087);
        return "Success"


    }


}
