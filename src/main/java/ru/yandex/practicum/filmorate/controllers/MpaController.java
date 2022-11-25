package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    private final MpaDao mpaDao;

    @Autowired
    public MpaController(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    @GetMapping({"{id}"})
    public MPA getMPA(@PathVariable(value = "id") int mpaID) {
        return mpaDao.getMpaByID(mpaID);
    }

    @GetMapping
    public List<MPA> getAllMPA() {
        return mpaDao.getAllMPA();
    }

}
