package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.model.entity.User;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Session session;

    private static final String GET_USER_BY_NUMBER = "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber";
    private static final String GET_USER_BY_ID = "SELECT u FROM User u WHERE u.id = :id";
    private static final String GET_ALL_USERS = "SELECT u FROM User u";
    private static final String DELETE_USER = "DELETE User u WHERE u.id = :id";

    @Override
    public void addNewUser(User user) {
        session.persist(user);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return session.createQuery(GET_USER_BY_NUMBER, User.class)
                .setParameter("phoneNumber", phoneNumber)
                .uniqueResult();
    }

    @Override
    public User getUserById(int id) {
        return session.createQuery(GET_USER_BY_ID, User.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery(GET_ALL_USERS, User.class)
                .list();
    }

    @Override
    public void updateUser(User user) {
        session.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        session.createQuery(DELETE_USER)
                .setParameter("id", user.getId())
                .executeUpdate();
    }
}
