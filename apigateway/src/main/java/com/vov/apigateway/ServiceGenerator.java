package com.vov.apigateway;

import android.content.Context;

import com.vov.apigateway.core.CommonUtils;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ServiceGenerator {
    private Retrofit retrofit;
    private Retrofit.Builder builder;

    private ServiceGenerator(Retrofit.Builder builder) {
        this.builder = builder;
        this.retrofit = builder.build();
    }

    private void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    private void setBuilder(Retrofit.Builder builder) {
        this.builder = builder;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Retrofit.Builder getBuilder() {
        return builder;
    }

    public void changeApiBaseUrl(String newApiBaseUrl) {
        Retrofit.Builder builderNewApi = builder.baseUrl(newApiBaseUrl);
        retrofit = builderNewApi.build();
    }

    public <T> T createService(Class<T> service) {
        return getRetrofit().create(service);
    }

    public static class Builder {

        private String baseUrl;
        private Retrofit.Builder builder;
        private OkHttpClient.Builder httpClient;
        private final Context context;
       // private final TechnoParkSharedPreferences sharedPreferences;

//        private TechnoParkSharedPreferences getSharedPreferences() {
//            return sharedPreferences;
//        }

        private Context getContext() {
            return context;
        }

        public Builder(Context context) {
            this.baseUrl = BuildConfig.BASE_URL;
            this.builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create());
            this.httpClient = new OkHttpClient.Builder();
            this.context = context;
            //sharedPreferences = TechnoParkSharedPreferences.getInstance(context);
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setRetrofitBuilder(Retrofit.Builder builder) {
            this.builder = builder;
            return this;
        }

        public Builder setHttpClient(OkHttpClient.Builder httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder setInterceptor(Interceptor interceptor) {
            httpClient.addInterceptor(interceptor);
            return this;
        }

        public ServiceGenerator build() {
            Retrofit.Builder builderConfig = builder.baseUrl(baseUrl);
            setInitInterceptor();
            SSLSocketFactory sslSocketFactory = CommonUtils.getSSLSocketFactory();
            if (sslSocketFactory != null) {
                httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) CommonUtils.trustAllCerts[0]);
            }
            builderConfig.client(httpClient.build());
            return new ServiceGenerator(builderConfig);
        }

        private void setInitInterceptor() {
          //  httpClient.addNetworkInterceptor(new HeaderInterceptor(context, sharedPreferences));
          //  httpClient.addInterceptor(new AuthorizedInterceptor(sharedPreferences));
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
            }
            httpClient.addInterceptor(loggingInterceptor);
        }
    }
}
