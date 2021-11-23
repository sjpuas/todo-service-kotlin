package cl.puas.todoapp.repositories.entities

import cl.puas.todoapp.domain.TodoItem

fun ToDoEntity.toDomain(): TodoItem =
    TodoItem(
        id = this.id,
        text = this.text,
        status = this.status,
        createdAt = this.createdAt,
        completedAt = this.completedDate,
    )
