package com.dreamy_delights.root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing authentication request details in the system")
public class AuthRequest {

    @NotBlank(message = "Username must not be blank")
    @Schema(description = "Registered unique username", example = "JohnDoe")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Schema(description = "Registered Password", example = "Example@123")
    @JsonProperty("password")
    private String password;
}
