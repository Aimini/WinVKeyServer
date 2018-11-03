package com.win_vkey.startai.server

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@RestController
@RequestMapping("key-down")
class KeyController {
    @RequestMapping
    public fun key(KeyCode: Int): String {
        //TODO: execute python script to send key
        val cmd = """cmd /c dir c:\windows""" //raw string with three "
        val process = Runtime.getRuntime().exec(cmd)
        printMessage(process.inputStream)
        printMessage(process.errorStream)
        val value = process.waitFor()

        return "key down(code):$KeyCode"
    }

    private fun printMessage(input: InputStream) {
        Thread(Runnable {
            val reader = InputStreamReader(input)
            val bf = BufferedReader(reader)
            var line: String?
            while(true) {
                line = bf.readLine()
                if(line == null)
                    break
                println(line)
            }
        }).start();
    }
}