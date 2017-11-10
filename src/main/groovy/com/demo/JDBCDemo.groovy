package com.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient

public class JDBCDemo extends AbstractVerticle {

    public void start() {
        println "---1--0-----------"

        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/demo_lending?autoreconnect=true")
                .put("user", "demo")
                .put("password", "demo")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30);

        def client = JDBCClient.createShared(vertx, config);
        println "-----1-----------" + client

        client.getConnection({ conn ->

            def connection = conn.result()

            // query some data with arguments
            connection.query("select * from user where id < 20", { rs ->
                if (rs.failed()) {
                    println("Cannot retrieve the data from the database")
                    rs.cause().printStackTrace()
                    return
                }

                rs.result().results.each { line ->
                    println("-----****---- : " + groovy.json.JsonOutput.toJson(line))
                }

                // and close the connection
                connection.close({ done ->
                    if (done.failed()) {
                        throw new java.lang.RuntimeException(done.cause())
                    }

                })
            })
        })
        println "----Success-------"
    }
}