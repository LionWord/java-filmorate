package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FilmorateApplicationTests {

	@Test
	void shouldReturnFalseIfUserIsValid() {
		User validUser = User.builder()
				.id(1)
				.birthday(LocalDate.of(1984, 12, 21))
				.email("ilovejava@yandex.ru")
				.name("Ivan")
				.login("Zeliboba")
				.build();
		assertFalse(UserController.isInvalidUserInput(validUser));
	}
	@Test
	void shouldReturnTrueIfEmailDontContainsAtSigh() {
		User noAtSignEmailUser = User.builder()
				.id(2)
				.birthday(LocalDate.of(1984, 12, 21))
				.email("azaza")
				.name("Andrew")
				.login("I_HATE_VALID_EMAILS")
				.build();
		assertTrue(UserController.isInvalidUserInput(noAtSignEmailUser));
	}
	@Test
	void shouldReturnTrueIfEmailIsEmpty() {
		User emptyEmailUser = User.builder()
				.id(4)
				.birthday(LocalDate.of(1999, 4, 3))
				.email("")
				.name("Eugen Blank")
				.login("E.Blank")
				.build();
		UserController.isInvalidUserInput(emptyEmailUser);
	}
	@Test
	void shouldReturnTrueIfLoginIsEmpty() {
		User emptyLoginUser = User.builder()
				.id(6)
				.birthday(LocalDate.of(1991, 1, 1))
				.email("iam@login.hater")
				.name("Olologin")
				.login("")
				.build();
		assertTrue(UserController.isInvalidUserInput(emptyLoginUser));
	}
	@Test
	void shouldReturnTrueIfLoginContainsSpaces() {
		User loginWithSpacesUser = User.builder()
				.id(5)
				.birthday(LocalDate.of(1947, 1, 8))
				.email("space@oddity.pun")
				.name("David Bowie")
				.login("Ground control to major Tom")
				.build();
		assertTrue(UserController.isInvalidUserInput(loginWithSpacesUser));
	}
	@Test
	void shouldReturnTrueIfUserIsFromTheFuture() {
		User futureBirthDateUser = User.builder()
				.id(3)
				.birthday(LocalDate.of(2029, 7, 11))
				.email("clothes@boots.motorcycle")
				.name("T800")
				.login("IllBeBack")
				.build();
		assertTrue(UserController.isInvalidUserInput(futureBirthDateUser));
	}
}
