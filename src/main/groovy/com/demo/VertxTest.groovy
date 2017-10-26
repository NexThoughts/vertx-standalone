package com.demo

import io.vertx.core.Vertx;
import io.vertx.ext.web.*;
import io.vertx.ext.jdbc.*;
import io.vertx.core.json.*;
import io.vertx.ext.sql.*;
import io.vertx.core.http.*;
import io.vertx.ext.web.handler.*;


public class VertxTest {

    public static void main(String[] args){
        firstMethod()
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


}
