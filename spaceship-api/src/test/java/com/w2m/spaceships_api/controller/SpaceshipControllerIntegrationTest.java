package com.w2m.spaceships_api.controller;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import com.w2m.spaceships_api.utils.ApiConstants;
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

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void testGetAllSpaceships() throws Exception {
        Spaceship spaceship = new Spaceship(null, "Galactica", "X-100", new Date());
        spaceshipRepository.save(spaceship);
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
    public void testGetSpaceshipById() throws Exception {
        Spaceship spaceship = new Spaceship(null, "Galactica", "X-100", new Date());
        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + ApiConstants.ID, spaceship.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galactica"));
    }

    @Test
    public void testGetSpaceshipsByName() throws Exception {
        Spaceship spaceship1 = new Spaceship(null, "Galactica", "X-100", new Date());
        Spaceship spaceship2 = new Spaceship(null, "Galactica", "X-200", new Date());
        spaceshipRepository.save(spaceship1);
        spaceshipRepository.save(spaceship2);
        mockMvc.perform(
                        get(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + "search")
                                .param("name", "Galactica")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name")
                        .value("Galactica"))
                .andExpect(jsonPath("$[1].name")
                        .value("Galactica"));
    }

    @Test
    public void testUpdateSpaceship() throws Exception {
        Spaceship spaceship = new Spaceship(null, "Galactica", "X-100", new Date());
        Spaceship replacingSpaceship = new Spaceship(null, "Galactica Updated", "X-100", new Date());
        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        put(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + ApiConstants.ID, spaceship.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(replacingSpaceship.toJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galactica Updated"));
    }


    @Test
    public void testDeleteSpaceship() throws Exception {
        Spaceship spaceship = new Spaceship(null, "Galactica", "X-100", new Date());
        spaceship = spaceshipRepository.save(spaceship);
        mockMvc.perform(
                        delete(ApiConstants.SLASH + ApiConstants.SPACESHIPS + ApiConstants.SLASH + ApiConstants.ID, spaceship.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}