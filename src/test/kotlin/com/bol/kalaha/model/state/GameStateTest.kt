package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.service.exception.MoveNotAllowedException
import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameStateTest {

    @Test
    fun `should set initial state to START`() {
        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        assertTrue(context.getState() is StartState)
    }

    @Test
    fun `should throw exception when playing an invalid house number`() {
        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.startGame()

        assertThrows<MoveNotAllowedException> { context.play(TestUtils.NUMBER_OF_HOUSES + 1) }
    }

    @Test
    fun `should throw exception when trying to sow from a house with no seeds`() {
        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.startGame()
        // Initial player board setup
        // (6) (6) (6) (6) (6) (6) [0]

        val houseNumber = 1
        context.play(houseNumber)
        // Player board setup after sowing from house 1
        // (0) (7) (7) (7) (7) (7) [1]
        // Player can play another turn since last seed ended up in own store
        assertThrows<MoveNotAllowedException> { context.play(houseNumber) }
    }

    @Test
    fun `should sow seeds and set next turn correctly`() {
        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, 2))
        context.setState(context.getP1TurnState())

        val initialBoardSetup = """
            [0] (2) (2) (2) (2) (2) (2)
                (2) (2) (2) (2) (2) (2) [0]
        """.trimIndent()

        assertEquals(initialBoardSetup, context.getBoard().toString())

        context.play(5)

        // Board setup after player 1 sows from house 5
        var updatedBoard = """
            [1] (3) (0) (2) (2) (2) (2)
                (2) (2) (2) (2) (2) (2) [0]
        """.trimIndent()

        assertEquals(updatedBoard, context.getBoard().toString())

        // P1 can play another turn since last seed ended up in own store
        assertTrue(context.getState() is P1TurnState)

        context.play(1)

        // Board setup after player 1 sows from house 1
        updatedBoard = """
            [1] (3) (0) (2) (3) (3) (0)
                (2) (2) (2) (2) (2) (2) [0]
        """.trimIndent()

        assertEquals(updatedBoard, context.getBoard().toString())

        // Assert that player 2 is next
        assertTrue(context.getState() is P2TurnState)
    }

    @Test
    fun `should sow seeds and only add to own store`() {

        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, 10))
        context.setState(context.getP1TurnState())

        val initialBoardSetup = """
            [0] (10) (10) (10) (10) (10) (10)
                (10) (10) (10) (10) (10) (10) [0]
        """.trimIndent()
        assertEquals(initialBoardSetup, context.getBoard().toString())

        context.play(4)

        // Board setup after player 1 sows from house 4
        val updatedBoard = """
            [1] (11) (11) (0) (10) (10) (11)
                (11) (11) (11) (11) (11) (11) [0]
        """.trimIndent()
        assertEquals(updatedBoard, context.getBoard().toString())
    }

    @Test
    fun `should steal seeds from opponent`() {
        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, 3))
        context.setState(context.getP1TurnState())

        val initialBoardSetup = """
            [0] (3) (3) (3) (3) (3) (3)
                (3) (3) (3) (3) (3) (3) [0]
        """.trimIndent()

        assertEquals(initialBoardSetup, context.getBoard().toString())

        context.play(4)

        // Board setup after player 1 sows from house 4
        val updatedBoard = """
            [1] (4) (4) (0) (3) (3) (3)
                (3) (3) (3) (3) (3) (3) [0]
        """.trimIndent()

        assertEquals(updatedBoard, context.getBoard().toString())

        context.play(1)

        // Board setup after player 1 sows from house 1
        val boardWithStolenSeeds = """
            [5] (4) (4) (0) (4) (4) (0)
                (3) (3) (0) (3) (3) (3) [0]
        """.trimIndent()

        assertEquals(boardWithStolenSeeds, context.getBoard().toString())
    }
}
