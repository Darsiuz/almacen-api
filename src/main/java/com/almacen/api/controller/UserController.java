package com.almacen.api.controller;

import com.almacen.api.dto.UserDTO;
import com.almacen.api.model.User;
import com.almacen.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET - Listar usuarios
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // GET - Usuario por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    // POST - Crear usuario
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role
    ) {
        User user = userService.createUser(name, email, password, role);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // PUT - Actualizar usuario
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable long id,
            @RequestBody UserDTO userDTO
    ) {
        return userService.updateUser(id, userDTO);
    }

    // DELETE - Eliminar usuario
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    // PATCH - Desactivar usuario
    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableUser(@PathVariable long id) {
        userService.disableUser(id);
    }
}
