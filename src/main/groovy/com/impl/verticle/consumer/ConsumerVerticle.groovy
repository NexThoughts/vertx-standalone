package com.impl.verticle.consumer

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.HttpRequest
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.codec.BodyCodec

class ConsumerVerticle extends AbstractVerticle {

    void start() {
        Router router = Router.router(vertx)
        router.get("/").handler(this.&invokeMicroService)

        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(8081)
    }

    void stop() {

    }

    private void invokeMicroService(RoutingContext rc) {
        HttpRequest<JsonObject> request = WebClient.create(vertx)
                .get(8080, "127.0.0.1", "/vert.x")
                .as(BodyCodec.jsonObject())


        request.send({ ar ->
            if (ar.failed()) {
                rc.fail(ar.cause())
            } else {
                rc.response().end(ar.result().body().encodePrettily())
            }
        })
    }
}

