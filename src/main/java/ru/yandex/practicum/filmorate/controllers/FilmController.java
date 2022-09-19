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
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController{
    private InMemoryFilmStorage inMemoryFilmStorage;

    private FilmService filmService;

@Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @NotNull Film film) throws ValidationException {
        log.error("Ошибки валидации при добавление фильма");
        return inMemoryFilmStorage.addFilm(film);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody @NotNull Film film) throws ValidationException {
        log.error("Ошибки валидации при обновление фильма");
        return inMemoryFilmStorage.updateFilm(film);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film getFimByID(@PathVariable Long id) throws NotFoundException {
        return filmService.getFilmByID(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable Long id, @PathVariable Long userID){
        filmService.addLike(id,userID);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userID){
        filmService.deleteLike(id,userID);
    }

    @GetMapping("/films/popular?count={count}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getFavoriteFilms(@RequestParam int count){
        return filmService.getFavoriteFilms(count);
    }

}
