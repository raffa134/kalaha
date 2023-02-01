package com.bol.kalaha.controller

import com.bol.kalaha.model.ErrorDTO
import com.bol.kalaha.service.exception.IllegalGameStateException
import com.bol.kalaha.service.exception.InvalidPlayerException
import com.bol.kalaha.service.exception.MoveNotAllowedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class RestExceptionHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
        const val GENERIC_ERROR_MESSAGE = "Error occurred while processing the request"
        const val BAD_REQUEST_ERROR_MESSAGE = "Invalid request parameter"
    }

    @ExceptionHandler(InvalidPlayerException::class)
    fun handleInvalidPlayerException(
        ex: InvalidPlayerException
    ): ResponseEntity<Any> {
        val error = ErrorDTO(ErrorDTO.Severity.error, ex.message)
        logger.error("Error with the following exception: $ex", ex)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MoveNotAllowedException::class)
    fun handleMoveNotAllowedException(
        ex: MoveNotAllowedException
    ): ResponseEntity<Any> {
        val error = ErrorDTO(ErrorDTO.Severity.error, ex.message)
        logger.error("Error with the following exception: $ex", ex)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalGameStateException::class)
    fun handleIllegalGameStateException(
        ex: IllegalGameStateException
    ): ResponseEntity<Any> {
        val error = ErrorDTO(ErrorDTO.Severity.error, GENERIC_ERROR_MESSAGE)
        logger.error("Error with the following exception: $ex", ex)
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<Any> {
        val error = ErrorDTO(ErrorDTO.Severity.error, BAD_REQUEST_ERROR_MESSAGE)
        logger.error("Error with the following exception: $ex", ex)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}