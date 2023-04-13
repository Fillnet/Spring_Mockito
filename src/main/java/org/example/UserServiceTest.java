package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllLogins() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        when(userRepository.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(Arrays.asList("user1", "user2"), userService.getAllLogins());
    }

    @Test
    public void testCreateNewUser() {
        String login = "newUser";
        String password = "newPassword";
        when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());

        userService.createUser(login, password);

        verify(userRepository).addUser(argThat(user -> user.getLogin().equals(login) && user.getPassword().equals(password)));
    }

    @Test
    public void testCreateNewUserWithEmptyLogin() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("", "password"));
    }

    @Test
    public void testCreateNewUserWithNullLogin() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null, "password"));
    }

    @Test
    public void testCreateNewUserWithEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("login", ""));
    }

    @Test
    public void testCreateNewUserWithNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("login", null));
    }

    @Test
    public void testCreateNewUserWithExistingLogin() {
        String login = "existingUser";
        when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(new User(login, "password")));

        assertThrows(UserNonUniqueException.class, () -> userService.createUser(login, "newPassword"));
    }

    @Test
    public void testLoginUserSuccess() {
        String login = "user";
        String password = "password";
        User user = new User(login, password);
        when(userRepository.findUserByLoginAndPassword(login, password)).thenReturn(Optional.of(user));

        assertTrue(userService.loginUser(login, password));
    }

    @Test
    public void testLoginUserFailure() {
        String login = "user";
        String password = "password";
        when(userRepository.findUserByLoginAndPassword(login, password)).thenReturn(Optional.empty());

        assertFalse(userService.loginUser(login, password));
    }
}
