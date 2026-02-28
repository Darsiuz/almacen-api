package com.almacen.api.controller;

import com.almacen.api.dto.UserDTO;
import com.almacen.api.mapper.UserMapper;
import com.almacen.api.model.User;
import com.almacen.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestor de Usuarios", description = "Endpoints para la gestion de usuarios")
@RestController
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET - Listar usuarios
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // GET - Usuario por ID
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario especifico mediante su ID")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    // POST - Crear usuario
    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema con los datos proporcionados")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del usuario a crear",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserDTO.class),
            examples= @ExampleObject(
                name = "Ejemplo de usuario",
                value = """
                {
                    "name": "Juan Perez",
                    "email": "juan@almacen.com",
                    "password": "123456",
                    "role": "ADMIN",
                    "active": true
                }
                """
            )
        )
    )
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {

        User createdUser = userService.createUser(dto);
        UserDTO createdUserDTO = UserMapper.toDTO(createdUser);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    // PUT - Actualizar usuario
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente mediante su ID")
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable long id,
            @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    // DELETE - Eliminar usuario
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema mediante su ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    // PATCH - Activar/Desactivar usuario
    @Operation(summary = "Activar/Desactivar usuario", description = "Activa o desactiva un usuario mediante su ID")
    @PatchMapping("/{id}/toggle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        if (user.isActive()) {
            userService.disableUser(id);
        } else {
            userService.enableUser(id);
        }
    }
}
