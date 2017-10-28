package com.demo

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.SQLClient
import io.vertx.ext.sql.SQLConnection
import io.vertx.ext.web.Router

public class JDBCDemo {

    public static String jdbc1() {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
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
        println "----1-3-----------"
        return "Success - 3"
    }

    public static String jdbc() {
        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])
        println "-----0-----------"

        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/demo_lending?autoreconnect=true")
                .put("user", "demo")
                .put("password", "demo")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30);

        SQLClient client = JDBCClient.createShared(vertx, config);
        println "-----1-----------" + client

        client.getConnection({ res ->

            println "-----2-----------" + res.properties
            if (res.failed()) {
                println "-----2 Failed-----------"
                println(res.cause().getMessage())
                return
            }
            if (res.succeeded()) {
                println "-----2.1-----------"
                SQLConnection connection = res.result();
                println "-----2.2-----------"
                connection.query("SELECT * FROM user", { res2 ->
                    println "-----2.3-----------"
                    if (res2.succeeded()) {
                        def rs = res2.result();
                        rs.results.each { line ->
                            println('----------' + groovy.json.JsonOutput.toJson(line))
                        }

                    }
                });
            } else {

                println "---- Failed to get connection ------"

                // Failed to get connection - deal with it
            }
        });
        println "-----3-----------"

        return "Success - 2"
    }

}
