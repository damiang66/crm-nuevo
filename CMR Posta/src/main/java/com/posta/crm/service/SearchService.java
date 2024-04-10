/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.service;

import com.posta.crm.entity.Client;
import com.posta.crm.entity.User;
import com.posta.crm.repository.ClientRepository;
import com.posta.crm.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crowl
 */
@Service
public class SearchService {

    @Autowired
    private ClientRepository clienteRepository;

    @Autowired
    private UserRepository usuarioRepository;

    public List<String> search(String term) {
        List<String> results = new ArrayList<>();

        List<Client> clientes = clienteRepository.findByNameContainingIgnoreCase(term);
        for (Client cliente : clientes) {
            results.add(cliente.getName());
        }

        List<User> usuarios = usuarioRepository.findByNameContainingIgnoreCase(term);
        for (User usuario : usuarios) {
            results.add(usuario.getEmail());
        }

        return results;
    }
}
