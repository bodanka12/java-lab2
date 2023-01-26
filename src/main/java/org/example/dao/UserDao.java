package org.example.dao;

import org.example.model.entity.User;

import java.util.List;

public interface UserDao {
    void addNewUser(User user);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(int id);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(User user);
}
