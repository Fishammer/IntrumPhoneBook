package com.example.phonebook.controllers;

import com.example.phonebook.entities.Phone;
import com.example.phonebook.logging.LogController;
import com.example.phonebook.services.PhoneService;
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
@Tag(name = "phone", description = "the Phone API")
public class PhoneController extends CustomExceptionHandler implements LogController {
    @Autowired
    PhoneService phoneService;

    @Operation(summary = "Find all phones", description = "Returns all phone entities", tags = {"phone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Phone.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @GetMapping("/phone")
    private ResponseEntity<List<Phone>> getAllPhones() {
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.getAll());
    }

    @Operation(summary = "Find phone by ID", description = "Returns a single phone entity", tags = {"phone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Phone.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Phone not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @GetMapping("/phone/{id}")
    private ResponseEntity<Phone> getPhone(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.getById(id));
    }

    @Operation(summary = "Deletes a phone", description = "", tags = {"phone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Phone not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @DeleteMapping("/phone/{id}")
    private ResponseEntity<String> deletePhone(@PathVariable("id") Long id) {
        phoneService.delete(id);
        getLogger().warn("Deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @Operation(summary = "Adds a phone", description = "", tags = {"phone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Phone with provided id not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @PostMapping(value = "/phone", consumes = "application/json", produces = "application/json")
    private ResponseEntity<String> savePhone(@Valid @RequestBody Phone phone) {
        phoneService.save(phone);
        getLogger().warn("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @Operation(summary = "Update an existing phone", description = "", tags = {"phone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Phone with provided id not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")})
    @PutMapping(value = "/phone/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> putPhone(@Valid @RequestBody Phone phone, @PathVariable("id") Long id) {
        phoneService.update(phone, id);
        getLogger().warn("Updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
    }

    @Override
    public String getLoggerClassName() {
        return PhoneController.class.getSimpleName();
    }
}
