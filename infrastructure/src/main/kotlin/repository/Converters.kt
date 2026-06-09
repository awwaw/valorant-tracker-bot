package org.example.repository

import org.example.entities.Match
import org.example.entities.Player
import org.example.entities.PlayerRegion
import org.example.entities.PlayerSnapshot
import org.example.infrastructure.db.tables.records.MatchRecord
import org.example.infrastructure.db.tables.records.PlayerRecord
import org.example.infrastructure.db.tables.records.PlayerSnapshotRecord
import org.example.stats.PlayerMatchStats

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
        takenAt = takenAt!!.toInstant(),
        tierName = tierName!!,
        rr = rr!!,
        elo = elo!!,
        wins = wins!!,
        losses = losses!!,
    )
