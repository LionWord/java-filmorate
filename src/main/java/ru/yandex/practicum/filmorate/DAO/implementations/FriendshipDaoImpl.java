package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    JdbcTemplate jdbcTemplate;
    UserDaoImpl userDaoImpl;

    @Autowired
    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate, UserDaoImpl userDaoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public boolean sendFriendshipRequest(String senderEmail, String recipientEmail) {
        String sql = "insert into FRIENDSHIP_REQUESTS (RECIPIENT_EMAIL, SENDER_EMAIL) values (?, ?)";
        try {
            jdbcTemplate.update(sql, recipientEmail, senderEmail);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> acceptFriendshipRequest(String acceptorEmail, String requesterEmail) {
        String sql = "delete from FRIENDSHIP_REQUESTS where RECIPIENT_EMAIL = ? and SENDER_EMAIL = ?;" +
                "insert into FRIENDS (USER_EMAIL, FRIEND_EMAIL) values (?, ?)";
        try {
            jdbcTemplate.update(sql, acceptorEmail, requesterEmail, acceptorEmail, requesterEmail);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(acceptorEmail, requesterEmail);
    }

    @Override
    public Optional<List<User>> getAllUserFriends(String userEmail) {
        String sql = "select u.* from USER_INFO as u " +
                "inner join FRIENDS as f on f.USER_EMAIL = u.EMAIL " +
                "where u.EMAIL = (select f.FRIEND_EMAIL from f where USER_EMAIL = ?)";
        return Optional.ofNullable(jdbcTemplate.query(sql, (rs, rowNum) -> userDaoImpl.makeUser(rs), userEmail));
    }

    @Override
    public Optional<List<User>> getCommonFriends(String firstUserEmail, String secondUserEmail) {
        String sql = "select u.* from USER_INFO as u " +
                "inner join FRIENDS as f on f.USER_EMAIL = u.EMAIL " +
                "where u.EMAIL = (select f.FRIEND_EMAIL from f where USER_EMAIL = ?) " +
                "and u.EMAIL = (select f.FRIEND_EMAIL from f where USER_EMAIL = ?)";
        return Optional.ofNullable(jdbcTemplate.query(sql, (rs, rowNum) -> userDaoImpl.makeUser(rs), firstUserEmail, secondUserEmail));
    }
}
