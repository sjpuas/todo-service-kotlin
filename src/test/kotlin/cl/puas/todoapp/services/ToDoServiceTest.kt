package cl.puas.todoapp.services

import cl.puas.todoapp.domain.TodoItem
import cl.puas.todoapp.domain.TodoItemStatus
import cl.puas.todoapp.repositories.ToDoRepository
import cl.puas.todoapp.repositories.entities.ToDoEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.OffsetDateTime
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class ToDoServiceTest {

    @Mock
    lateinit var toDoRepository: ToDoRepository
    @InjectMocks
    lateinit var toDoService: ToDoService

    @Test
    fun `should get all todo items`() {
        val todoListFromDatabase = listOf(
            ToDoEntity(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )
        )

        val expectedToDoList = listOf(
            TodoItem(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )
        )

        `when`(toDoRepository.findAll())
            .thenReturn(todoListFromDatabase)

        val todoList = toDoService.getAllToDoItems()

        assertThat(todoList)
            .hasSize(1)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expectedToDoList)
    }

    @Test
    fun `should get a todo item`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        val todoFromDatabase =
            ToDoEntity(
                id = id,
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        val expectedToDo =
            TodoItem(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        `when`(toDoRepository.findById(id))
            .thenReturn(Optional.of(todoFromDatabase))

        val todoItem = toDoService.getToDoItemById(id)

        assertThat(todoItem)
            .usingRecursiveComparison()
            .isEqualTo(expectedToDo)
    }

    @Test
    fun `should not get a todo item`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        `when`(toDoRepository.findById(id))
            .thenReturn(Optional.empty())

        val todoItem = toDoService.getToDoItemById(id)

        assertThat(todoItem).isNull()
    }

    @Test
    fun `should save a todo item`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        val todoFromDatabase =
            ToDoEntity(
                id = id,
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedDate = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        val expectedToDo =
            TodoItem(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        `when`(toDoRepository.save(any()))
            .thenReturn(todoFromDatabase)

        val savedTodoItem = toDoService.createTodoItem("test")

        assertThat(savedTodoItem)
            .usingRecursiveComparison()
            .isEqualTo(expectedToDo)
    }

    @Test
    fun `should delete a todo item`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        toDoService.deleteToDoItemById(id)

        verify(toDoRepository).deleteById(id)
    }
}
