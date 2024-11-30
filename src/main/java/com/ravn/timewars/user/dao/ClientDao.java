package com.ravn.timewars.user.dao;

import com.ravn.timewars.user.persistence.Client;

public interface ClientDao {

    Client getById(Long id);
}
