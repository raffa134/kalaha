package com.bol.kalaha.service

import com.bol.kalaha.model.GameContext
import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.state.P1TurnState
import com.bol.kalaha.model.state.P2TurnState
import com.bol.kalaha.service.exception.InvalidPlayerException
import com.bol.kalaha.service.exception.MoveNotAllowedException
import org.springframework.stereotype.Service

@Service
class GameService {

    private lateinit var context: GameContext

    companion object {
        const val NUMBER_OF_HOUSES = 6
        const val NUMBER_OF_SEEDS = 6
    }

    fun startGame(): GameContext {
        context = GameContext(Board(NUMBER_OF_HOUSES, NUMBER_OF_SEEDS))
        context.startGame()
        return context
    }

    fun play(playerID: String, houseNumber: Int): GameContext {
        check(isValidPlayer(playerID)) { throw InvalidPlayerException("Player is not valid") }
        check(isValidMove(playerID)) { throw MoveNotAllowedException("It's not $playerID's turn!") }

        context.play(houseNumber)
        return context
    }

    fun endGame(): GameContext {
        context.endGame()
        return context
    }

    private fun isValidPlayer(playerID: String) =
        playerID == Board.PLAYER_1_ID || playerID == Board.PLAYER_2_ID

    private fun isValidMove(playerID: String) = (
        playerID == Board.PLAYER_1_ID && context.getState() is P1TurnState
        ) || (
        playerID == Board.PLAYER_2_ID && context.getState() is P2TurnState
        )
}
