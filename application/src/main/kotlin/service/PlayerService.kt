package org.example.service

import kotlinx.coroutines.runBlocking
import org.example.client.HenrikApiClient
import org.example.entities.Player
import org.example.entities.PlayerRegion
import org.example.entities.PlayerSnapshot
import org.example.repository.PlayerRepository
import org.example.repository.PlayerSnapshotRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.time.Clock

@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerSnapshotRepository: PlayerSnapshotRepository,
    private val henrikApiClient: HenrikApiClient,
) {
    @Transactional
    fun addPlayer(
        name: String,
        tag: String,
        region: PlayerRegion,
    ): Player {
        playerRepository.findByNameAndTag(name, tag)?.let {
            error("Player $name#$tag already exists")
        }

        val mmr = henrikApiClient.getMmr(region.name.lowercase(), name, tag).data

        val wins = mmr.seasonal.sumOf { it.wins }
        val losses = mmr.seasonal.sumOf { it.games - it.wins }

        val player =
            playerRepository.save(
                Player(
                    id = 0,
                    puid = mmr.account.puuid,
                    name = name,
                    tag = tag,
                    region = region,
                    wins = wins,
                    losses = losses,
                ),
            )

        val snapshot =
            PlayerSnapshot(
                id = 0,
                playerId = player.id,
                takenAt = Clock.System.now(),
                tierName = mmr.current.tier.name,
                rr = mmr.current.rr,
                elo = mmr.current.elo,
                wins = wins,
                losses = losses,
            )
        playerSnapshotRepository.save(snapshot, player.id)

        return player
    }

    fun getLatestSnapshot(playerId: Long): PlayerSnapshot =
        playerSnapshotRepository.findLatestByPlayerId(playerId)
            ?: error("No snapshot found for player $playerId")
}
