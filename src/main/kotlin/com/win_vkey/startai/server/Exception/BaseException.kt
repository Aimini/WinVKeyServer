package com.win_vkey.startai.server.Exception

/**
 * @Author: AiMin
 * @Description:
 * @Date: Created in 12:25 2018/11/4
 * @Modified By:
 */

open class BaseException(val code: BaseException.T, message:String = "") : Exception(message) {
    enum class T{
        FORBIDDEN_KEY,
        UNSUPPORTED_FILTER_MODE
    }
}



