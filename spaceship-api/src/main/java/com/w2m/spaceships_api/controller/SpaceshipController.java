package com.w2m.spaceships_api.controller;

import com.w2m.spaceships_api.model.SpaceshipDTO;
import com.w2m.spaceships_api.service.SpaceshipService;
import com.w2m.spaceships_api.utils.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static com.w2m.spaceships_api.utils.ApiConstants.DEFAULT_PAGE_NUMBER;
import static com.w2m.spaceships_api.utils.ApiConstants.DEFAULT_PAGE_SIZE_NUMBER;
import static com.w2m.spaceships_api.utils.ApiConstants.ID;
import static com.w2m.spaceships_api.utils.ApiConstants.SEARCH;
import static com.w2m.spaceships_api.utils.ApiConstants.SLASH;
import static com.w2m.spaceships_api.utils.ApiConstants.SPACESHIPS;


@RestController
@RequestMapping(SLASH + SPACESHIPS)
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @Operation(summary = "Retrieve a paginated list of spaceships", description =
            "Fetches a paginated list of spaceships with a default size of " + DEFAULT_PAGE_SIZE_NUMBER + " per page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of spaceships")
    })
    @GetMapping
    public ResponseEntity<Page<SpaceshipDTO>> getAllSpaceships(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE_NUMBER) int size) {

        Page<SpaceshipDTO> pagedSpaceships = spaceshipService.getAllSpaceships(page, size);

        if (pagedSpaceships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pagedSpaceships, HttpStatus.OK);
    }

    @Operation(summary = "Get a spaceship by its ID", description = "Retrieves a specific spaceship by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved spaceship data"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @GetMapping(SLASH + ApiConstants.ID)
    public ResponseEntity<SpaceshipDTO> getSpaceshipById(@PathVariable Long id) {
        return spaceshipService.getSpaceshipById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Search spaceships by name", description = "Searches for spaceships that match a specific name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved spaceships by name"),
            @ApiResponse(responseCode = "404", description = "Unexpected error")
    })
    @GetMapping(SLASH + SEARCH)
    public ResponseEntity<Collection<SpaceshipDTO>> getSpaceshipsByName(@RequestParam String name) {
        Collection<SpaceshipDTO> spaceships = spaceshipService.getSpaceshipsByName(name);

        return spaceships.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(spaceships, HttpStatus.OK);
    }

    @Operation(summary = "Create a new spaceship", description = "Creates a new spaceship.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the spaceship")
    })
    @PostMapping
    public ResponseEntity<SpaceshipDTO> createSpaceship(@RequestBody SpaceshipDTO spaceship) {
        SpaceshipDTO createdSpaceship = spaceshipService.createSpaceship(spaceship);
        return new ResponseEntity<>(createdSpaceship, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a spaceship", description = "Updates the information of an existing spaceship.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated spaceship"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @PutMapping(SLASH + ID)
    public ResponseEntity<SpaceshipDTO> updateSpaceship(
            @PathVariable Long id,
            @RequestBody @Valid SpaceshipDTO spaceship) {
        return Optional.ofNullable(spaceshipService.updateSpaceship(id, spaceship))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a spaceship", description = "Deletes a spaceship identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted spaceship"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @DeleteMapping(SLASH + ID)
    public ResponseEntity<String> deleteSpaceship(@PathVariable Long id) {
        String responseMessage = spaceshipService.deleteSpaceship(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }
}
