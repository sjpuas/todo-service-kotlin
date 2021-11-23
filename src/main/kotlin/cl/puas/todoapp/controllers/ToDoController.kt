package cl.puas.todoapp.controllers

import cl.puas.todoapp.controllers.models.ToDoItemRequest
import cl.puas.todoapp.domain.NotFoundException
import cl.puas.todoapp.domain.TodoItem
import cl.puas.todoapp.services.ToDoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/todos")
class ToDoController(
    val toDoService: ToDoService
) {

    @GetMapping
    fun getAllToDoItems(): List<TodoItem> =
        toDoService.getAllToDoItems()

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createToDoItem(@RequestBody toDoItemRequest: ToDoItemRequest): TodoItem =
        toDoService.createTodoItem(toDoItemRequest.text)

    @GetMapping("/{id}")
    fun getToDoItemById(@PathVariable("id") id: UUID): TodoItem =
        toDoService.getToDoItemById(id) ?: throw NotFoundException()

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun deleteToDoItemById(@PathVariable("id") id: UUID) =
        toDoService.deleteToDoItemById(id)
}
