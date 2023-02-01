package com.bol.kalaha.model.extensions

import com.bol.kalaha.model.GameContextDTO
import com.bol.kalaha.model.PitDTO
import com.bol.kalaha.model.PlayerBoardDTO
import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.state.EndState
import com.bol.kalaha.model.state.P1TurnState
import com.bol.kalaha.model.state.P2TurnState
import com.bol.kalaha.model.state.StartState
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.service.exception.IllegalGameStateException

fun GameContext.toGameContextDTO(): GameContextDTO {
    return GameContextDTO(
        listOf(
            getPlayerBoardDTO(Board.PLAYER_1_ID),
            getPlayerBoardDTO(Board.PLAYER_2_ID)
        ),
        nextState = getNextStateDTO()
    )
}

private fun GameContext.getPlayerBoardDTO(playerID: String) =
    PlayerBoardDTO(
        playerID,
        getBoard().houses[playerID]?.map { PitDTO(it.count()) },
        PitDTO(getBoard().stores[playerID]?.count())
    )

private fun GameContext.getNextStateDTO(): String =
    when (getState()) {
        is StartState -> GameContext.START_STATE
        is P1TurnState -> GameContext.P1_TURN_STATE
        is P2TurnState -> GameContext.P2_TURN_STATE
        is EndState -> GameContext.END_STATE
        else -> throw IllegalGameStateException("State not allowed")
    }
