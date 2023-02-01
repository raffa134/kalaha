package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StartStateTest {

    private lateinit var context: GameContext
    private lateinit var startState: StartState

    @BeforeEach
    fun initializeContext() {
        context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        startState = context.getState() as StartState
    }

    @Test
    fun `start method should pick a random player and set the next state correctly`() {

        var p1StartsAtLeastOnce = false
        var p2StartsAtLeastOnce = false

        while (!(p1StartsAtLeastOnce && p2StartsAtLeastOnce)) {
            startState.startGame()
            assertTrue(context.getState() !is EndState)
            if (context.getState() is P1TurnState) p1StartsAtLeastOnce = true
            if (context.getState() is P2TurnState) p2StartsAtLeastOnce = true
        }
    }

    @Test
    fun `play method should not produce any effect`() {
        startState.play(1)
        assertTrue(context.getState() is StartState)
    }

    @Test
    fun `endGame method should not produce any effect`() {
        startState.endGame()
        assertTrue(context.getState() is StartState)
    }
}
