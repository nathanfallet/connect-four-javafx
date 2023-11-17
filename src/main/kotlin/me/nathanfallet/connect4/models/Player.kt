package me.nathanfallet.connect4.models

enum class Player {

    RED,
    YELLOW;

    val opponent: Player
        get() = when (this) {
            RED -> YELLOW
            YELLOW -> RED
        }

}
