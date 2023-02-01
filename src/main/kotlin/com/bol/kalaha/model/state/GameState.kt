package com.bol.kalaha.model.state

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.service.exception.MoveNotAllowedException

abstract class GameState(context: GameContext) {

    private val currentContext: GameContext = context

    abstract fun startGame()
    abstract fun play(houseNumber: Int)
    abstract fun endGame()

    protected fun sow(playerID: String, opponentPlayerID: String, houseNumber: Int): Boolean {
        val board = currentContext.getBoard()
        var lastSeedInOwnStore = false

        val ownHouses = board.houses[playerID]!!
        val ownStore = board.stores[playerID]!!
        val opponentHouses = board.houses[opponentPlayerID]!!

        check((houseNumber > 0 && houseNumber <= ownHouses.size) && ownHouses[houseNumber - 1].count() != 0) {
            throw MoveNotAllowedException("House is empty or does not belong to the player.")
        }

        var currentHouseIndex = houseNumber - 1
        var seeds = ownHouses[currentHouseIndex].empty()
        currentHouseIndex++

        while (seeds > 0) {
            while (seeds > 0 && currentHouseIndex < ownHouses.size) {
                if (seeds == 1 && seedsCanBeStolen(playerID, opponentPlayerID, currentHouseIndex)) {
                    val stolenSeeds = opponentHouses[opponentHouses.size - currentHouseIndex - 1].empty()
                    ownStore.add(seeds + stolenSeeds)
                } else {
                    ownHouses[currentHouseIndex].add()
                }
                currentHouseIndex++
                seeds--
            }

            if (seeds > 0) {
                ownStore.add()
                seeds--
                if (seeds == 0) {
                    lastSeedInOwnStore = true
                }
            }

            currentHouseIndex = 0
            while (seeds > 0 && currentHouseIndex < opponentHouses.size) {
                opponentHouses[currentHouseIndex].add()
                currentHouseIndex++
                seeds--
            }
            currentHouseIndex = 0
        }

        return lastSeedInOwnStore
    }

    protected fun isGameOver(): Boolean {
        val seedsP1 = currentContext.getBoard().houses[Board.PLAYER_1_ID]!!.sumOf { it.count() }
        val seedsP2 = currentContext.getBoard().houses[Board.PLAYER_2_ID]!!.sumOf { it.count() }
        return (seedsP1 == 0 || seedsP2 == 0)
    }

    private fun seedsCanBeStolen(playerID: String, opponentPlayerID: String, houseIndex: Int): Boolean {
        val board = currentContext.getBoard()
        return board.houses[playerID]!![houseIndex].count() == 0 &&
            board.houses[opponentPlayerID]!![board.getNumberOfHouses() - houseIndex - 1].count() != 0
    }
}
