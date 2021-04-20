package com.epam.repository.api;

import com.epam.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAllUser();
}