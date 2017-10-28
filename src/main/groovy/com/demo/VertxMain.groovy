package com.demo

import io.vertx.core.Vertx;
import io.vertx.ext.web.*;
import io.vertx.ext.jdbc.*;
import io.vertx.core.json.*;
import io.vertx.ext.sql.*;
import io.vertx.core.http.*;
import io.vertx.ext.web.handler.*;


public class VertxMain {

    public static void main(String[] args) {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
        vertx.deployVerticle(new FutureDemo())

    }

    public static String firstMethod() {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        println "----#######---- Hello -------------"

        vertx.createHttpServer().requestHandler({ req ->
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!")
        }).listen(8084)

        return "Success"

    }

    public static String web() {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        def server = vertx.createHttpServer()

        def router = Router.router(vertx)

        router.route().handler({ routingContext ->

            // This handler will be called for every request
            def response = routingContext.response()
            response.putHeader("content-type", "text/plain")

            // Write to the response and end it
            response.end("Hello World from Vert.x-Web!")
        })

        server.requestHandler(router.&accept).listen(8085)
        return "Success - 1"

    }
}
