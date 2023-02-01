package com.bol.kalaha.model.board

import com.bol.kalaha.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HouseTest {

    @Test
    fun `should add one seed to the total seeds of the house`() {
        val house = House(TestUtils.NUMBER_OF_SEEDS)
        house.add()
        assertEquals(house.count(), TestUtils.NUMBER_OF_SEEDS + 1)
    }

    @Test
    fun `should return the number of seeds of the house`() {
        val house = House(TestUtils.NUMBER_OF_SEEDS)
        assertEquals(house.count(), TestUtils.NUMBER_OF_SEEDS)
    }

    @Test
    fun `should empty house and return the number of seeds`() {
        val house = House(TestUtils.NUMBER_OF_SEEDS)
        val seeds = house.empty()

        assertEquals(seeds, TestUtils.NUMBER_OF_SEEDS)
        assertEquals(house.count(), 0)
    }
}
