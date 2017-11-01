package com.impl.verticle

import io.vertx.core.AbstractVerticle

class HttpMicroVerticle extends AbstractVerticle {
    void start() {
        vertx.createHttpServer()
                .requestHandler(
                { req ->
                    req.response().end("Welcome to World of Vert.x")
                })
                .listen(8080)

    }

    void stop() {

    }
}
