package cl.puas.todoapp.services

import cl.puas.todoapp.domain.TodoItem
import cl.puas.todoapp.domain.TodoItemStatus
import cl.puas.todoapp.repositories.ToDoRepository
import cl.puas.todoapp.repositories.entities.ToDoEntity
import cl.puas.todoapp.repositories.entities.toDomain
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ToDoService(
    val toDoRepository: ToDoRepository
) {

    fun getAllToDoItems(): List<TodoItem> = toDoRepository
        .findAll()
        .map { item -> item.toDomain() }

    @Transactional
    fun createTodoItem(text: String): TodoItem {
        val toDoEntity = ToDoEntity(
            id = UUID.randomUUID(),
            text = text,
            status = TodoItemStatus.ACTIVE,
            createdAt = OffsetDateTime.now()
        )
        return toDoRepository
            .save(toDoEntity)
            .toDomain()
    }

    fun getToDoItemById(id: UUID): TodoItem? =
        toDoRepository.findByIdOrNull(id)?.toDomain()

    @Transactional
    fun deleteToDoItemById(id: UUID) = toDoRepository.deleteById(id)
}
