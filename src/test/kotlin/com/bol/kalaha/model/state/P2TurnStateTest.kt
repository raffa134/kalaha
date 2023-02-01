package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class P2TurnStateTest {

    private lateinit var context: GameContext
    private lateinit var p2TurnState: P2TurnState

    @BeforeEach
    fun initializeContext() {
        context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.setState(context.getP2TurnState())
        p2TurnState = context.getState() as P2TurnState
    }

    @Test
    fun `start method should not produce any effect`() {
        p2TurnState.startGame()
        Assertions.assertTrue(context.getState() is P2TurnState)
    }

    @Test
    fun `play method should produce a change in the state`() {
        p2TurnState.play(3)
        // Last seed does not end up in own store. P1 is next.
        Assertions.assertTrue(context.getState() is P1TurnState)
    }

    @Test
    fun `endGame method should not produce any effect`() {
        p2TurnState.endGame()
        Assertions.assertTrue(context.getState() is P2TurnState)
    }
}