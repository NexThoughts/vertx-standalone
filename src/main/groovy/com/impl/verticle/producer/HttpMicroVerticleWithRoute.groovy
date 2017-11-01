package com.impl.verticle.producer

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router

class HttpMicroVerticleWithRoute extends AbstractVerticle {
    void start() {
        Router router = Router.router(vertx)
        router.get("/").handler({ rc -> rc.response().end("Welcome to World of Vert.x") })
        router.get("/:name").handler({ rc -> rc.response().end("Hello ${rc.pathParam("name")} to the world of Vert.x") })
        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(8080)

    }

    void stop() {

    }
}