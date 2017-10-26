package com.demo

import com.demo.model.User
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

public class RouterDemo {

    public static def productList = new HashMap<>();

    public static String route1() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        setUpInitialData()

        Router router = Router.router(vertx);
        println "----1----------"
        router.route().handler(BodyHandler.create());
        println "----2----------"
        router.get("/products/:productID").handler(this.&handleGetProduct);
        println "----3----------"
        router.put("/products/:productID").handler(this.&handleAddProduct);
        println "----4----------"
        router.get("/products").handler(this.&handleListProducts);
        println "----5----------"
        router.get("/users").handler(this.&handleListUsersFromDatabase);
        println "----6----------"


        vertx.createHttpServer().requestHandler(router.&accept).listen(8086);

        return "Success - 4"
    }

    public static void handleGetProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");
        HttpServerResponse response = routingContext.response();
        if (productID == null) {
            sendError(400, response);
        } else {
            JsonObject product = productList.get(productID);
            if (product == null) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(product.encodePrettily());
            }
        }
    }

    public static void handleAddProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");
        HttpServerResponse response = routingContext.response();
        if (productID == null) {
            sendError(400, response);
        } else {
            JsonObject product = routingContext.getBodyAsJson();
            if (product == null) {
                sendError(400, response);
            } else {
                productList.put(productID, product);
                response.end();
            }
        }
    }

    public static void handleListProducts(RoutingContext routingContext) {
        JsonArray arr = new JsonArray();
        productList.each { k, v ->
            arr.add(v)
        }
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
    }

    public static void handleListUsersFromDatabase(RoutingContext routingContext) {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        println "----7----------"

        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/demo_lending?autoreconnect=true")
                .put("user", "demo")
                .put("password", "demo")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30);

        def client = JDBCClient.createShared(vertx, config);
        println "-----1---1--------" + client
        List<User> userList = []
        client.getConnection({ conn ->
            def connection = conn.result()
            println "----9----------"

            connection.query("select * from user where id < 20", { rs ->
                if (rs.failed()) {
                    println "----10----------"

                    println("Cannot retrieve the data from the database")
                    rs.cause().printStackTrace()
                    return
                }
                println "----11----------"

                rs.result().results.each { line ->
                    println "---1---" + line.class
                    println "---2---" + line.properties
                    println "---3---" + line.get(4)
                    println "---4---" + line.get(10)
                    println "---5---" + line.get(11)
                    println "---6---" + line.get(12)
                    println "---7---" + line.get(13)
                    userList.add(new User(email: line.get(12), uuid: line.get(13)))
                    println("--------- : " + groovy.json.JsonOutput.toJson(line))
                    println "-"
                    println "-"
                    println "-"
                    println "-"
                }
                println "----12----------"

                // and close the connection
                connection.close({ done ->
                    if (done.failed()) {
                        throw new java.lang.RuntimeException(done.cause())
                    }
                })
                println "----13----------"

            })
            println "----14----------"

        })
        println "----15----------"

        routingContext.next();
        println "-----userList------- " + userList
        routingContext.response().putHeader("content-type", "application/json").end(userList*.toString());
    }

    public static void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

    public static void setUpInitialData() {
        addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", "3.99").put("weight", "150"));
        addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", "5.99").put("weight", "100"));
        addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", "1.00").put("weight", "80"));
    }

    public static void addProduct(JsonObject product) {
        productList.put(product.getString("id"), product);
    }

    public static String route2() {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        setUpInitialData()

        Router router = Router.router(vertx);
        def route1 = router.route("/some/path/").handler({ routingContext ->

            def response = routingContext.response()
            // enable chunked responses because we will be adding data as
            // we execute over other handlers. This is only required once and
            // only if several handlers do output.
            response.setChunked(true)

            response.write("route1\n")

            // Call the next matching route after a 5 second delay
            routingContext.vertx().setTimer(5000, { tid ->
                routingContext.next()
            })
        })

        def route2 = router.route("/some/path/").handler({ routingContext ->

            def response = routingContext.response()
            response.write("route2\n")

            // Call the next matching route after a 5 second delay
            routingContext.vertx().setTimer(5000, { tid ->
                routingContext.next()
            })
        })

        def route3 = router.route("/some/path/").handler({ routingContext ->

            def response = routingContext.response()
            response.write("route3")

            // Now end the response
            routingContext.response().end()
        })

        return "Success - 5"
    }


}
