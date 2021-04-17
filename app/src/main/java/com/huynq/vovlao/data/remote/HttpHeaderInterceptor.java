package com.huynq.vovlao.data.remote;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("accept", "*/*")
                .build();
        return chain.proceed(request);
    }
}
