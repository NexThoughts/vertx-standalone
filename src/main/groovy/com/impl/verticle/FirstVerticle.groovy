package com.impl.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class FirstVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) {
        println("Start Your Verticle")
    }

    @Override
    public void stop(Future stopFuture) {
        println("Stopping Verticle")
    }
}