package org.example.entities

import org.example.stats.PlayerMatchStats
import java.time.Instant

data class Match(
    val id: String,
    val playedAt: Instant,
    val map: String,
    val queueId: String,
    val stats: PlayerMatchStats,
)
