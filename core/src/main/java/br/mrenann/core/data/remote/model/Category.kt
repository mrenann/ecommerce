package br.mrenann.core.data.remote.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = ""
)