package com.win_vkey.startai.server

import com.win_vkey.startai.server.Exception.ForbiddenKeyException
import com.win_vkey.startai.server.Exception.UnsupportedModeException
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import javax.annotation.PostConstruct

@RestController
@RequestMapping("key-down")
class KeyController {
    private var pyProcess: Process = Runtime.getRuntime().exec("""python""")
    private var pyProcessReader:BufferedReader = BufferedReader(InputStreamReader(pyProcess.inputStream))
    private var pyProcessWriter: OutputStreamWriter = OutputStreamWriter(pyProcess.outputStream)

    companion object {
        var MODE_WHITELIST = 0
        var MODE_BLACKLIST = 1
    }

    private var mMode:Int = MODE_WHITELIST
    private var mKeyCodes:Array<Int> = arrayOf(0XAD,0xAE,0XAF,0xb1,0xb2,0xb3)


    @PostConstruct
    fun loadProcess() {
        pyProcess = Runtime.getRuntime().exec("""python ./.extract/SendKey.py""")
        pyProcessReader = BufferedReader(InputStreamReader(pyProcess.inputStream))
        pyProcessWriter = OutputStreamWriter(pyProcess.outputStream)
    }


    fun setKeyFilter(mode:Int,keyCodes:Array<Int>){
        if(mode > 1 || mode < 0){
            throw UnsupportedModeException(mode)
        }
        this.mMode = mode
        this.mKeyCodes = keyCodes
    }

    @RequestMapping
    public fun key(KeyCode: Int): String {
        if((mMode == MODE_BLACKLIST && mKeyCodes.contains(KeyCode))
            || (mMode == MODE_WHITELIST && !mKeyCodes.contains(KeyCode)))
            throw  ForbiddenKeyException(KeyCode,mMode)

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
            println(result)
        }
        return "key down(code):$KeyCode"
    }
}
