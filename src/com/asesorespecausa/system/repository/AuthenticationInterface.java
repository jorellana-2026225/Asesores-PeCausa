package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.model.User;

public interface AuthenticationInterface {

    User login(String name, String password);

}