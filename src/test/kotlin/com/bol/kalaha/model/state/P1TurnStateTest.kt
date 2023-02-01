package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class P1TurnStateTest {

    private lateinit var context: GameContext
    private lateinit var p1TurnState: P1TurnState

    @BeforeEach
    fun initializeContext() {
        context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.setState(context.getP1TurnState())
        p1TurnState = context.getState() as P1TurnState
    }

    @Test
    fun `start method should not produce any effect`() {
        p1TurnState.startGame()
        assertTrue(context.getState() is P1TurnState)
    }

    @Test
    fun `play method should produce a change in the state`() {
        p1TurnState.play(3)
        // Last seed does not end up in own store. P2 is next.
        assertTrue(context.getState() is P2TurnState)
    }

    @Test
    fun `endGame method should not produce any effect`() {
        p1TurnState.endGame()
        assertTrue(context.getState() is P1TurnState)
    }
}
