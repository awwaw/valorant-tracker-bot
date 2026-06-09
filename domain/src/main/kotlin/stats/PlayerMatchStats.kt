package org.example.stats

data class PlayerMatchStats(
    val agent: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val won: Boolean,
)
