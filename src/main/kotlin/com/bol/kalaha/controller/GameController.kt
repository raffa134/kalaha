package com.bol.kalaha.controller

import com.bol.kalaha.model.GameContextDTO
import com.bol.kalaha.model.extensions.toGameContextDTO
import com.bol.kalaha.service.GameService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    val gameService: GameService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    @PostMapping("/startGame")
    fun startGame(): GameContextDTO {
        logger.info("Request to start a new game")
        return gameService.startGame().toGameContextDTO()
    }

    @PostMapping("/play/{player}/{houseNumber}")
    fun play(@PathVariable player: String, @PathVariable houseNumber: Int): GameContextDTO {
        logger.info("Request to play from player $player on house $houseNumber")
        return gameService.play(player, houseNumber).toGameContextDTO()
    }

    @PostMapping("/endGame")
    fun endGame(): GameContextDTO {
        logger.info("Request to end the game")
        return gameService.endGame().toGameContextDTO()
    }
}
