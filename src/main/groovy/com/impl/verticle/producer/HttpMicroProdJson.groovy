package com.impl.verticle.producer

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class HttpMicroProdJson extends AbstractVerticle {

    void start() {
        Router router = Router.router(vertx)
        router.get("/").handler(this.&hello)
        router.get("/:name").handler(this.&hello)

        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(8080)
    }

    void stop() {

    }

    private void hello(RoutingContext rc) {
        String message = ""
        if (rc.pathParam("name")) {
            message += "Hello ${rc.pathParam("name")}"
        }
        JsonObject jsonObject = new JsonObject().put("message", message)
        rc.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(jsonObject.encodePrettily())
    }
}