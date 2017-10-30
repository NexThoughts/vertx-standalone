package com.demo

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer

class FileSystemDemo {

    static def fileCopyDemoNonBlocking(String path) {
        Vertx vertx = Vertx.vertx()
        def fs = vertx.fileSystem()

        fs.copy("$path/foo.txt", "$path/bar.txt", { res ->
            if (res.succeeded()) {
                println("File has been copied")
            } else {
                println("Failed to Copy")
            }
        })
    }

    static def fileCopyDemoBlocking(String path) {
        Vertx vertx = Vertx.vertx()
        def fs = vertx.fileSystem()
        fs.copyBlocking("$path/foo.txt", "$path/bar.txt")
    }

    static def readingFromFileNonBlocking(String path) {
        Vertx vertx = Vertx.vertx()

        vertx.fileSystem().readFile("$path/readme.txt", { result ->
            if (result.succeeded()) {
                println(result.result())
            } else {
                System.err.println("Oh oh ...${result.cause()}")
            }
        })
    }

    static def writingToFileNonBlocking(String path) {
        Vertx vertx = Vertx.vertx()
        vertx.fileSystem().writeFile("$path/hello.txt", Buffer.buffer("Hello"), { result ->
            if (result.succeeded()) {
                println("File written")
            } else {
                System.err.println("Oh oh ...${result.cause()}")
            }
        })
    }

    static def existenceAndDeleteNonBlocking(String path) {
        Vertx vertx = Vertx.vertx()
        vertx.fileSystem().exists("$path/junk.txt", { result ->
            if (result.succeeded() && result.result()) {
                vertx.fileSystem().delete("$path/junk.txt", { r ->
                    println("File deleted")
                })
            } else {
                System.err.println("Oh oh ... - cannot delete the file: ${result.cause()}")
            }
        })
    }
}