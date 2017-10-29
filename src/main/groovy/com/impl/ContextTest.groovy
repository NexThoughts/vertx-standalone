package com.impl

import com.impl.verticle.FirstVerticle
import io.vertx.core.Context
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

class ContextTest {
    public static void main(String[] arg) {
        deployVerticleAsWorker()
    }

    static def deployVerticleAsWorker() {
        println("Going to initialize Vertx")
        Vertx vertx = Vertx.vertx()
        println("Setting Deployment Options")
        DeploymentOptions deploymentOptions = new DeploymentOptions()
        deploymentOptions.worker = true
        println("Going to deploy Verticle")
        vertx.deployVerticle(new FirstVerticle(), deploymentOptions)
        def context = vertx.getOrCreateContext()
        contextInfo(context)
        println("Going to un-deploy verticle")
        vertx.close()
    }

    static def deployVerticleAsStandard() {
        println("Going to initialize Vertx")
        Vertx vertx = Vertx.vertx()
        println("Going to deploy Verticle")
        vertx.deployVerticle(new FirstVerticle())
        def context = vertx.getOrCreateContext()
        contextInfo(context)
        println("Going to un-deploy verticle")
        vertx.close()
    }

    static def contextInfo(Context context) {
        if (context.isEventLoopContext()) {
            println("Context attached to Event Loop")
        } else if (context.isWorkerContext()) {
            println("Context attached to Worker Thread")
        } else if (context.isMultiThreadedWorkerContext()) {
            println("Context attached to Worker Thread - multi threaded worker")
        } else if (!Context.isOnVertxThread()) {
            println("Context not attached to a thread managed by vert.x")
        }
        println(context.properties)
    }
}
