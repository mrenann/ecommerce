package br.mrenann.core.domain.model

data class Address(
    val id: String = "",
    val street: String = "",
    val number: Int = 0,
    val district: String = "",
    val complement: String? = "",
    val city: String = "",
    val state: String = "",
    val code: String = "",
    val type: String = "",
    val main: Boolean = false
)
