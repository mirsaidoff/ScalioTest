package uz.mirsaidoff.scaliotest.model.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private var instance: Retrofit? = null

    private fun createInstance() {
        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newRequest = request.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "mirsaidoff")
                    .build()
                it.proceed(newRequest)
            }.build()

        instance = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    fun getInstance(): Retrofit {
        if (instance == null) createInstance()

        return instance!!
    }
}