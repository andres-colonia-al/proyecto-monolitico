package com.acolonia.spring.service.IServices;


import com.acolonia.spring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUser();

    Optional<User> saveNewUser (User user);

    Optional<User> deleteByIdUser(Long id);

    Optional<User> updateUser (Long id, User updateUser);

}
