package uz.mirsaidoff.scaliotest.model.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.mirsaidoff.scaliotest.model.SearchResult

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") login: String,
        @Query("per_page") perPage: Int,
        @Query("page") pageNumber: Int
    ): SearchResult
}