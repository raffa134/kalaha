package com.bol.kalaha.model

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.state.EndState
import com.bol.kalaha.model.state.GameState
import com.bol.kalaha.model.state.P1TurnState
import com.bol.kalaha.model.state.P2TurnState
import com.bol.kalaha.model.state.StartState
import com.bol.kalaha.service.exception.IllegalGameStateException
import org.slf4j.LoggerFactory

class GameContext(private var board: Board) {

    private val startState: StartState = StartState(this)
    private val p1TurnState: P1TurnState = P1TurnState(this)
    private val p2TurnState: P2TurnState = P2TurnState(this)
    private val endState: EndState = EndState(this)
    private var currentState: GameState = startState

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)

        const val START_STATE = "GAME_START"
        const val P1_TURN_STATE = "PLAYER_1_TURN"
        const val P2_TURN_STATE = "PLAYER_2_TURN"
        const val END_STATE = "GAME_END"
    }

    fun startGame() {
        currentState.startGame()
    }

    fun play(houseNumber: Int) {
        currentState.play(houseNumber)
    }

    fun endGame() {
        currentState.endGame()
    }

    fun getStartState() = startState
    fun getP1TurnState() = p1TurnState
    fun getP2TurnState() = p2TurnState
    fun getEndState() = endState

    fun getBoard() = board

    fun getState() = currentState
    fun setState(state: GameState) {
        this.currentState = state
        println(board.toString())
        logger.info("Next state: ${mapState(this.currentState)}")
    }

    private fun mapState(state: GameState) = when (state) {
        is StartState -> START_STATE
        is P1TurnState -> P1_TURN_STATE
        is P2TurnState -> P2_TURN_STATE
        is EndState -> END_STATE
        else -> throw IllegalGameStateException("State not allowed")
    }
}
