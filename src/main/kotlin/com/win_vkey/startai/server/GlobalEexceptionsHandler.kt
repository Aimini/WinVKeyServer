package com.win_vkey.startai.server

import com.win_vkey.startai.server.Exception.BaseException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import kotlin.reflect.full.isSubclassOf

/**
 * @Author: AiMin
 * @Description:
 * @Date: Created in 12:24 2018/11/4
 * @Modified By:
 */
@RestControllerAdvice
class GlobalEexceptionsHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler
    protected  fun handleConflict(ex:Exception, request: WebRequest): ResponseEntity<Any>? {
        if(ex::class.isSubclassOf(BaseException::class)){
            val status = HttpStatus.METHOD_NOT_ALLOWED
            val headers = HttpHeaders()
            return super.handleExceptionInternal(ex as HttpRequestMethodNotSupportedException,ex, headers, status, request)
        }
        return super.handleException(ex,request)
    }
}