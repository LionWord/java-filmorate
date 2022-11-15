package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.UserDao;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String sql = "insert into USER_INFO (EMAIL, LOGIN, USER_NAME, BIRTHDAY) values (?,?,?,?)";
        if (!getUserByEmail(user.getEmail()).isEmpty()) {
            throw new AlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        }
        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getUsername(), user.getBirthday());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public void removeUser(User user) {
       removeUserByEmail(user.getEmail());
    }

    @Override
    public User updateUser(User user) {
        String sql = "update "
        return null;
    }

    @Override
    public boolean removeUserByEmail(String userEmail) {
        if (getUserByEmail(userEmail).isEmpty()) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        String sql = "delete from USER_INFO where EMAIL = ?";
        try {
            jdbcTemplate.update(sql, userEmail);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User updateUserByEmail(String userEmail) {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String userEmail) {
        return null;
    }

    @Override
    public List<User> getAllUsers(int limit, String direction) {
        return null;
    }
}
