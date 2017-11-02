package com.impl.verticle

import io.vertx.core.AbstractVerticle

class EventBusSimpleVerticle extends AbstractVerticle {

    void start() {
        vertx.eventBus().consumer("local", { message ->
            println("I got your message: ${message.body()}")
            message.reply("Pong")
        })
    }

    void stop() {

    }
}
