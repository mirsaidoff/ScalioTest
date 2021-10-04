package uz.mirsaidoff.scaliotest.model

import com.google.gson.annotations.SerializedName
import uz.mirsaidoff.scaliotest.model.User

class SearchResult(
    @SerializedName("message") val message: String?,         //error message
    @SerializedName("total_count") val totalCount: Long?,
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<User>?
) {
    fun isSuccessful(): Boolean = message == null
}