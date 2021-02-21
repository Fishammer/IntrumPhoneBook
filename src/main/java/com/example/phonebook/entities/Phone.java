package com.example.phonebook.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Phone {
    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of the Phone.",
            example = "1124", required = true)
    private Long id;
    @Pattern(regexp = "mobile|home|work", message = "Type must match one of these: mobile, home or work")
    @Size(message = "Number must contain 1 to 6 characters", min = 1, max = 6)
    @Schema(description = "Type of the Phone.",
            example = "work", required = true)
    private String type;
    @Pattern(regexp = "\\+371 2[0-9]{7}", message = " Number must match format: \"+371 XXXXXXXX\"\n")
    @Size(message = "Number must contain 1 to 13 characters", min = 1, max = 13)
    @Schema(description = "Phone number.",
            example = "+371 23245121", required = true)
    private String number;
    @Schema(description = "Person ID phone's associated with",
            example = "1421", required = true)
    private Long personId;
}
