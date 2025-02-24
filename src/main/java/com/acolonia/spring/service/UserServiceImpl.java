package com.acolonia.spring.service;

import com.acolonia.spring.model.User;
import com.acolonia.spring.repository.UserRepository;
import com.acolonia.spring.service.IServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> saveNewUser(User user) {
        if (user.getIdUser() == null || user.getIdUser() == 0) {
            //encriptacion de password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userOptional = userRepository.save(user) ;
            return Optional.of(userOptional);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteByIdUser(Long id) {
        Optional<User> deleteUser = userRepository.findById(id);
        if (deleteUser.isPresent()) {
            userRepository.deleteById(id);
            return deleteUser;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> updateUser(Long id, User updateUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Solo actualiza los valores si no son nulos en el objeto recibido
                    if (updateUser.getName() != null) {
                        existingUser.setName(updateUser.getName());
                    }
                    if (updateUser.getLastName() != null) {
                        existingUser.setLastName(updateUser.getLastName());
                    }
                    if (updateUser.getDateOfBirth() != null) {
                        existingUser.setDateOfBirth(updateUser.getDateOfBirth());
                    }
                    if (updateUser.getPassword() != null) {
                        existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                    }
                    if (updateUser.getRole() != null) {
                        existingUser.setRole(updateUser.getRole());
                    }

                    return userRepository.save(existingUser);
                });
    }


}
