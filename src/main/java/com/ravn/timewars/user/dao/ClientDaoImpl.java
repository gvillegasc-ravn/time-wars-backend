package com.ravn.timewars.user.dao;

import com.ravn.timewars.shared.exception.ResourceNotFoundException;
import com.ravn.timewars.user.persistence.Client;
import com.ravn.timewars.user.persistence.ClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDaoImpl implements ClientDao {

    private final ClientRepository clientRepository;

    public ClientDaoImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }
}
