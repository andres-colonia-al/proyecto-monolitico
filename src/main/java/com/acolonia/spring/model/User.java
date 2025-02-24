package com.acolonia.spring.model;


import com.acolonia.spring.model.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //Atributos de la tabla USUARIO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "cedula", length = 12, nullable = false, unique = true)
    private Integer cc;

    @Column(name = "nombre", nullable = false, length = 20)
    private String name;

    @Column(name = "apellido", nullable = false, length = 20)
    private String lastName;

    @Column(name = "fecha_cumpleaños", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "contraseña", nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 80)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;


}
