package com.win_vkey.startai.server

import org.apache.tomcat.jni.Buffer
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.*
import java.nio.charset.Charset
import javax.annotation.PostConstruct

@RestController
@RequestMapping("key-down")
class KeyController {
    private var pyProcess: Process = Runtime.getRuntime().exec("""python""")
    private var pyProcessReader:BufferedReader = BufferedReader(InputStreamReader(pyProcess.inputStream))
    private var pyProcessWriter: OutputStreamWriter = OutputStreamWriter(pyProcess.outputStream)

    @PostConstruct
    fun loadProcess() {
        pyProcess = Runtime.getRuntime().exec("""python ./.extract/SendKey.py""")
        pyProcessReader = BufferedReader(InputStreamReader(pyProcess.inputStream))
        pyProcessWriter = OutputStreamWriter(pyProcess.outputStream)

    }

    @RequestMapping
    public fun key(KeyCode: Int): String {
        //use sync for single user and low concurrency,(I'm so slothful)
        synchronized(this) {
            var result: String = ""
            val write = Thread(Runnable {
                pyProcessWriter.apply {
                    write(KeyCode.toString() + "\n")
                    flush()
                }
            })
            write.start()
            write.join()

            val read = Thread(Runnable {
                result = pyProcessReader.readLine()

            })

            read.start()
            read.join()
            print(result)
        }
        return "key down(code):$KeyCode"
    }
}
