package com.acolonia.spring.controller;

import com.acolonia.spring.model.User;
import com.acolonia.spring.service.IServices.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "API para gestionar los usuarios, este modulo requiere autenticación por token")
public class UserController {

    @Autowired
    private UserService userSerivce;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Obtener todas los Usuarios", description = "Retorna una lista con todos los usuarios" +
            " registrados (solo cuando se ha autenticado con el rol ADMIN o USER)")
    public ResponseEntity<List<User>> getAllUser () {
        return new ResponseEntity<>(userSerivce.findAllUser(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registra nuevos usuarios", description = "Registra Usuarios en la" +
            " base de datos (solo cuando se ha autenticado con el rol ADMIN)")
    public ResponseEntity<Optional<User>> createUser (@RequestBody User newUser) {
        Optional<User> saveUser = userSerivce.saveNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualiza usuarios existentes segun su id", description = "Actualiza Usuarios en la" +
            " base de datos (solo cuando se ha autenticado con el rol ADMIN) solo actualiza los datos de Nombre - Apellido - Fecha de cumpleaños - Password y Role")
    public ResponseEntity<Optional<User>> updateUser (@PathVariable Long id, @RequestBody User user) {
        Optional<User> updateUser = userSerivce.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Elimina usuarios según su id", description = "Elimina Usuarios en la" +
            " base de datos (solo cuando se ha autenticado con el rol ADMIN)")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        Optional<User> deleteUser = userSerivce.deleteByIdUser(id);
        if (deleteUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
