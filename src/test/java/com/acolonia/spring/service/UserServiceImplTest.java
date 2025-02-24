package com.acolonia.spring.service;

import com.acolonia.spring.model.User;
import com.acolonia.spring.model.enums.UserRoles;
import com.acolonia.spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setIdUser(null);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@gmail.com");
        user.setName("John");
        user.setLastName("Doe");
        user.setRole(UserRoles.USER);
    }


    @Test
    void findAllUsersTest() {
        //Debería buscar todos los usuarios en la base de datos
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.findAllUser();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveNewUserEmptyTest() {
        //Dberia retornar un vacio cuando el usuario existe
        user.setIdUser(1L);
        Optional<User> savedUser = userService.saveNewUser(user);
        assertTrue(savedUser.isEmpty());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void saveNewUserTest() {
        //Debería guardar un usuario cuando el usuario es válido
        user.setIdUser(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<User> savedUser = userService.saveNewUser(user);

        assertTrue(savedUser.isPresent());
        assertEquals(user.getIdUser(), savedUser.get().getIdUser());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteByIdUserTest() {
        //Debería eliminar un usuario cuando existe
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        Optional<User> deletedUser = userService.deleteByIdUser(1L);

        assertTrue(deletedUser.isPresent());
        assertEquals(user.getIdUser(), deletedUser.get().getIdUser());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdUserEmptyTest() {
        //Debería retornar vacio cuando el usuario a eliminar no existe
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> deletedUser = userService.deleteByIdUser(1L);

        assertTrue(deletedUser.isEmpty());
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void updateUserTest() {
        //Deberia retornar el usuario actualizado cuando existe
        User updatedData = new User();
        updatedData.setName("Felipe");
        updatedData.setLastName("Aldana");
        updatedData.setPassword("newPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updatedData.getPassword())).thenReturn("hashedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<User> updatedUser = userService.updateUser(1L, updatedData);

        assertTrue(updatedUser.isPresent());
        assertEquals("Felipe", updatedUser.get().getName());
        assertEquals("Aldana", updatedUser.get().getLastName());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserEmptyTest() {
        //Debería retornar vacio cuando el usuario a actualizar no existe
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<User> updatedUser = userService.updateUser(1L, user);
        assertTrue(updatedUser.isEmpty());
        verify(userRepository, never()).save(any(User.class));
    }

}