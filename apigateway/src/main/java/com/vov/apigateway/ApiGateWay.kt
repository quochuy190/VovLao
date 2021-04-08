package com.vinsmart.officeapp.apigateway

import android.content.Context
import java.lang.RuntimeException

class ApiGateWay {
    private lateinit var serviceGeneratorDefault: ServiceGenerator
    private lateinit var appContext: Context

    companion object {
        private var INSTANCE: ApiGateWay? = null

        fun getInstance(): ApiGateWay {
            INSTANCE?.let {
                return it
            }

            INSTANCE = ApiGateWay()
            return INSTANCE!!
        }
    }

    fun prepare(context: Context): ApiGateWay {
        appContext = context
        serviceGeneratorDefault = ServiceGenerator.Builder(context).build()
        return this
    }

    fun <T> createService(service: Class<T>, baseUrl: String? = null): T {
        if(baseUrl == null) {
            if (!this::serviceGeneratorDefault.isInitialized) {
                throw RuntimeException(
                        "ApiGateWay.createService: must call " +
                                "ApiGateWay.prepare(context: Context) first"
                )
            }
            return serviceGeneratorDefault.createService(service)
        } else {
            if (!this::appContext.isInitialized) {
                throw RuntimeException(
                        "ApiGateWay.createServiceWithUrl: must call " +
                                "ApiGateWay.prepare(context: Context) first"
                )
            }
            return ServiceGenerator.Builder(appContext)
                    .setBaseUrl(baseUrl)
                    .build().createService(service)
        }
    }

    fun changeApiBaseUrl(baseUrl: String) {
        if (!this::serviceGeneratorDefault.isInitialized) {
            throw RuntimeException(
                "ApiGateWay.changeApiBaseUrl: must call " +
                        "ApiGateWay.prepare(context: Context) first"
            )
        }
        serviceGeneratorDefault.changeApiBaseUrl(baseUrl)
    }
}