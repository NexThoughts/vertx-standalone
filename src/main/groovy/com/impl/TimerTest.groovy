package com.impl

import io.vertx.core.Vertx

class TimerTest {
    public static void main(String[] arg) {
        periodicExample()
    }

    static def timerExample() {
        Vertx vertx = Vertx.vertx()
        vertx.setTimer(1000, { id ->
            println("After One Second this will be printed")
        })
        println("First It will be printed")
    }

    static def periodicExample() {
        Vertx vertx = Vertx.vertx()
        def timerId = vertx.setPeriodic(1000, { id ->
            println "And every second this is printed"
        })
        println "First this is printed"
        println("Cancelling Time")
//        vertx.cancelTimer(timerId)
    }
}
