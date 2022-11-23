package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.MpaDao;
import ru.yandex.practicum.filmorate.exceptions.NoSuchEntryException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.utils.Messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MPA getMpaByID(int mpaID) {
        String sql = "select * from MPA_RATING where MPA_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeMpa(rs)), mpaID);
        } catch (Exception e) {
            throw new NoSuchEntryException(Messages.INVALID_MPA);
        }
    }

    public List<MPA> getAllMPA() {
        String sql = "select * from MPA_RATING";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeMpa(rs)));
    }


    private MPA makeMpa(ResultSet rs) throws SQLException {
        return MPA.builder()
                .id(rs.getInt("MPA_ID"))
                .name(rs.getString("RATING_NAME"))
                .build();
    }
}
