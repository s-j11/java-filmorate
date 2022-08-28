package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    Map<Integer, Film> films = new HashMap<>();
    Film film = new Film();

    @BeforeEach
    public void fillStorage(){
        film.setId(1);
        film.setName("Титаник");
        film.setDescription("Драма");
        film.setReleaseDate(LocalDate.of(1997,1,1));
        film.setDuration(195);
        films.put(film.getId(),film);
    }

    @Test
    public void shouldReturnAllFilms() throws Exception {
        Map<Integer,Film> filmsTest = new HashMap<>();
        Film filmTest = new Film();
        filmTest.setId(1);
        filmTest.setName("Титаник");
        filmTest.setDescription("Драма");
        filmTest.setReleaseDate(LocalDate.of(1997,1,1));
        filmTest.setDuration(195);
        filmsTest.put(filmTest.getId(),filmTest);
        Assertions.assertEquals(filmsTest,films);
        filmsTest.clear();
        Assertions.assertNotEquals(filmsTest,films);
        films.clear();
        Assertions.assertEquals(filmsTest,films);
    }

    @Test
    public void  shouldAddFilms(){
        LocalDate dateTest = LocalDate.of(1895,12,28);
        Map<Integer,Film> filmsTest = new HashMap<>();
        Film filmTest = new Film();
        filmTest.setId(1);
        filmTest.setName("Титаник");
        filmTest.setDescription("Драма");
        filmTest.setReleaseDate(LocalDate.of(1997,1,1));
        filmTest.setDuration(195);
        filmsTest.put(filmTest.getId(),filmTest);
        Film film1 =films.get(1);
        assertAll(
                ()->assertEquals(filmsTest, films),
                ()->assertNotEquals(null, film1.getName()),
                ()->assertTrue(film1.getDescription().length() <= 200),
                ()->assertTrue(film1.getReleaseDate().isAfter(dateTest)),
                ()->assertTrue(film1.getDuration()>0));
    }

    @Test
    public void  shouldUpdateFilms() {
        LocalDate dateTest = LocalDate.of(1895, 12, 28);
        Map<Integer, Film> filmsTest = new HashMap<>();
        Film filmTest = new Film();
        filmTest.setId(1);
        filmTest.setName("Титаник");
        filmTest.setDescription("Драма");
        filmTest.setReleaseDate(LocalDate.of(1997, 1, 1));
        filmTest.setDuration(195);
        filmsTest.put(filmTest.getId(), filmTest);
        Film film1 = films.get(1);
        assertAll(
                () -> assertEquals(filmsTest, films),
                () -> assertNotEquals(null, film1.getName()),
                () -> assertTrue(film1.getDescription().length() <= 200),
                () -> assertTrue(film1.getReleaseDate().isAfter(dateTest)),
                () -> assertTrue(film1.getDuration() > 0));
    }
}

