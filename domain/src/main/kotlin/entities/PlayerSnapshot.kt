package org.example.entities

import kotlin.time.Instant

data class PlayerSnapshot(
    val id: Long,
    val playerId: Long,
    val takenAt: Instant,
    val tierName: String,
    val rr: Int,
    val elo: Int,
    val wins: Int,
    val losses: Int,
)
