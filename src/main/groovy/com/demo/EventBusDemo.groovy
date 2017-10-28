package com.demo

import io.vertx.core.Vertx

class EventBusDemo {


    public static eventBusExample() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
        def eventBus = vertx.eventBus()

        def consumer = eventBus.consumer("news.india.fashion")
        consumer.handler({ message ->
            println("FASHION: I have received a message: ${message.body()}")
        })

        def consumer1 = eventBus.consumer("news.india.sport")
        consumer1.handler({ message ->
            println("SPORTS: I have received a message: ${message.body()}")
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
        eventBus.publish("news.india.sport", "Yay! Someone kicked a ball: Publish")

        eventBus.publish("news.india.fashion", "Yay! Someone kicked a ball: Publish")

        eventBus.send("news.india.sport", "Yay! Someone kicked a ball : Send")

        eventBus.send("news.india.fashion", "Yay! Someone kicked a ball : Send")

        eventBus.send("news.india.fashion", "! : ${220 + 500}")
        eventBus.send("news.india.fashion", "! : ${someRandomMethod()}")

    }

    public static String someRandomMethod() {
        (0..5).each {
            println "-- Some random text ------" + it
        }

        return "Success"
    }

}
