package com.bol.kalaha.model.board

import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `should create board with the right setup`() {
        val board = Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS)

        // Each player has 6 houses
        board.houses.values.forEach { assertEquals(it.size, TestUtils.NUMBER_OF_HOUSES) }

        // Each house has 6 seeds
        board.houses.values.flatten().forEach { assertEquals(it.count(), TestUtils.NUMBER_OF_SEEDS) }

        // Each player starts with 0 seeds in his store
        board.stores.values.forEach { assertEquals(it.count(), 0) }
    }
}
