package org.example.stats

import org.example.entities.Match

data class SessionSummary(
    val matches: List<Match>,
    val winrate: Double,
    val avgKills: Double,
    val avgDeaths: Double,
    val avgAssists: Double,
)
