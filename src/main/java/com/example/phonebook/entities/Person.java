package com.example.phonebook.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of the Person.",
            example = "5123", required = true)
    private Long id;
    @NotNull(message = "First name can't be null.")
    @Size(message = "First name must contain 1 to 20 characters", min = 1, max = 20)
    @Schema(description = "First name of the person",
            example = "Kristers", required = true)
    private String firstName;
    @NotNull(message = "Last name can't be null.")
    @Size(message = "Last name must contain 1 to 20 characters", min = 1, max = 20)
    @Schema(description = "Last name of the person",
            example = "Fisers", required = true)
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Birth date has to be in the past.")
    @NotNull(message = "Birth date can't be null.")
    @Schema(description = "First name of the person",
            example = "1997-04-23", required = true)
    private LocalDate birthDate;
}
