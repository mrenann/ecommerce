package br.mrenann.profile.presentation.components.flipCard

sealed class CardFace(val angle: Float) {
    object Front : CardFace(angle = 0f)
    object Back : CardFace(angle = 180f)

    fun flip(): CardFace {
        return when (this) {
            is Back -> Front
            is Front -> Back
        }
    }
}