package com.vinsmart.officeapp.apigateway.core.response

// File ResponseResult
// @project OfficeApp
// @author minhhoang on 12-03-2021
class ResponseResult<Data> private constructor() {
    private var data: Data? = null
    private var code: Int = 0
    private var status: ResponseStatus? = null
    private var message: String? = null
    private var errors: List<Error>? = null

    companion object {
        @JvmStatic
        fun <Data> process(): ResponseResult<Data?> {
            return ResponseResult(ResponseStatus.IN_PROCESS)
        }

        @JvmStatic
        fun <Data> success(data: Data?): ResponseResult<Data?> {
            return ResponseResult(ResponseStatus.SUCCESS, data)
        }

        @JvmStatic
        fun <Data> error(message: String?, errors: List<Error>? = null): ResponseResult<Data?> {
            return ResponseResult(ResponseStatus.ERROR, message, errors)
        }

        @JvmStatic
        fun <Data> error(code: Int): ResponseResult<Data?> {
            return ResponseResult(ResponseStatus.ERROR, code)
        }
    }

    private constructor(status: ResponseStatus) : this() {
        this.status = status
    }

    private constructor(status: ResponseStatus, data: Data?) : this() {
        this.status = status
        this.data = data
    }

    private constructor(status: ResponseStatus, message: String?, errors: List<Error>?) : this() {
        this.status = status
        this.message = message
        this.errors = errors
    }

    private constructor(status: ResponseStatus, code: Int) : this() {
        this.status = status
        this.code = code
    }

    fun getData() = data
    fun getCode() = code
    fun getStatus() = status
    fun getMessage() = message
    fun getErrors() = errors
    fun isFinish() = status != ResponseStatus.IN_PROCESS
}

enum class ResponseStatus {
    IN_PROCESS,
    SUCCESS,
    ERROR
}