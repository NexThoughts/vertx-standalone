package com.demo

import io.vertx.core.Vertx

class WebDemo {

    static def webSample() {
        Vertx vertx = Vertx.vertx()
        def server = vertx.createHttpServer()

        server.requestHandler({ request ->
            def response = request.response()
            response.putHeader("content-type", "text/plain")
            response.end("Hello World!")
        })

        server.listen(8080)
    }
}
