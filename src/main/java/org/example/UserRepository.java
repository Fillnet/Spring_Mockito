package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> findUserByLogin(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    public void addUser(User user) {
        users.add(user);
    }
}
