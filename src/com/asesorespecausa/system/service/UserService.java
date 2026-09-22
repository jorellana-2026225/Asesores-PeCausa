
package com.asesorespecausa.system.service;

import com.asesorespecausa.system.model.User;
import com.asesorespecausa.system.repository.UserRepository;

public class UserService {
    
    private UserRepository userRepo = new UserRepository();

    public User searchByName(String name) {
        return userRepo.searchByName(name);
    }
}
