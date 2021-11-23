package cl.puas.todoapp.repositories

import cl.puas.todoapp.repositories.entities.ToDoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ToDoRepository : JpaRepository<ToDoEntity, UUID>
