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
@Schema(description = "Data Transfer Object representing a role in the system")
public class Role {
    @JsonProperty("role_id")
    @Schema(description = "Unique identifier of the role", example = "1")
    private Integer Id;

    @NotBlank(message = "Role name must not be blank")
    @Schema(description = "Name of the role", example = "admin")
    @JsonProperty("role_name")
    private String name;

}
