package com.impl.verticle

import io.vertx.core.AbstractVerticle

class SecondVerticle extends AbstractVerticle {
    public void start() {
        println("Start Your Second Verticle")
        vertx.deployVerticle(new FirstVerticle())
    }

    public void stop() {
        println("Stopping Second Verticle")
    }
}
