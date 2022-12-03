package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class MpaService {

    private final MpaStorage mpaStorage;

    public MPA getMpaByID(int mpaID) {
        return mpaStorage.getMpaByID(mpaID);
    }

    public List<MPA> getAllMPA() {
        return mpaStorage.getAllMPA();
    }

}
