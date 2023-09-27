package com.ua.glebkorobov.javapracticaltestassignment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.glebkorobov.javapracticaltestassignment.dto.UserDto;
import com.ua.glebkorobov.javapracticaltestassignment.entities.UserEntity;
import com.ua.glebkorobov.javapracticaltestassignment.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
@SpringJUnitConfig
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        Mockito.when(userService.save(userDto)).thenReturn(Optional.of(userEntity));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(1L);
        updatedUserEntity.setEmail("updated@example.com");
        updatedUserEntity.setFirstName("Updated");
        updatedUserEntity.setLastName("User");
        Mockito.when(userService.update(1L, "updated@example.com", "Updated", "User",
                        null, null, null))
                .thenReturn(Optional.of(updatedUserEntity));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/1")
                        .param("email", "updated@example.com")
                        .param("firstName", "Updated")
                        .param("lastName", "User")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"));
    }

    @Test
    void testUpdateAllFields() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("updated@example.com");
        userDto.setFirstName("Updated");
        userDto.setLastName("User");

        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(1L);
        updatedUserEntity.setEmail("updated@example.com");
        updatedUserEntity.setFirstName("Updated");
        updatedUserEntity.setLastName("User");
        Mockito.when(userService.update(1L, userDto)).thenReturn(Optional.of(updatedUserEntity));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.when(userService.delete(1L)).thenReturn(Optional.of(new UserEntity()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testSearchUsersFromToDate() throws Exception {
        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setFirstName("User1");
        user1.setLastName("Last1");
        users.add(user1);

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setFirstName("User2");
        user2.setLastName("Last2");
        users.add(user2);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        Mockito.when(userService.getFromToDate(fromDate, toDate)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .param("fromDate", "2023-01-01")
                        .param("toDate", "2023-12-31"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Last1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("user2@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("User2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Last2"));
    }
}