package com.impl

import com.impl.verticle.FirstVerticle
import io.vertx.core.Vertx

class VerticleTest {

    public static void main(String[] arg) {
        Vertx vertx = Vertx.vertx()
        deployVerticleAsync(vertx)
    }

    static def deployVerticleByInstance(Vertx vertx) {
        //Deploy Vertex
        vertx.deployVerticle(new FirstVerticle())
    }

    static def deployVerticleByClassName(Vertx vertx) {
        //You can also deploy verticle by class name
        vertx.deployVerticle("com.impl.verticle.FirstVerticle")
    }

    static def deployVerticleAsync(Vertx vertx) {
        /* vertx.deployVerticle(new FirstVerticle(), { res ->
             if (res.succeeded()) {
                 println("Verticle has been deployed with id:- ${res.result()}")
             } else {
                 println("Deployment is failed")
             }
         })*/



        /*vertx.deployVerticle(new FirstVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("FirstVerticle deployment complete")
            }
        })*/
    }
}
