package com.impl.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject

class EventBusJsonProdVerticle extends AbstractVerticle {

    void start() {
        vertx.eventBus().consumer("hello", { message ->
            JsonObject jsonObject = new JsonObject()
                    .put("served-by", this.toString())

            if (message.body() == "") {
                message.reply(jsonObject.put("message", "hello"))
            } else {
                message.reply(jsonObject.put("message", "hello ${message.body()}"))
            }
        })
    }

    void stop() {

    }
}
