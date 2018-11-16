package com.win_vkey.startai.server.Exception

/**
 * @Author: AiMin
 * @Description:
 * @Date: Created in 22:14 2018/11/16
 * @Modified By:
 */
class UnsupportedModeException(private val mode: Int) :
        BaseException(BaseException.T.UNSUPPORTED_FILTER_MODE, "unsupported key filter mode (${mode})") {}
