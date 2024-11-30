package com.ravn.timewars.user.dao;

import com.ravn.timewars.user.persistence.User;

public interface UserDao {

    User getById(Long id);
}
