package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EndStateTest {

    private lateinit var context: GameContext
    private lateinit var endState: EndState

    @BeforeEach
    fun initializeContext() {
        context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.setState(context.getEndState())
        endState = context.getState() as EndState
    }

    @Test
    fun `start method should not produce any effect`() {
        endState.startGame()
        assertTrue(context.getState() is EndState)
    }

    @Test
    fun `play method should not produce any effect`() {
        endState.play(1)
        assertTrue(context.getState() is EndState)
    }

    @Test
    fun `endGame method should produce a change in the state`() {
        endState.endGame()
        assertTrue(context.getState() is StartState)
    }
}
