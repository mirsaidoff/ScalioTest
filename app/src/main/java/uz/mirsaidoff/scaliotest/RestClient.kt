package uz.mirsaidoff.scaliotest

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private var instance: Retrofit? = null

    private fun createInstance() {
        instance = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    fun getInstance(): Retrofit {
        if (this.instance == null) createInstance()

        return this.instance!!
    }
}