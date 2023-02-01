package com.bol.kalaha.controller

import com.bol.kalaha.model.board.Board
import com.bol.kalaha.service.GameService
import com.bol.kalaha.service.exception.InvalidPlayerException
import com.bol.kalaha.service.exception.MoveNotAllowedException
import com.bol.kalaha.utils.TestUtils
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [GameController::class])
class GameControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var gameService: GameService

    companion object {
        private const val ERROR_SEVERITY = "error"
        private const val ERROR_MESSAGE = "Error message"
    }

    @Test
    fun `should return context DTO when game starts successfully`() {
        val requestURL = "http://localhost/startGame"
        every { gameService.startGame() } returns TestUtils.getGameContext()

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.board[0].player") { value(Board.PLAYER_1_ID) }
                jsonPath("$.board[1].player") { value(Board.PLAYER_2_ID) }
            }
    }

    @Test
    fun `should return an error when GameService throws InvalidPlayerException`() {
        val requestURL = "http://localhost/play/1/1"
        every { gameService.play(any(), any()) } throws InvalidPlayerException(ERROR_MESSAGE)

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.severity") { value(ERROR_SEVERITY) }
                jsonPath("$.message") { value(ERROR_MESSAGE) }
            }
    }

    @Test
    fun `should return an error when GameService throws MoveNotAllowedException`() {
        val requestURL = "http://localhost/play/1/1"
        every { gameService.play(any(), any()) } throws MoveNotAllowedException(ERROR_MESSAGE)

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.severity") { value(ERROR_SEVERITY) }
                jsonPath("$.message") { value(ERROR_MESSAGE) }
            }
    }

    @Test
    fun `should return context DTO when a move is made successfully`() {
        val requestURL = "http://localhost/play/1/1"
        every { gameService.play(any(), any()) } returns TestUtils.getGameContext()

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.board[0].player") { value(Board.PLAYER_1_ID) }
                jsonPath("$.board[1].player") { value(Board.PLAYER_2_ID) }
            }
    }

    @Test
    fun `should return an error when requesting to play with wrong house number`() {
        val requestURL = "http://localhost/play/1/HOUSE_NUMBER"

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.severity") { value(ERROR_SEVERITY) }
                jsonPath("$.message") { value(RestExceptionHandler.BAD_REQUEST_ERROR_MESSAGE) }
            }
    }

    @Test
    fun `should return context DTO when game ends successfully`() {
        val requestURL = "http://localhost/endGame"
        every { gameService.endGame() } returns TestUtils.getGameContext()

        mockMvc.post(requestURL) {}
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.board[0].player") { value(Board.PLAYER_1_ID) }
                jsonPath("$.board[1].player") { value(Board.PLAYER_2_ID) }
            }
    }
}