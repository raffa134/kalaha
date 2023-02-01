package com.bol.kalaha.utils

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext

object TestUtils {

    const val NUMBER_OF_HOUSES = 6
    const val NUMBER_OF_SEEDS = 6

    fun getGameContext() = GameContext(Board(NUMBER_OF_HOUSES, NUMBER_OF_SEEDS))
}