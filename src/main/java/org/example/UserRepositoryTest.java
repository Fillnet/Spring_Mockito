package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testGetAllUsersEmpty() {
        assertTrue(userRepository.getAllUsers().isEmpty());
    }

    @Test
    public void testGetAllUsersWithInitialData() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        assertEquals(2, userRepository.getAllUsers().size());
        assertTrue(userRepository.getAllUsers().contains(user1));
        assertTrue(userRepository.getAllUsers().contains(user2));
    }

    @Test
    public void testFindUserByLoginExists() {
        User user = new User("user1", "password1");
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.findUserByLogin("user1");
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testFindUserByLoginNotExists() {
        Optional<User> foundUser = userRepository.findUserByLogin("nonexistent");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindUserByLoginAndPasswordExists() {
        User user = new User("user1", "password1");
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.findUserByLoginAndPassword("user1", "password1");
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testFindUserByLoginAndPasswordWrongPassword() {
        User user = new User("user1", "password1");
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.findUserByLoginAndPassword("user1", "wrongpassword");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindUserByLoginAndPasswordWrongLogin() {
        User user = new User("user1", "password1");
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.findUserByLoginAndPassword("wronglogin", "password1");
        assertFalse(foundUser.isPresent());
    }
}
