package cl.puas.todoapp.domain

import java.time.OffsetDateTime
import java.util.UUID

data class TodoItem(
    val id: UUID,
    val text: String,
    val status: TodoItemStatus = TodoItemStatus.ACTIVE,
    val createdAt: OffsetDateTime,
    val completedAt: OffsetDateTime? = null,
)

enum class TodoItemStatus {
    ACTIVE,
    DONE,
}
