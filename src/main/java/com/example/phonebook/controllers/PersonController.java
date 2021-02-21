package com.example.phonebook.controllers;

import com.example.phonebook.entities.Person;
import com.example.phonebook.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("")
@RestController
@Tag(name = "person", description = "the Person API")
public class PersonController extends CustomExceptionHandler {

    @Autowired
    PersonService personService;

    @Operation(summary = "Find all persons", description = "Returns all person entities", tags = {"person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @GetMapping("/person")
    private ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
    }

    @Operation(summary = "Find person by ID", description = "Returns a single person entity", tags = {"person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @GetMapping("/person/{id}")
    private ResponseEntity<Person> getPerson(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getById(id));
    }

    @Operation(summary = "Deletes a person", tags = {"person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @DeleteMapping("/person/{id}")
    private ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
        getLogger().warn("Deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @Operation(summary = "Adds a person", tags = {"person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Person with provided id not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @PostMapping(value = "/person", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> savePerson(@RequestBody @Valid Person person) {
        personService.save(person);
        getLogger().warn("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @Operation(summary = "Update an existing person", description = "", tags = {"person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Person with provided id not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @PutMapping(value = "/person/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<String> putPerson(@RequestBody @Valid Person person, @PathVariable("id") Long id) {
        personService.update(person, id);
        getLogger().warn("Updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
    }
}
