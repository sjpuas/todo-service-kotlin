package cl.puas.todoapp.repositories.entities

import cl.puas.todoapp.domain.TodoItemStatus
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "todo")
data class ToDoEntity(
    @Id @Column(name = "id") val id: UUID,
    @Column(name = "text") val text: String,
    @Enumerated(EnumType.STRING) @Column(name = "status") val status: TodoItemStatus,
    @Column(name = "created_date") val createdAt: OffsetDateTime,
    @Column(name = "completed_date") val completedDate: OffsetDateTime? = null,
)
