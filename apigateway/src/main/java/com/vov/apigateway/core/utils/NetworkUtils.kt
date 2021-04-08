package com.vinsmart.officeapp.apigateway.core.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.vinsmart.commons.core.BaseApplication
import com.vinsmart.officeapp.apigateway.R
import com.vinsmart.officeapp.apigateway.core.response.ApiResponse
import com.vinsmart.officeapp.apigateway.core.response.ResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val CODE_SERVER_ACCOUNT_NO_ACTIVE = 4000017
const val CODE_SERVER_ACCOUNT_WRONG = 4010004

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): ResponseResult<T?> {
    return withContext(dispatcher) {
        try {
            ResponseResult.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            ResponseResult.error<T>(throwable.message)
            when (throwable) {
                is ConnectException, is UnknownHostException -> ResponseResult.error<T>(BaseApplication.getStringResource(R.string.message_connect_exception))
                is SocketTimeoutException -> ResponseResult.error<T>(BaseApplication.getStringResource(R.string.message_socket_timeout_exception))
                is HttpException -> {
                    val code = throwable.code()
                    val contentError = throwable.response()?.errorBody()?.stringSuspending()
                    val responseError = Gson().fromJson(contentError, ApiResponse::class.java)

                    if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        if(CODE_SERVER_ACCOUNT_WRONG == responseError?.code) {
                            ResponseResult.error<T>(responseError.code)
                        } else {
                            ResponseResult.error<T>(BaseApplication.getStringResource(R.string.message_unauthorized))
                        }
                    } else {
                        try {
                            // Account has not active yet
                            if(CODE_SERVER_ACCOUNT_NO_ACTIVE == responseError.code) {
                                return@withContext ResponseResult.error<T>(responseError.code)
                            }
                            contentError?.let {
                                return@withContext ResponseResult.error<T>(responseError.message, responseError.errors)
                            }
                            return@withContext ResponseResult.error<T>(throwable.response()?.message())
                        } catch (e: JsonSyntaxException) {
                            return@withContext ResponseResult.error<T>(BaseApplication.getStringResource(R.string.error_other))
                        } catch (e: Exception) {
                            return@withContext ResponseResult.error<T>(throwable.response()?.message())
                        }
                    }
                }
                else -> {
                    if (!BaseApplication.checkNetwork()) {
                        ResponseResult.error<T>(BaseApplication.getStringResource(R.string.no_internet))
                    } else {
                        ResponseResult.error<T>(throwable.message)
                    }
                }
            }
        }
    }
}

suspend fun <Result, Data: ApiResponse<Result>> safeApiCallWithModel(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> Data): ResponseResult<Result?> {
    return withContext(dispatcher) {
        try {
            ResponseResult.success(apiCall.invoke().data)
        } catch (throwable: Throwable) {
            ResponseResult.error<Result>(throwable.message)
            when (throwable) {
                is ConnectException, is UnknownHostException -> ResponseResult.error<Result>(BaseApplication.getStringResource(R.string.message_connect_exception))
                is SocketTimeoutException -> ResponseResult.error<Result>(BaseApplication.getStringResource(R.string.message_socket_timeout_exception))
                is HttpException -> {
                    val code = throwable.code()
                    if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        ResponseResult.error<Result>(BaseApplication.getStringResource(R.string.message_unauthorized))
                    } else {
                        try {
                            val contentError = throwable.response()?.errorBody()?.stringSuspending()
                            val responseError = Gson().fromJson(contentError, ApiResponse::class.java)
                            contentError?.let {
                                return@withContext ResponseResult.error<Result>(responseError.message, responseError.errors)
                            }
                            return@withContext ResponseResult.error<Result>(throwable.response()?.message())
                        } catch (e: JsonSyntaxException) {
                            return@withContext ResponseResult.error<Result>(BaseApplication.getStringResource(R.string.error_other))
                        } catch (e: Exception) {
                            return@withContext ResponseResult.error<Result>(throwable.response()?.message())
                        }
                    }
                }
                else -> {
                    if (!BaseApplication.checkNetwork()) {
                        ResponseResult.error<Result>(BaseApplication.getStringResource(R.string.no_internet))
                    } else {
                        ResponseResult.error<Result>(throwable.message)
                    }
                }
            }
        }
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
        withContext(Dispatchers.IO) { string() }