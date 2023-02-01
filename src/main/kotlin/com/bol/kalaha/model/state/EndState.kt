package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import org.slf4j.LoggerFactory

class EndState(context: GameContext) : GameState(context) {

    private val currentContext: GameContext = context

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    override fun startGame() {
        logger.info("Nothing to do in end state")
    }

    override fun play(houseNumber: Int) {
        logger.info("Nothing to do in end state")
    }

    override fun endGame() {
        collectLastSeeds()
        currentContext.setState(currentContext.getStartState())
    }

    private fun collectLastSeeds() {
        val board = currentContext.getBoard()
        val seedsP1 = board.houses[Board.PLAYER_1_ID]!!.sumOf { it.empty() }
        val seedsP2 = board.houses[Board.PLAYER_2_ID]!!.sumOf { it.empty() }
        board.stores[Board.PLAYER_1_ID]!!.add(seedsP1)
        board.stores[Board.PLAYER_2_ID]!!.add(seedsP2)
    }
}
