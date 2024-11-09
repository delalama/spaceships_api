package com.w2m.spaceships_api.controller;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.service.SpaceshipService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.List;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

    @Autowired

    private SpaceshipService spaceshipService;

    @Operation(summary = "Retrieve a paginated list of spaceships", description = "Fetches a paginated list of spaceships with a default size of 10 per page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of spaceships")
    })
    @GetMapping
    public ResponseEntity<Page<Spaceship>> getAllSpaceships(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Spaceship> result = spaceshipService.getAllSpaceships(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a spaceship by its ID", description = "Retrieves details of a specific spaceship identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved spaceship data"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
        Spaceship spaceship = spaceshipService.getSpaceshipById(id);
        return new ResponseEntity<>(spaceship, HttpStatus.OK);
    }

    @Operation(summary = "Search spaceships by name", description = "Searches for spaceships that match a specific name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved spaceships by name")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Spaceship>> getSpaceshipsByName(@RequestParam String name) {
        List<Spaceship> result = spaceshipService.getSpaceshipsByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Create a new spaceship", description = "Creates a new spaceship entry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the spaceship"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid spaceship data")
    })
    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
        Spaceship createdSpaceship = spaceshipService.createSpaceship(spaceship);
        return new ResponseEntity<>(createdSpaceship, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a spaceship", description = "Updates the information of an existing spaceship.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated spaceship"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceship(@PathVariable Long id, @RequestBody Spaceship spaceship) {
        Spaceship updatedSpaceship = spaceshipService.updateSpaceship(id, spaceship);
        return new ResponseEntity<>(updatedSpaceship, HttpStatus.OK);
    }

    @Operation(summary = "Delete a spaceship", description = "Deletes a spaceship identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted spaceship"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
        spaceshipService.deleteSpaceship(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
