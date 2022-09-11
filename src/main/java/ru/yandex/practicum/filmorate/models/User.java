package ru.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    @NotBlank(message = "E-mail не введен")
    @Email(message = "E-mail должен содержать символ @")
    private String email;

    @NotBlank(message = "Логин не введен")
    private String login;


    private String name;

    @Future
    private LocalDate birthday;

}
