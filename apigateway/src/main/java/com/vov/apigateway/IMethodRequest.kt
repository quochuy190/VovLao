package com.vinsmart.officeapp.apigateway

interface IMethodRequest {
    fun post()
    fun get()
    fun push()
    fun delete()
}