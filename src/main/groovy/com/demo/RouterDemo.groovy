package com.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

public class RouterDemo extends AbstractVerticle {

    public static def productList = new HashMap<>()

    public void start() {
        setUpInitialData()
        Router router = Router.router(vertx);
        println "----1----------"
        router.route().handler(BodyHandler.create());
        println "----2----------"
        router.get("/products").handler(this.&handleListProducts);
        println "----3----------"
        router.get("/products/:productID").handler(this.&handleGetProduct);
        println "----4----------"

        vertx.createHttpServer().requestHandler(router.&accept).listen(8086);
    }

    public void stop() {
        println "Undeploying RouterDemo Verticle"
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

    public static void handleListProducts(RoutingContext routingContext) {
        JsonArray arr = new JsonArray();
        productList.each { k, v ->
            arr.add(v)
        }
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
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
}
