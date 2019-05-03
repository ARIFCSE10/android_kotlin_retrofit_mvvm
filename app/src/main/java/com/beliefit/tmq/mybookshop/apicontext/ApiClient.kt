package com.beliefit.tmq.mybookshop.apicontext


import com.beliefit.tmq.mybookshop.BuildConfig
import com.beliefit.tmq.mybookshop.Interface.ApiEndpointInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private var BASE_URL: String? = "https://booksdemo.herokuapp.com"

    fun getApiService(): ApiEndpointInterface {

        val logging = HttpLoggingInterceptor()
            .apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            }

        // add logging interceptors …
        val httpClient = OkHttpClient.Builder().apply { addInterceptor(logging) }

        val retrofit = Retrofit.Builder().apply {
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
            client(httpClient.build())
            baseUrl(BASE_URL)
        }

        return retrofit.build().create(ApiEndpointInterface::class.java)
    }
}