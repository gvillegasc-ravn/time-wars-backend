package com.ravn.timewars.timer.dao;

import com.ravn.timewars.shared.exception.ResourceNotFoundException;
import com.ravn.timewars.timer.persistence.Project;
import com.ravn.timewars.timer.persistence.ProjectRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    private final ProjectRepository projectRepository;

    public ProjectDaoImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }
}
