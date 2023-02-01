package com.bol.kalaha.model.state

import com.bol.kalaha.model.GameContext
import org.slf4j.LoggerFactory
import kotlin.random.Random

class StartState(context: GameContext) : GameState(context) {

    private val currentContext: GameContext = context

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    override fun startGame() {
        val playerFirstTurn = Random.nextInt(1, 3)
        val firstTurn = if (playerFirstTurn == 1) currentContext.getP1TurnState() else currentContext.getP2TurnState()
        currentContext.setState(firstTurn)
    }

    override fun play(houseNumber: Int) {
        logger.info("Nothing to do in start state")
    }

    override fun endGame() {
        logger.info("Nothing to do in start state")
    }
}
