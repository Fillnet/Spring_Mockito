package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
        // Добавим несколько пользователей для первоначального тестирования
        userRepository.addUser(new User("test1", "test1"));
        userRepository.addUser(new User("test2", "test2"));
    }

    public List<String> getAllLogins() {
        return userRepository.getAllUsers().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    public void createUser(String login, String password) {
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Login and password must not be empty or null");
        }

        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new UserNonUniqueException("User with the given login already exists");
        }

        userRepository.addUser(new User(login, password));
    }

    public boolean loginUser(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password).isPresent();
    }
}

class UserNonUniqueException extends RuntimeException {
    public UserNonUniqueException(String message) {
        super(message);
    }
}
