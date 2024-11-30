package com.ravn.timewars.timer.dao;

import com.ravn.timewars.timer.persistence.Project;

public interface ProjectDao {

    Project getById(Long id);
}
