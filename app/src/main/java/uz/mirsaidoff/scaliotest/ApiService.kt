package uz.mirsaidoff.scaliotest

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") login: String,
        @Query("per_page") perPage: Int,
        @Query("page") pageNumber: Int
    ): SearchResult
}