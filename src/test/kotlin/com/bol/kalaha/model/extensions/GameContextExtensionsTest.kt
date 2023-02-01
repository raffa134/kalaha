package com.bol.kalaha.model.extensions

import com.bol.kalaha.model.GameContextDTO
import com.bol.kalaha.model.board.Board
import com.bol.kalaha.model.GameContext
import com.bol.kalaha.utils.TestUtils
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

class GameContextExtensionsTest {

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }
    @Test
    fun `should map game context to GameContextDTO`() {

        val context = GameContext(Board(TestUtils.NUMBER_OF_HOUSES, TestUtils.NUMBER_OF_SEEDS))
        context.setState(context.getP1TurnState())

        val gameContextResponse = ClassPathResource(
            "jsonResponses/gameContext.json"
        ).file.readText()

        val expectedGameContextDTO = objectMapper.readValue<GameContextDTO>(gameContextResponse)

        assertEquals(context.toGameContextDTO(), expectedGameContextDTO)
    }
}