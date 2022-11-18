package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.implementations.UserDbStorage;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateApplicationTests {
    private final UserDbStorage userStorage;

    @Test
    public void testFindUserById() {

        Optional<User> userOptional = Optional.of(userStorage.getUser(1));

        Assertions.assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}