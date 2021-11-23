package cl.puas.todoapp.repositories

import cl.puas.todoapp.domain.TodoItemStatus
import cl.puas.todoapp.repositories.entities.ToDoEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.OffsetDateTime
import java.util.UUID

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class ToDoRepositoryTest(
    @Autowired val toDoRepository: ToDoRepository
) {

    @Test
    fun `should add multiple todo items and then return them from database`() {
        val todoItem = ToDoEntity(
            id = UUID.fromString("e8e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
            text = "test",
            status = TodoItemStatus.ACTIVE,
            createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
            completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
        )

        toDoRepository.save(todoItem)

        val anotherTodoItem = ToDoEntity(
            id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
            text = "test",
            status = TodoItemStatus.ACTIVE,
            createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
            completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
        )

        toDoRepository.save(anotherTodoItem)

        val todoItemList = toDoRepository.findAll()

        assertThat(todoItemList)
            .hasSize(2)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(listOf(todoItem, anotherTodoItem))
    }

    @Test
    fun `should add a new todo item and then get it from database`() {
        val id = UUID.fromString("e8e9426a-8f7d-442b-bdbb-e068eb5d80aa")
        val todoItem = ToDoEntity(
            id = id,
            text = "test",
            status = TodoItemStatus.ACTIVE,
            createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
            completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
        )

        toDoRepository.save(todoItem)

        val todoItemFound = toDoRepository.findById(id)

        assertThat(todoItemFound)
            .isPresent
            .get()
            .usingRecursiveComparison()
            .isEqualTo(todoItem)
    }

    @Test
    fun `should add a new todo item and then delete it from database`() {
        val id = UUID.fromString("e8e9426a-8f7d-442b-bdbb-e068eb5d80aa")
        val todoItem = ToDoEntity(
            id = id,
            text = "test",
            status = TodoItemStatus.ACTIVE,
            createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
            completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
        )

        toDoRepository.save(todoItem)
        toDoRepository.deleteById(id)

        val todoItemFound = toDoRepository.findById(id)

        assertThat(todoItemFound).isNotPresent
    }
}
