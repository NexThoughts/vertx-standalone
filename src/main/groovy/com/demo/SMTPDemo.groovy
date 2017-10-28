package com.demo

import io.vertx.core.Vertx
import io.vertx.ext.mail.MailClient

class SMTPDemo {


    public static sendEmail() {

        def vertx = Vertx.vertx([
                workerPoolSize: 40
        ])

        println "---sendEmail--1--------"

        def config = [:]
        config.hostname = "smtp.zoho.com"
        config.port = 587
        config.starttls = "REQUIRED"
        config.username = ""
        config.password = ""

        println "---sendEmail--2--------" + config

        def mailClient = MailClient.createNonShared(vertx, config)

        println "-----mailClient-------" + mailClient

        def message = [:]
        message.from = ""
        message.to = ""
//        message.cc = "Another User <another@example.net>"
        message.text = "Hello: Simple Email using Vertx"
        message.subject = "Hello: Simple Email using Vertx"
        message.html = "Hi, this is html text <a href=\"http://vertx.io\">vertx.io</a>"

        println "-----message-------" + message

        mailClient.sendMail(message, { result ->
            println "--------- Sending Email Message ---------"
            if (result.succeeded()) {
                println(result.result())
                println "--------- Sending Email Message Succeeded---------"
            } else {
                println "--------- Sending Email Message FAILED---------"
                result.cause().printStackTrace()
            }
        })

        println "------5--------"
    }
}
