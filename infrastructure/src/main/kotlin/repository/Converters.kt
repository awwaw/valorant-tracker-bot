package org.example.repository

import generated.db.tables.records.MatchRecord
import generated.db.tables.records.PlayerRecord
import generated.db.tables.records.PlayerSnapshotRecord
import org.example.entities.Match
import org.example.entities.Player
import org.example.entities.PlayerRegion
import org.example.entities.PlayerSnapshot
import org.example.stats.PlayerMatchStats
import kotlin.time.toKotlinInstant

fun PlayerRecord.toModel(): Player =
    Player(
        id = id!!,
        puid = puuid!!,
        name = name!!,
        tag = tag!!,
        region = PlayerRegion.from(region!!),
        wins = wins!!,
        losses = losses!!,
    )

fun MatchRecord.toModel(): Match =
    Match(
        id = id!!,
        playedAt = playedAt!!.toInstant(),
        map = map!!,
        queueId = queueId!!,
        stats =
            PlayerMatchStats(
                agent = agent!!,
                kills = kills!!,
                deaths = deaths!!,
                assists = assists!!,
                won = won!!,
            ),
    )

fun PlayerSnapshotRecord.toModel(): PlayerSnapshot =
    PlayerSnapshot(
        id = id!!,
        playerId = playerId!!,
        takenAt = takenAt!!.toInstant().toKotlinInstant(),
        tierName = tierName!!,
        rr = rr!!,
        elo = elo!!,
        wins = wins!!,
        losses = losses!!,
        lastMatchPlayedAt = lastMatchPlayedAt?.toInstant()?.toKotlinInstant(),
    )
