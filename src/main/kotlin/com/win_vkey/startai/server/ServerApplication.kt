package com.win_vkey.startai.server


import ch.qos.logback.core.util.FileUtil
import org.springframework.beans.BeanWrapperImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.util.FileCopyUtils
import java.io.File
import java.io.InputStream

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    extractResources()
    runApplication<ServerApplication>(*args)
}

fun extractResources() {
    println(System.getProperty("user.dir"))
    var resolver: PathMatchingResourcePatternResolver = PathMatchingResourcePatternResolver();
    try {
        //获取所有匹配的文件
        var resources: Array<out Resource> = resolver.getResources("classpath*:/extract/*")
        println(resources)
        for (one in resources) {
            println(BeanWrapperImpl(one).getPropertyValue("path"))
            //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
            var stream: InputStream = one.inputStream
            var tmpFile: File =  File("./.extract",one.filename)
            FileUtil.createMissingParentDirectories(tmpFile)
            FileCopyUtils.copy(stream, tmpFile.outputStream())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

