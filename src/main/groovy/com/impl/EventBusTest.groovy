package com.impl

import com.impl.verticle.EventBusSimpleVerticle
import io.vertx.core.Vertx

class EventBusTest {

    public static void main(String[] ar) {
        sendMessageToEventBusVerticle()
    }

    static def simpleEventBusPublishing() {
        Vertx vertx = Vertx.vertx()
        println("Retrieve The Event Bus from vertx")
        def eb = vertx.eventBus()

        //Registering Handler (Consumer)
        println("Registering Handler for the vertx")
        eb.consumer("anyAddress", { message ->
            println("I have received a message: ${message.body()}")

        })

        println("Going to Publish Message on the Event Bus")

        eb.publish("anyAddress", "Hi, My Name is Ver.x")
        println("Publishing has been completed")
    }

    static def simpleEventBusSending() {
        Vertx vertx = Vertx.vertx()
        println("Retrieve The Event Bus from vertx")
        def eb = vertx.eventBus()

        //Registering Handler (Consumer)
        println("Registering Handler for the vertx")
        eb.consumer("anyAddress", { message ->
            println("I have received a message: ${message.body()}")
        })

        println("Going to Sending Message on the Event Bus")

        eb.send("anyAddress", "Hi, My Name is Ver.x")
        println("Sending has been completed")
    }

    static def newWayOfEventBus() {
        Vertx vertx = Vertx.vertx()
        def eventBus = vertx.eventBus()
        def consumer = eventBus.consumer("my.nexthought")
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

        eventBus.publish("my.nexthought", "This is your message")

        consumer.unregister({ res ->
            if (res.succeeded()) {
                println("The handler un-registration has reached all nodes")
            } else {
                println("Un-registration failed!")
            }
        })

    }

    static def sendingHeaderOnEventBus() {
        Vertx vertx = Vertx.vertx()
        println("Retrieve The Event Bus from vertx")
        def eb = vertx.eventBus()

        //Registering Handler (Consumer)
        println("Registering Handler for the vertx")
        eb.consumer("anyAddress", { message ->
            println("I have received a message: ${message.body()}")

        })

        println("Going to Publish Message on the Event Bus")

        def options = [
                headers: [
                        "some-header": "some-value"
                ]]

        eb.publish("anyAddress", "Hi, My Name is Ver.x", options)
        println("Publishing has been completed")
    }

    static def sendingMessageAndAcknowledge() {
        Vertx vertx = Vertx.vertx()
        println("Retrieve The Event Bus from vertx")
        def eb = vertx.eventBus()

        //Registering Handler (Consumer)
        println("Registering Handler for the vertx")
        eb.consumer("anyAddress", { message ->
            println("I have received a message: ${message.body()}")
            message.reply("I got your message Thank You!!!")
        })

        println("Going to Sending Message on the Event Bus")

        eb.send("anyAddress", "Hi, My Name is Ver.x", { ar ->
            if (ar.succeeded()) {
                println("Received reply: ${ar.result().body()}")
            }
        })
        println("Sending has been completed")
    }

    static def sendMessageToEventBusVerticle() {
        def vertx = Vertx.vertx()

        vertx.deployVerticle(new EventBusSimpleVerticle())

        def eb = vertx.eventBus()
        eb.send("local", "pong", { ar ->
            if (ar.succeeded()) {
                println("Received reply: ${ar.result().body()}")
            }
        })

        /*vertx.deployVerticle(new EventBusJsonProdVerticle())

        def eb = vertx.eventBus()
        eb.send("hello", "Hello Vertx,\n How it is going.", { ar ->
            if (ar.succeeded()) {
                println("Received reply: ${ar.result().body()}")
            }
        })*/

    }
}
