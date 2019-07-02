package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserController {

    private final TheatreClient theatreClient;

    @Autowired
    public UserController(TheatreClient theatreClient) {
        this.theatreClient = theatreClient;
    }

    public void save(UserDto userDto) {
        theatreClient.saveUser(userDto);
    }

    public Boolean mailExist(String mail) {
        return theatreClient.mailExist(mail).isSuccess();
    }

    public Boolean loginSuccess(String mail, String password) {
        return theatreClient.loginSuccess(mail, password).isSuccess();
    }

    public UserDto getUserDtoByMail(String mail) {
        return theatreClient.getUserByMail(mail);
    }

    public List<UserDto> getUsers() {
        return theatreClient.getUsers();
    }
}
