package com.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class SessionDemo extends AbstractVerticle {

    public static String sessionDemo() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        println "----SessionDemo---- Hello -------------"

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route().handler({routingContext ->

            Session session = routingContext.session();

            Integer cnt = session.get("hitcount");
            cnt = (cnt == null ? 0 : cnt) + 1;

            session.put("hitcount", cnt);

            routingContext.response().putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hitcount: " + cnt + "</h1></body></html>");
        });

        vertx.createHttpServer().requestHandler(router.&accept).listen(8088);
        return "Success"


    }


}
