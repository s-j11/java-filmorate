package ru.yandex.practicum.filmorate.controllers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController{
    private FilmService filmService;
@Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> findAll() {
        return filmService.getInMemoryFilmStorage().findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @NotNull Film film) throws ValidationException {
        log.error("Ошибки валидации при добавление фильма");
        return filmService.getInMemoryFilmStorage().addFilm(film);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody @NotNull Film film) throws ValidationException {
        log.error("Ошибки валидации при обновление фильма");
        return filmService.getInMemoryFilmStorage().updateFilm(film);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film getFimByID(@PathVariable Long id) throws NotFoundException {
        return filmService.getFilmByID(id);
    }
    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable Long id, @PathVariable Long userId) throws ValidationException{
        filmService.addLike(id,userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) throws NotFoundException,
            ValidationException {
        filmService.deleteLike(id,userId);
    }
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getFavoriteFilms(@RequestParam(defaultValue = "10") long count) throws NotFoundException{
        return filmService.getFavoriteFilms(count);
    }

}
