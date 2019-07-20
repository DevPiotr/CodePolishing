package com.codepolishing.engineer.service;

import com.codepolishing.engineer.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(int theId);

    void save(User user);

    void deleteById(int theId);
}
