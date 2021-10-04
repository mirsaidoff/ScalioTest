package uz.mirsaidoff.scaliotest.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("login") val login: String,
    @SerializedName("type") val type: String
) {
    override fun toString(): String {
        return "[$login, $avatarUrl, $type]\n"
    }
}