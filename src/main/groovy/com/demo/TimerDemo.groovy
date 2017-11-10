package com.demo

import io.vertx.core.AbstractVerticle

class TimerDemo extends AbstractVerticle {
    public void start() {
        timerExample()
        periodicExample()
    }

    def timerExample() {
        vertx.setTimer(1000, { id ->
            println("After One Second this will be printed")
        })
        println("First It will be printed")
    }

    def periodicExample() {
        def timerId = vertx.setPeriodic(1000, { id ->
            println "And every second this is printed"
        })
        println "First this is printed"
        println("Cancelling Time")
//        vertx.cancelTimer(timerId)
    }
}
