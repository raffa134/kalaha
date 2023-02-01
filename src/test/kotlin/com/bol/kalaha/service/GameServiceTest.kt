package com.bol.kalaha.service

import com.bol.kalaha.model.state.P1TurnState
import com.bol.kalaha.model.state.P2TurnState
import com.bol.kalaha.service.exception.InvalidPlayerException
import com.bol.kalaha.service.exception.MoveNotAllowedException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GameServiceTest {

    @Autowired
    private lateinit var gameService: GameService

    companion object {
        const val INVALID_PLAYER_ID = "SOME_PLAYER"
        const val PLAYER_1_ID = "PLAYER_1"
        const val PLAYER_2_ID = "PLAYER_2"
    }

    @Test
    fun `should throw exception when player is invalid`() {
        assertThrows<InvalidPlayerException> { gameService.play(INVALID_PLAYER_ID, 1) }
    }

    @Test
    fun `should throw exception when move is not allowed`() {
        val context = gameService.startGame()
        if (context.getState() is P1TurnState) {
            assertThrows<MoveNotAllowedException> { gameService.play(PLAYER_2_ID, 1) }
        } else if (context.getState() is P2TurnState) {
            assertThrows<MoveNotAllowedException> { gameService.play(PLAYER_1_ID, 1) }
        }
    }
}