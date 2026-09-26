package com.asesorespecausa.system.service;


import com.asesorespecausa.system.repository.AuthenticationRepository;
import com.asesorespecausa.system.model.User;

public class AuthenticationService {

    private UserService userService = new UserService();
    private User usuarioAutenticado;

    public AuthenticationStatus tryLogin(String name, String password) {
        User userFound = userService.searchByName(name);
        this.usuarioAutenticado = userFound;

        if (userFound == null) {
            return AuthenticationStatus.NOT_EXIST_USER;
        } else {
            return AuthenticationStatus.LOGIN_SUCCESS;
        }
    }

    public User getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}