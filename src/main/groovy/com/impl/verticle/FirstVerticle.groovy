package com.impl.verticle

import io.vertx.core.AbstractVerticle

class FirstVerticle extends AbstractVerticle {
    public void start() {
        println("Start Your First Verticle")
    }

    public void stop() {
        println("Stopping First Verticle")
    }
}