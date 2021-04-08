package com.vinsmart.officeapp.apigateway.core.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

// File UnAuthorizedEvent
// @project OfficeApp
// @author minhhoang on 18-03-2021
class SendBodyEvent private constructor() {
    private val progress = MutableLiveData<SendBodyType>()

    companion object {
        private var INSTANCE: SendBodyEvent? = null

        @JvmStatic
        val instance by lazy {
            if (INSTANCE == null) INSTANCE = SendBodyEvent()
            INSTANCE!!
        }
    }

    fun setProgress(type: SendBodyType) {
        progress.postValue(type)
    }

    fun getProgress() = progress.distinctUntilChanged()
}

enum class SendBodyType {
    IN_PROGRESS,
    FINISH
}