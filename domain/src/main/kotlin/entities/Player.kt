package org.example.entities

import java.util.UUID

enum class PlayerRegion {
    EU,
    NA,
    LATAM,
    BR,
    AP,
    KR,
}

data class Player(
    val id: Long,
    val puid: UUID,
    val name: String,
    val tag: String,
    val region: PlayerRegion,
    val wins: Int = 0,
    val losses: Int = 0,
)
