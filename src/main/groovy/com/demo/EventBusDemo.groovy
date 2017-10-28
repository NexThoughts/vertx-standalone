package com.demo

import io.vertx.core.Vertx

class EventBusDemo {


    public static eventBusExample() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
        def eventBus = vertx.eventBus()

        def consumer = eventBus.consumer("news.uk.sport")
        consumer.handler({ message ->
            println("I have received a message: ${message.body()}")
        })
        consumer.completionHandler({ res ->
            if (res.succeeded()) {
                println("The handler registration has reached all nodes")
            } else {
                println("Registration failed!")
            }
        })

        def options = [
                headers: [
                        "some-header": "some-value"
                ]
        ]
        eventBus.publish("news.uk.sport", "Yay! Someone kicked a ball: Publish", options)

        eventBus.send("news.uk.sport", "Yay! Someone kicked a ball : Send")

    }

}
