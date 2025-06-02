package com.dreamy_delights.root.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotBlank(message = "username must not be blank")
    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @NotBlank(message = "password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email must not be blank")
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @NotBlank(message = "phone must not be blank")
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @NotNull(message = "role id must not be null")
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
}
