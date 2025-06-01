package com.dreamy_delights.root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest {

    @NotBlank(message = "Username must not be blank")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @JsonProperty("password")
    private String password;
}
