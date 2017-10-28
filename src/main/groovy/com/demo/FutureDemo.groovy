package com.demo

import groovy.json.JsonOutput
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.SQLClient
import io.vertx.ext.sql.SQLConnection
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine

import java.util.stream.Collectors

class FutureDemo extends AbstractVerticle {
    SQLClient dbclient = null
    Vertx vertx = null

    private void createVertx() {
        println "----- Creating Vertx -------"
        vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
    }

    private void createDatabaseConnection() {
        println "----- Creating Database Connection 1 -------"
        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/demo_lending?autoreconnect=true")
                .put("user", "demo")
                .put("password", "demo")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30);
        println "----- Creating Database Connection 2 -------"

        dbclient = JDBCClient.createShared(vertx, config);

    }

    private Future<Void> startHttpServer() {

        println "---startHttpServer 1 ----"
        createVertx()
        createDatabaseConnection()
        Future<Void> future = Future.future();
        HttpServer server = vertx.createHttpServer()
        Router router = Router.router(vertx)

//        router.get("/").handler(this.&indexHandler);
        router.get("/users").handler(this.&indexHandler);
        println "---- Created users path -------"
//        router.get("/wiki/:page").handler(this.&pageRenderingHandler)
//        router.post().handler(BodyHandler.create())
//        router.post("/save").handler(this.&pageUpdateHandler)
//        router.post("/create").handler(this.&pageCreateHandler)
//        router.post("/delete").handler(this.&pageDeletionHandler)

        server.requestHandler(router.&accept)
                .listen(8081, { ar ->
            if (ar.succeeded()) {
                println("###### HTTP server running on port 8081");
                future.complete();
            } else {
                println("##### Could not start a HTTP server : ${ar.cause()}");
                future.fail(ar.cause());
            }
        });
        return future;
    }

    FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();

    private void indexHandler(RoutingContext context) {
        println "----indexHandler 1 --"
        dbclient.getConnection { car ->
            if (car.succeeded()) {
                println "----indexHandler 2 --"
                SQLConnection connection = car.result();
                connection.query('SELECT * FROM user', { res ->
                    println "----indexHandler 3 --"
                    List<String> userList = []
                    if (res.succeeded()) {
                        res.result().results.each { line ->
                            println('-----222-----' + JsonOutput.toJson(line))
                            userList.add(JsonOutput.toJson(line))
                        }
                        println "----indexHandler 4 --" + userList
                        connection.close();

                        context.put("title", "User home")
                        context.put("name", "User My Home")
                        context.put("userList", userList)
                        templateEngine.render(context, "main/webapp/template/freemarker", "/index.ftl", { ar ->
                            if (ar.succeeded()) {
                                context.response().putHeader("Content-Type", "text/html");
                                context.response().end(ar.result())
                            } else {
                                context.fail(ar.cause());
                            }
                        });
                        println "----indexHandler 5 --"
                    } else {
                        context.fail(res.cause())
                    }
                });
            } else {
                context.fail(car.cause());
            }
        }
        println "----indexHandler 6 --"
    }

    public void start(Future<Void> startFuture) throws Exception {
        println "--- Inside Start Server ------1------"
        Future<Void> steps = startHttpServer().compose { v ->
//            startHttpServer()
            println "--- Inside Start Server ------2------"
        }
        steps.setHandler({ ar ->
            println "--- Inside Start Server ------3------"
            if (ar.succeeded()) {
                startFuture.complete();
                println "--- Inside Start Server ------4------"
            } else {
                startFuture.fail(ar.cause());
                println "--- Inside Start Server ------5------"
            }
        })
    }
}
