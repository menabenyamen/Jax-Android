package com.criminal.menabenyamen.taskr.repository;


import com.criminal.menabenyamen.taskr.model.User;

import java.util.List;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public interface UserRepository {

    List<User> getUsers();
    User getUser(long id);
    long addOrUpdateUser(User user);
    User removeUser(long id);
}
