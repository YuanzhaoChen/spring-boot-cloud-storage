package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Check whether the name of the user can be registered to the database
     *
     * @param username
     * @return true if the username has not been registered, otherwise false
     */
    public boolean isUserNameAvailable(String username) {
        return this.userMapper.getUserByName(username) == null;
    }

    /**
     * Write user POJO to database
     *
     * @param user
     * @return user id in the database
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public User getUser(String username) {
        return this.userMapper.getUserByName(username);
    }

    public User getUserById(Integer userId) {
        return this.userMapper.getUserById(userId);
    }


}
