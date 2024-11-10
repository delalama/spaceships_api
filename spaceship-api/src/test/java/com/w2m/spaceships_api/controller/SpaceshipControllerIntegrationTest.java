package com.w2m.spaceships_api.controller;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.model.SpaceshipDTO;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import com.w2m.spaceships_api.utils.ApiConstants;
import com.w2m.spaceships_api.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.w2m.spaceships_api.utils.ApiConstants.SEARCH;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpaceshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        spaceshipRepository.deleteAll();
    }

    private Spaceship getSpaceShip() {
        return Spaceship.builder()
                .id(null)
                .name("Galactica")
                .model("X-100")
                .creationDate(LocalDate.of(2024, 1, 1))
                .build();
    }

    private Spaceship getSpaceShip2() {
        return Spaceship.builder()
                .id(null)
                .name("Galactica2")
                .model("X-100")
                .creationDate(LocalDate.of(2024, 1, 1))
                .build();
    }

    @Test
    public void testGetAllSpaceships() throws Exception {
        spaceshipRepository.save(getSpaceShip());
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS)
                                .param("page", "0")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$" + ".content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name")
                        .value("Galactica"));
    }

    @Test
    public void testGetAllSpaceshipsNotFoundStatus() throws Exception {
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS)
                                .param("page", "0")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetSpaceshipById() throws Exception {
        Spaceship spaceship = getSpaceShip();

        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + spaceship.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galactica"));
    }

    @Test
    public void testGetSpaceshipsByName() throws Exception {
        Spaceship spaceship1 = getSpaceShip();
        Spaceship spaceship2 = getSpaceShip2();
        spaceshipRepository.save(spaceship1);
        spaceshipRepository.save(spaceship2);
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + SEARCH)
                                .param("name", "Galactica")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name")
                        .value("Galactica"))
                .andExpect(jsonPath("$[1].name")
                        .value("Galactica2"));
    }

    @Test
    public void testGetSpaceshipsByName_NotFound() throws Exception {
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + SEARCH)
                                .param("name", "NonExistentName")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSpaceship_Success() throws Exception {
        SpaceshipDTO spaceshipDTO = SpaceshipDTO.builder()
                .name("Voyager")
                .model("Intrepid-class")
                .creationDate(LocalDate.now())
                .build();

        mockMvc.perform(post(ApiConstants.SLASH + ApiConstants.SPACESHIPS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(spaceshipDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(spaceshipDTO.getName()))
                .andExpect(jsonPath("$.model").value(spaceshipDTO.getModel()))
                .andExpect(jsonPath("$.creationDate").exists());
    }

    @Test
    public void testUpdateSpaceship() throws Exception {
        Spaceship spaceship = getSpaceShip();
        Spaceship replacingSpaceship = Spaceship.builder().name("Galactica Updated").build();

        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        put(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + spaceship.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(replacingSpaceship.toJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galactica Updated"));
    }


    @Test
    public void testDeleteSpaceship() throws Exception {
        Spaceship spaceship = getSpaceShip();
        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        delete(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + spaceship.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}