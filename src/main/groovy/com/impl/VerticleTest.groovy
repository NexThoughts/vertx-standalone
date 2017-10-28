package com.impl

import com.impl.verticle.FirstVerticle
import io.vertx.core.Vertx

class VerticleTest {

    public static void main(String[] arg) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new FirstVerticle())
    }
}
