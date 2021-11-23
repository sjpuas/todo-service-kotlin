package cl.puas.todoapp.controllers

import cl.puas.todoapp.domain.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.NativeWebRequest
import org.zalando.problem.Problem
import org.zalando.problem.Status
import org.zalando.problem.spring.web.advice.ProblemHandling

@ControllerAdvice
class ControllerAdvice : ProblemHandling {

    @ExceptionHandler
    fun handleNotFound(
        exception: NotFoundException,
        request: NativeWebRequest
    ): ResponseEntity<Problem> = create(Status.NOT_FOUND, exception, request)
}
