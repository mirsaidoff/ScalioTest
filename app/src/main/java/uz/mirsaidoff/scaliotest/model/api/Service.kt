package uz.mirsaidoff.scaliotest.model.api

object Service {
    private var instance: ApiService? = null

    private fun createInstance() {
        instance = RestClient.getInstance()
            .create(ApiService::class.java)
    }

    fun getInstance(): ApiService {
        if (instance == null) createInstance()

        return instance!!
    }
}