package com.vinsmart.officeapp.apigateway.core.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.vinsmart.commons.utils.SingleLiveEvent

// File UnAuthorizedEvent
// @project OfficeApp
// @author minhhoang on 18-03-2021
class AuthorizedEvent private constructor() {
    val authorized = SingleLiveEvent<AuthorizedEventType>()

    companion object {
        private var INSTANCE: AuthorizedEvent? = null

        @JvmStatic
        val instance by lazy {
            if (INSTANCE == null) INSTANCE = AuthorizedEvent()
            INSTANCE!!
        }
    }

    fun setAuthorized(type: AuthorizedEventType) {
        authorized.postValue(type)
    }
}

enum class AuthorizedEventType {
    AUTHORIZED,
    UN_AUTHORIZED
}