package org.example.service

import org.example.client.HenrikApiClient
import org.example.entities.Player
import org.example.entities.PlayerSnapshot
import org.example.repository.PlayerRepository
import org.example.repository.PlayerSnapshotRepository
import org.springframework.stereotype.Service
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

@Service
class SessionService(
    private val playerRepository: PlayerRepository,
    private val playerSnapshotRepository: PlayerSnapshotRepository,
    private val henrikApiClient: HenrikApiClient,
) {
    companion object {
        val STALE_THRESHOLD = 3.hours
    }

    fun checkPlayer(playerId: Long): CheckResult {
        val player = playerRepository.findById(playerId) ?: error("Player with id $playerId does not exist")
        val snapshot =
            playerSnapshotRepository.findLatestByPlayerId(playerId)
                ?: error("Player Snapshot for player with id $playerId does not exist")
        if (checkForStale(snapshot)) {
            return resync(player)
        }

        return computeSessionDiff(player, snapshot)
    }

    private fun checkForStale(snapshot: PlayerSnapshot): Boolean {
        val now = Clock.System.now()
        return snapshot.lastMatchPlayedAt == null
                || (now - snapshot.takenAt) > STALE_THRESHOLD
    }

    private fun resync(player: Player): CheckResult {
        val mmr = henrikApiClient.getMmr(player.region.name.lowercase(), player.name, player.tag).data
        val wins = mmr.seasonal.sumOf { it.wins }
        val losses = mmr.seasonal.sumOf { it.games - it.wins }

        val matches =
            henrikApiClient
                .getStoredMatches(
                    region = player.region.name.lowercase(),
                    name = player.name,
                    tag = player.tag,
                    mode = "competitive",
                    size = 1,
                ).data

        val lastMatchPlayedAt =
            matches
                .firstOrNull()
                ?.meta
                ?.startedAt
                ?.let { Instant.parse(it) }

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
                lastMatchPlayedAt = lastMatchPlayedAt,
            )
        playerSnapshotRepository.save(snapshot, player.id)

        return CheckResult.Resynced(snapshot)
    }

    private fun computeSessionDiff(
        player: Player,
        snapshot: PlayerSnapshot,
    ): CheckResult {
        val matches =
            henrikApiClient
                .getStoredMatches(
                    region = player.region.name.lowercase(),
                    name = player.name,
                    tag = player.tag,
                    mode = "competitive",
                ).data

        val sessionMatches =
            matches.filter { match ->
                val playedAt = Instant.parse(match.meta.startedAt)
                playedAt > snapshot.lastMatchPlayedAt!!
            }

        if (sessionMatches.isEmpty()) {
            playerSnapshotRepository.updateTakenAt(snapshot.id, Clock.System.now())
            return CheckResult.NoNewMatches
        }

        val wins =
            sessionMatches.count { match ->
                val playerTeam = match.stats.team
                val redRounds = match.teams.red
                val blueRounds = match.teams.blue
                (playerTeam == "Red" && redRounds > blueRounds) ||
                    (playerTeam == "Blue" && blueRounds > redRounds)
            }
        val losses = sessionMatches.size - wins

        val lastMatch = sessionMatches.maxBy { Instant.parse(it.meta.startedAt) }
        val lastMatchPlayedAt = Instant.parse(lastMatch.meta.startedAt)

        val mmr = henrikApiClient.getMmr(player.region.name.lowercase(), player.name, player.tag).data

        val newSnapshot =
            PlayerSnapshot(
                id = 0,
                playerId = player.id,
                takenAt = Clock.System.now(),
                tierName = mmr.current.tier.name,
                rr = mmr.current.rr,
                elo = mmr.current.elo,
                wins = snapshot.wins + wins,
                losses = snapshot.losses + losses,
                lastMatchPlayedAt = lastMatchPlayedAt,
            )
        playerSnapshotRepository.save(newSnapshot, player.id)

        return CheckResult.SessionDiff(
            wins = wins,
            losses = losses,
            rrDiff = mmr.current.rr - snapshot.rr,
            eloDiff = mmr.current.elo - snapshot.elo,
            snapshot = newSnapshot,
        )
    }
}

sealed class CheckResult {
    data class SessionDiff(
        val wins: Int,
        val losses: Int,
        val rrDiff: Int,
        val eloDiff: Int,
        val snapshot: PlayerSnapshot,
    ) : CheckResult()

    data class Resynced(
        val snapshot: PlayerSnapshot,
    ) : CheckResult()

    data object NoNewMatches : CheckResult()
}
