package com.demo

import io.vertx.core.Vertx

public class VertxMain {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        // Hello World Demo
//        vertx.deployVerticle(new HelloWorldDemo())

        // EventBus Example
//        vertx.deployVerticle(new EventBusDemo())

        // Timer Example
//        vertx.deployVerticle(new TimerDemo())

        //Router Example
//        vertx.deployVerticle(new RouterDemo())

        // JDBC Example
//        vertx.deployVerticle(new JDBCDemo())

        // Mail Example
//        vertx.deployVerticle(new SMTPDemo())

        // Template Example
//        vertx.deployVerticle(new TemplateDemo())
    }
}