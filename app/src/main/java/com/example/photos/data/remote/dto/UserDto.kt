package com.example.photos.data.remote.dto

import com.example.photos.domain.model.User
import com.google.gson.annotations.SerializedName


data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        username = username
    )
}