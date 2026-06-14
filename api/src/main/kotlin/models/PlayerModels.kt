package org.example.models

import org.example.entities.Player
import org.example.entities.PlayerSnapshot
import kotlin.time.Instant

data class AddPlayerRequest(
    val name: String,
    val tag: String,
    val region: String,
)

data class PlayerResponse(
    val id: Long,
    val name: String,
    val tag: String,
    val region: String,
    val wins: Int,
    val losses: Int,
) {
    companion object {
        fun from(player: Player): PlayerResponse =
            PlayerResponse(
                id = player.id,
                name = player.name,
                tag = player.tag,
                region = player.region.name,
                wins = player.wins,
                losses = player.losses,
            )
    }
}

data class PlayerSnapshotResponse(
    val tierName: String,
    val rr: Int,
    val elo: Int,
    val wins: Int,
    val losses: Int,
    val takenAt: Instant,
) {
    companion object {
        fun from(snapshot: PlayerSnapshot): PlayerSnapshotResponse =
            PlayerSnapshotResponse(
                tierName = snapshot.tierName,
                rr = snapshot.rr,
                elo = snapshot.elo,
                wins = snapshot.wins,
                losses = snapshot.losses,
                takenAt = snapshot.takenAt,
            )
    }
}

data class CheckResponse(
    val status: String,
    val wins: Int? = null,
    val losses: Int? = null,
    val rrDiff: Int? = null,
    val eloDiff: Int? = null,
)
