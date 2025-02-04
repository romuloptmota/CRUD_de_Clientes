package com.devsuperior.clientes.services;

import com.devsuperior.clientes.dto.ClientDTO;
import com.devsuperior.clientes.entities.Client;
import com.devsuperior.clientes.repositories.ClientRepository;
import com.devsuperior.clientes.services.exceptions.ResourseNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    //GET All
    @Transactional(readOnly = true)
    public Page<ClientDTO> findByAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x.getId(), x.getName(), x.getCpf(),
                x.getIncome(), x.getBirthDate(), x.getChildren()));
    }

    //POST
    @Transactional
    public ClientDTO insert(ClientDTO dto) {
            Client entity = new Client();
            copyDtotoEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity.getId(), entity.getName(), entity.getCpf(),
                    entity.getIncome(), entity.getBirthDate(), entity.getChildren());
    }

    //AUX
    private void copyDtotoEntity(ClientDTO dto, Client entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

    }
}

