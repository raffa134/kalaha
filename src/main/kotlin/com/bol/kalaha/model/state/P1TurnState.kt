package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import org.slf4j.LoggerFactory

class P1TurnState(context: GameContext) : GameState(context) {

    private val currentContext: GameContext = context

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    override fun startGame() {
        logger.info("Nothing to do in play state")
    }

    override fun play(houseNumber: Int) {
        val lastSeedInOwnStore = sow(Board.PLAYER_1_ID, Board.PLAYER_2_ID, houseNumber)

        if (isGameOver()) {
            currentContext.setState(currentContext.getEndState())
        } else {
            if (lastSeedInOwnStore) {
                currentContext.setState(currentContext.getP1TurnState())
            } else {
                currentContext.setState(currentContext.getP2TurnState())
            }
        }
    }

    override fun endGame() {
        logger.info("Nothing to do in end state")
    }
}
