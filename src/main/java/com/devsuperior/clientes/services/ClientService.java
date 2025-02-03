package com.devsuperior.clientes.services;

import com.devsuperior.clientes.dto.ClientDTO;
import com.devsuperior.clientes.entities.Client;
import com.devsuperior.clientes.repositories.ClientRepository;
import com.devsuperior.clientes.services.exceptions.ResourseNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    //GET ONE
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id).orElseThrow(
                () -> new ResourseNotFoundExceptions("Recurso nao encontrado"));
        return new ClientDTO(client.getId(), client.getName(), client.getCpf(),
                client.getIncome(), client.getBirthDate(), client.getChildren());

    }
}
