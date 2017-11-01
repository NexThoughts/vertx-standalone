package com.impl

import com.impl.verticle.consumer.ConsumerVerticle
import io.vertx.core.Vertx

class HttpMicroTest {
    public static void main(String[] ar) {
        Vertx vertx = Vertx.vertx()
//        vertx.deployVerticle(new HttpMicroVerticle())
//        vertx.deployVerticle(new HttpMicroVerticleWithRoute())
//        vertx.deployVerticle(new HttpMicroProdJson())
        vertx.deployVerticle(new ConsumerVerticle())

    }
}
