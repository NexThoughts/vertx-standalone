package com.impl.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class ThirdVerticle extends AbstractVerticle {
    void start(Future<Void> future) {
        println "Starting ThirdVerticle"
        vertx.deployVerticle(new FirstVerticle(), { res ->
            if (res.succeeded()) {
                println("Verticle First Deployed")
                future.complete()
            } else {
                future.fail()
            }
        })
    }

    void stop(Future<Void> future) {
        println("Stoping ThirdVerticle")
        future.complete()
    }
}
