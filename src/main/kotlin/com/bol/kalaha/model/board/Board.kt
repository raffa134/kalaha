package com.bol.kalaha.model.board

class Board(private val numberOfHouses: Int, private val numberOfSeeds: Int) {

    companion object {
        const val PLAYER_1_ID = "PLAYER_1"
        const val PLAYER_2_ID = "PLAYER_2"
    }

    val houses = mapOf(
        PLAYER_1_ID to List(numberOfHouses) { House(numberOfSeeds) },
        PLAYER_2_ID to List(numberOfHouses) { House(numberOfSeeds) }
    )

    val stores = mapOf(
        PLAYER_1_ID to Store(),
        PLAYER_2_ID to Store()
    )

    fun getNumberOfHouses() = numberOfHouses

    override fun toString(): String {

        val storeP1 = stores[PLAYER_1_ID]?.count()
        val storeP2 = stores[PLAYER_2_ID]?.count()
        val housesP1 = houses[PLAYER_1_ID]?.reversed()?.map { it.count() }
        val housesP2 = houses[PLAYER_2_ID]?.map { it.count() }

        return """
            [$storeP1] ${ housesP1?.joinToString(") (", "(", ")") }
                ${ housesP2?.joinToString(") (", "(", ")") } [$storeP2]
        """.trimIndent()
    }
}
