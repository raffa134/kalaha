package com.bol.kalaha.model.board

class House(seeds: Int) : Pit(seeds) {

    fun empty(): Int {
        val currentSeeds = this.count()
        this.seeds = 0
        return currentSeeds
    }
}
