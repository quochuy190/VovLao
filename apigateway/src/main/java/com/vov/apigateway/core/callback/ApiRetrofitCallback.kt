package com.vinsmart.officeapp.apigateway.core.callback

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import com.vinsmart.officeapp.apigateway.core.response.ApiResponse
import com.vinsmart.officeapp.apigateway.core.response.Error
import com.vinsmart.officeapp.apigateway.core.response.ResponseResult
import com.vov.apigateway.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException


sealed class ApiRetrofitCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val responseCode = response.code()
        if (response.body() == null) {
            if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                onMyFailure(
                    call,
                    Throwable(BaseApplication.getStringResource(R.string.message_unauthorized))
                )
                return
            }
            try {
                val contentError = response.errorBody()?.string()
                val responseError = Gson().fromJson(contentError, ApiResponse::class.java)
                contentError?.let {
                    onMyFailure(call, Throwable(responseError.message), responseError.errors)
                    return
                }
                onMyFailure(call, Throwable(response.message()))
                return
            } catch (e: Exception) {
                onMyFailure(call, Throwable(response.message()))
                return
            } catch (e: JsonSyntaxException) {
                onMyFailure(
                    call,
                    Throwable(BaseApplication.getStringResource(R.string.error_other))
                )
                return
            }

        }
        onMySuccess(call, response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        when (t) {
            is ConnectException, is UnknownHostException -> {
                onMyFailure(
                    call,
                    Throwable(BaseApplication.getStringResource(R.string.message_connect_exception))
                )
                return
            }
            is SocketTimeoutException -> {
                onMyFailure(
                    call,
                    Throwable(BaseApplication.getStringResource(R.string.message_socket_timeout_exception))
                )
                return
            }
            else -> {
                if (!BaseApplication.checkNetwork()) {
                    onMyFailure(
                        call,
                        Throwable(BaseApplication.getStringResource(R.string.no_internet))
                    )
                    return
                }
            }
        }
        onMyFailure(call, t)
    }

    abstract fun onMySuccess(call: Call<T>?, response: Response<T>?)

    abstract fun onMyFailure(call: Call<T>?, t: Throwable?, errors: List<Error>? = null)
}

class ApiGetWayCallBack<Data>(private val data: MutableLiveData<ResponseResult<Data?>>? = null,private val context: Context? = null) :
    ApiRetrofitCallback<Data>() {
    private var dialog: Dialog? = null
    init {
        context?.let {
            dialog = getDialogWaiting(it)
            dialog?.show()
        }
        data?.value = ResponseResult.process()
    }

    override fun onMySuccess(
        call: Call<Data>?,
        response: Response<Data>?
    ) {
        val result = response?.body()
        dialog?.dismiss()
        data?.value = ResponseResult.success(result)
    }

    override fun onMyFailure(call: Call<Data>?, t: Throwable?, errors: List<Error>?) {
        dialog?.dismiss()
        data?.value = ResponseResult.error(t?.message, errors)
    }
}

class ApiGetWayCallBackWithModel<Data>(private val data: MutableLiveData<ResponseResult<Data?>>? = null,private val context: Context? = null) :
    ApiRetrofitCallback<ApiResponse<Data>>() {
    private var dialog: Dialog? = null
    init {
        context?.let {
            dialog = getDialogWaiting(it)
            dialog?.show()
        }
        data?.value = ResponseResult.process()
    }

    override fun onMySuccess(
        call: Call<ApiResponse<Data>>?,
        response: Response<ApiResponse<Data>>?
    ) {
        val result = response?.body()?.data
        dialog?.dismiss()
        data?.value = ResponseResult.success(result)
    }

    override fun onMyFailure(call: Call<ApiResponse<Data>>?, t: Throwable?, errors: List<Error>?) {
        dialog?.dismiss()
        data?.value = ResponseResult.error(t?.message, errors)
    }
}