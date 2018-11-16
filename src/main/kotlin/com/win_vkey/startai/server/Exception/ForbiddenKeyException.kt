package com.win_vkey.startai.server.Exception

/**
 * @Author: AiMin
 * @Description:
 * @Date: Created in 12:31 2018/11/4
 * @Modified By:
 */

class ForbiddenKeyException : BaseException {
    val keyCode: Int
    val filterModel: Int

    constructor(keyCode: Int, filterModel: Int)
            : super(BaseException.T.FORBIDDEN_KEY,
            "forbidden key(code = ${keyCode}) in mode(type = ${filterModel})") {
        this.keyCode = keyCode
        this.filterModel = filterModel
    }

}