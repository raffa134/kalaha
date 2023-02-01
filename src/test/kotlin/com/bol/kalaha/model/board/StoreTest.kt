package com.bol.kalaha.model.board

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StoreTest {

    @Test
    fun `should return the number of seeds of the store`() {
        val store = Store()
        assertEquals(store.count(), 0)
    }
    @Test
    fun `should add one seed to the total seeds of the store`() {
        val store = Store()
        store.add()
        assertEquals(store.count(), 1)
    }

    @Test
    fun `should add seeds to the total seeds of the store`() {
        val store = Store()
        val seedsToAdd = 10
        store.add(seedsToAdd)
        assertEquals(store.count(), seedsToAdd)
    }
}
