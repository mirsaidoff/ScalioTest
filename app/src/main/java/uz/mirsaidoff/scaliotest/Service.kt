package uz.mirsaidoff.scaliotest

object Service {
    private var instance: ApiService? = null

    private fun createInstance() {
        this.instance = RestClient
            .getInstance()
            .create(ApiService::class.java)
    }

    fun getInstance(): ApiService {
        if (this.instance == null) createInstance()

        return this.instance!!
    }
}