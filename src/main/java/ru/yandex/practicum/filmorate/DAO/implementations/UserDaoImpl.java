package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.UserDao;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.InvalidInputException;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdAssigner;
import ru.yandex.practicum.filmorate.utils.Messages;
import ru.yandex.practicum.filmorate.utils.SortingDirection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String sql = "insert into USER_INFO (EMAIL, LOGIN, USER_NAME, BIRTHDAY) values (?,?,?,?)";
        String returnSql = "select * from USER_INFO where EMAIL = ?";
        if (checkIfEmailIsInDatabase(user.getEmail())) {
            throw new AlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        }
        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        } catch (Exception e) {
            return null;
        }
        user.setId(jdbcTemplate.query(returnSql,(rs, rowNum) -> makeUser(rs), user.getEmail()).get(0).getId());
        System.out.println(user);
        return user;
    }

    @Override
    public void removeUser(User user) {
       removeUserById(user.getId());
    }

    @Override
    public User updateUser(User user) {
        String sql = "update USER_INFO set LOGIN = ?, USER_NAME = ?, BIRTHDAY = ?, EMAIL = ? where ID = ?";
        try {
            jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getBirthday(), user.getEmail(), user.getId());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public boolean removeUserById(int userID) {
        if (getUserById(userID).isEmpty()) {
            throw new NoSuchEntryException(Messages.NO_SUCH_USER);
        }
        String sql = "delete from USER_INFO where ID = ?";
        try {
            jdbcTemplate.update(sql, userID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<User> getUserById(int userID) throws EmptyResultDataAccessException {
        String sql = "select * from USER_INFO where ID = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs), userID));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<User> getAllUsers(int limit, String sortBy, String direction) {
        if (!direction.equalsIgnoreCase(SortingDirection.ASC.name())
                & !direction.equalsIgnoreCase(SortingDirection.DESC.name())) {
            throw new InvalidInputException(Messages.INVALID_INPUT);
        }
        String sql = "select * from USER_INFO order by ? " + SortingDirection.valueOf(direction) + " limit ?";
        return jdbcTemplate.query(sql,(rs, rowNum) -> makeUser(rs), sortBy, limit);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from USER_INFO";
        return jdbcTemplate.query(sql,(rs, rowNum) -> makeUser(rs));
    }
    @Override
    public User makeUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("ID"))
                .email(rs.getString("EMAIL"))
                .login(rs.getString("LOGIN"))
                .name(rs.getString("USER_NAME"))
                .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                .build();
    }

    private boolean checkIfEmailIsInDatabase(String email) {
        String sql = "select * from USER_INFO where EMAIL = ?";
        try {
            return jdbcTemplate.query(sql,(rs, rowNum) -> makeUser(rs), email).equals(null);
        } catch (Exception e) {
            return false;
        }
    }

}
