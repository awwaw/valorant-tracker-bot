package org.example.controller

import org.example.entities.PlayerRegion
import org.example.models.AddPlayerRequest
import org.example.models.PlayerResponse
import org.example.models.PlayerSnapshotResponse
import org.example.service.PlayerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/players")
class PlayerController(
    private val playerService: PlayerService,
) {
    @PostMapping
    fun addPlayer(
        @RequestBody request: AddPlayerRequest,
    ): PlayerResponse {
        val player =
            playerService.addPlayer(
                name = request.name,
                tag = request.tag,
                region = PlayerRegion.from(request.region),
            )
        return PlayerResponse.from(player)
    }

    @GetMapping("/{id}/snapshot")
    fun getLatestSnapshot(
        @PathVariable id: Long,
    ): PlayerSnapshotResponse {
        val snapshot = playerService.getLatestSnapshot(id)
        return PlayerSnapshotResponse.from(snapshot)
    }
}
