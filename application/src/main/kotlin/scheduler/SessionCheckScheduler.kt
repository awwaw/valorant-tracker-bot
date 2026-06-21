package org.example.scheduler

import org.example.repository.PlayerRepository
import org.example.service.SessionService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SessionCheckScheduler(
    private val playerRepository: PlayerRepository,
    private val sessionService: SessionService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "0 0 * * * *")
    fun checkAllPlayers() {
        val players = playerRepository.findAll() // Тут бы пагинацию по хорошему
        logger.info("Running session check for ${players.size} players")

        players.forEach { player ->
            runCatching {
                sessionService.checkPlayer(player.id)
            }.onFailure { e ->
                logger.error("Failed to check player ${player.id} (name=${player.name}, tag=${player.tag})", e)
            }
        }
    }
}
