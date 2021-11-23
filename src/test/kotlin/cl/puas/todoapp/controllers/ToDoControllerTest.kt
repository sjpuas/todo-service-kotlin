package cl.puas.todoapp.controllers

import cl.puas.todoapp.domain.TodoItem
import cl.puas.todoapp.domain.TodoItemStatus
import cl.puas.todoapp.services.ToDoService
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.OffsetDateTime
import java.util.UUID

@WebMvcTest(ToDoController::class)
internal class ToDoControllerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var toDoService: ToDoService

    @Test
    fun `should get all todo items`() {
        val expectedToDoList = listOf(
            TodoItem(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )
        )

        `when`(toDoService.getAllToDoItems())
            .thenReturn(expectedToDoList)

        mockMvc
            .perform(get("/todos"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize<Any>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", equalTo("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].text", equalTo("test")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", equalTo("ACTIVE")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].created_at", equalTo("2021-05-08T10:00:00.931342Z")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed_at", equalTo("2021-05-09T10:00:00.931342Z")))
    }

    @Test
    fun `should get a todo item by id`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")
        val expectedToDo =
            TodoItem(
                id = id,
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        `when`(toDoService.getToDoItemById(id))
            .thenReturn(expectedToDo)

        mockMvc
            .perform(get("/todos/{id}", id))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.text", equalTo("test")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("ACTIVE")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.created_at", equalTo("2021-05-08T10:00:00.931342Z")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.completed_at", equalTo("2021-05-09T10:00:00.931342Z")))
    }

    @Test
    fun `should get 404 when item doesn't exist`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        mockMvc
            .perform(get("/todos/{id}", id))
            .andExpect(MockMvcResultMatchers.status().`is`(404))
    }

    @Test
    fun `should save a todo item`() {
        val text = "test"
        val expectedToDo =
            TodoItem(
                id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa"),
                text = "test",
                status = TodoItemStatus.ACTIVE,
                createdAt = OffsetDateTime.parse("2021-05-08T10:00:00.931342+00:00"),
                completedAt = OffsetDateTime.parse("2021-05-09T10:00:00.931342+00:00"),
            )

        `when`(toDoService.createTodoItem(text))
            .thenReturn(expectedToDo)

        mockMvc
            .perform(
                post("/todos")
                    .contentType("application/json")
                    .content("{\"text\":\"test\"}")
            )
            .andExpect(MockMvcResultMatchers.status().`is`(201))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.text", equalTo("test")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("ACTIVE")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.created_at", equalTo("2021-05-08T10:00:00.931342Z")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.completed_at", equalTo("2021-05-09T10:00:00.931342Z")))
    }

    @Test
    fun `should delete a todo item`() {
        val id = UUID.fromString("a9e9426a-8f7d-442b-bdbb-e068eb5d80aa")

        mockMvc
            .perform(delete("/todos/{id}", id))
            .andExpect(MockMvcResultMatchers.status().`is`(204))

        verify(toDoService).deleteToDoItemById(id)
    }
}
