package org.example.controller

import org.example.models.CheckResponse
import org.example.service.CheckResult
import org.example.service.SessionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/players")
class PlayerCheckController(
    private val sessionService: SessionService,
) {
    @PostMapping("/{id}/check")
    fun check(@PathVariable id: Long): ResponseEntity<CheckResponse> {
        return when (val result = sessionService.checkPlayer(id)) {
            is CheckResult.SessionDiff -> ResponseEntity.ok(
                CheckResponse(
                    status = "session_diff",
                    wins = result.wins,
                    losses = result.losses,
                    rrDiff = result.rrDiff,
                    eloDiff = result.eloDiff,
                )
            )
            is CheckResult.Resynced -> ResponseEntity.ok(
                CheckResponse(status = "resynced")
            )
            CheckResult.NoNewMatches -> ResponseEntity.ok(
                CheckResponse(status = "no_new_matches")
            )
        }
    }
}