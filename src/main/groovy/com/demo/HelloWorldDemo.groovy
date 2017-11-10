package com.demo

import io.vertx.core.AbstractVerticle

class HelloWorldDemo extends AbstractVerticle {
    void start() {
        println("Going to start Server")
        vertx.createHttpServer().requestHandler({ req ->
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!")
        }).listen(8084)
        println("Server has been started on 8084 port")
    }

    void stop() {

    }
}
