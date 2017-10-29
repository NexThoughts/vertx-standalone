package com.impl

import com.impl.verticle.FirstVerticle
import com.impl.verticle.ThirdVerticle
import io.vertx.core.Vertx

class VerticleTest {

    static String verticle_id = ""

    public static void main(String[] arg) {
        Vertx vertx = createVertx()
        deployVerticleAsync(vertx)
    }

    static def createVertx() {
        return Vertx.vertx()
    }

    static def createVertexWithOptions() {
        return Vertx.vertx([workerPoolSize: 40])
    }

    static def deployVerticleByInstance(Vertx vertx) {
        //Deploy Vertex
        vertx.deployVerticle(new ThirdVerticle())
    }

    static def deployVerticleByClassName(Vertx vertx) {
        //You can also deploy verticle by class name
        vertx.deployVerticle("com.impl.verticle.FirstVerticle")
    }

    static def deployVerticleAsync(Vertx vertx) {
        vertx.deployVerticle(new FirstVerticle(), { res ->
            if (res.succeeded()) {
                println("Verticle has been deployed with id:- ${res.result()}")
                verticle_id = res.result()
            } else {
                println("Deployment is failed")
            }
        })

        /*vertx.deployVerticle(new FirstVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("FirstVerticle deployment complete")
            }
        })*/
    }

    static def deployVerticleWithOptions(Vertx vertx) {
        def config = [name: "myVerticle", directory: "/home/vijay"]
        def options = ["config": config, instances: 16]
        vertx.deployVerticle("com.impl.verticle.FirstVerticle", options)

    }

    static def undeployVerticle(Vertx vertx) {
        vertx.undeploy(verticle_id)
    }

    static def undeployVerticleAsync(Vertx vertx) {
        vertx.undeploy(verticle_id, { res ->
            if (res.succeeded()) {
                println("Verticle has been un deployed")
                verticle_id = res.result()
            } else {
                println("UnDeployment is failed")
            }
        })
    }
}
