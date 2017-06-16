package com.criminal.menabenyamen.taskr.HttpService;


import com.criminal.menabenyamen.taskr.http.HttpClient;
import com.criminal.menabenyamen.taskr.model.User;
import com.criminal.menabenyamen.taskr.repository.UserRepository;

import java.util.List;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public class UserHttpRepository extends HttpClient implements UserRepository {

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public long addOrUpdateUser(User user) {
        return 0;
    }

    @Override
    public User removeUser(long id) {
        return null;
    }
}
