package com.bol.kalaha.model.board

class Store : Pit(0) {

    fun add(newSeeds: Int) {
        this.seeds += newSeeds
    }
}
