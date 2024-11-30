package com.ravn.timewars.user.dao;

import com.ravn.timewars.shared.exception.ResourceNotFoundException;
import com.ravn.timewars.user.persistence.User;
import com.ravn.timewars.user.persistence.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
