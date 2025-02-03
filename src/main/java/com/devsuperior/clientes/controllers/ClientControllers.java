package com.devsuperior.clientes.controllers;

import com.devsuperior.clientes.dto.ClientDTO;
import com.devsuperior.clientes.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientControllers {

    @Autowired
    private ClientService service;

    //GET ONE
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findByAll(Pageable pageable) {
        Page<ClientDTO> dto = service.findByAll(pageable);
        return ResponseEntity.ok(dto);
    }

}
