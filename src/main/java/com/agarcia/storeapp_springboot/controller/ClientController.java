package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.ClientEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/list")
    public List<ClientEntity> getListClient(){
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ClientEntity getIdClient(@PathVariable long id){
        return clientRepository.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public ClientEntity createClient(@RequestBody ClientEntity client){
        return clientRepository.save(client);
    }

    @PutMapping("/update/{id}")
    public ClientEntity updateClient (@PathVariable long id, @RequestBody ClientEntity client){
        ClientEntity updatedClient = clientRepository.findById(id).get();
        updatedClient.setName(client.getName());
        updatedClient.setLastName(client.getLastName());
        updatedClient.setDni(client.getDni());
        return clientRepository.save(updatedClient);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable long id){
        ClientEntity deletedClient = clientRepository.findById(id).get();
        clientRepository.delete(deletedClient);
    }
}
