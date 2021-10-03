package uz.mirsaidoff.scaliotest

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers(
        @Query("q") login: String,
        @Query("per_page") perPage: Int,
        @Query("page_number") pageNumber: Int
    ): Single<SearchResult>
}