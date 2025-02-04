package com.devsuperior.clientes.services;

import com.devsuperior.clientes.dto.ClientDTO;
import com.devsuperior.clientes.entities.Client;
import com.devsuperior.clientes.repositories.ClientRepository;
import com.devsuperior.clientes.services.exceptions.DataBaseExcepitions;
import com.devsuperior.clientes.services.exceptions.ResourseNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    //PUT
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getOne(id);
            copyDtotoEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity.getId(), entity.getName(), entity.getCpf(),
                    entity.getIncome(), entity.getBirthDate(), entity.getChildren());
        }catch (EntityNotFoundException e) {
            throw new ResourseNotFoundExceptions("Recurso nao encontrado");
        }
    }

    //DELETE
    @Transactional(propagation = Propagation.SUPPORTS) //propagation para DataIntegrityViolationException, pois h2
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourseNotFoundExceptions("Recurso nao encontrado");
        }
        try{
            repository.deleteById(id);
            //Caso tente apagar algum produto vinculado a algum pedido
        }catch (DataIntegrityViolationException e) {
            throw new DataBaseExcepitions("Falha na integridade referencial");
        }
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

