package com.bol.kalaha.model.board

abstract class Pit(var seeds: Int) {

    fun count() = seeds
    fun add() = seeds++
}
