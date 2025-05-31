package com.dreamy_delights.root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @JsonProperty("role_id")
    private Integer Id;

    @JsonProperty("role_name")
    private String name;

}
