package com.dreamy_delights.root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing a user in the system")
public abstract class User {

    @JsonProperty("user_id")
    @Schema(description = "Unique identifier of the user", example = "123")
    protected Integer id;

    @NotBlank(message = "Username can not be blank")
    @Schema(description = "Username of the user", example = "JohnDoe")
    @JsonProperty("username")
    protected String username;

    @NotBlank(message = "Password can not be blank")
    @JsonProperty("password")
    protected String password;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    @Schema(description = "Email address of the user", example = "john@example.com")
    protected String email;

    @NotBlank(message = "Phone can not be blank")
    @JsonProperty("phone")
    @Schema(description = "Phone number of the user", example = "077-777-7777")
    protected String phone;

    @NotNull(message = "Role can not be null")
    @JsonProperty("user_role")
    @Schema(description = "User belongs role id", example = "2")
    protected Integer role;

}

