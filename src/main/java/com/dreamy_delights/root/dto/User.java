package com.dreamy_delights.root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @JsonProperty("user_id")
    protected Integer id;

    @JsonProperty("username")
    protected String username;

    @JsonProperty("password")
    protected String password;

    @JsonProperty("email")
    protected String email;

    @JsonProperty("phone")
    protected String phone;

    @JsonProperty("user_role")
    protected Integer role;

}

