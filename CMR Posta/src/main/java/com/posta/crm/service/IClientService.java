
package com.posta.crm.service;

import com.posta.crm.entity.Client;
import com.posta.crm.enums.Gender;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface IClientService {
    
    public List<Client> findAll();
    public Optional<Client> findById(Long id);
    public Client save(Client client);
    public void activateDeactivate(Long id);
    public Page<Client> findByGender(Gender gender, Pageable pageable);
    public Page<Client> findByType(String type, Pageable pageable);
    public Page<Client>findByActive(Boolean active, Pageable pageable);
    public Page<Client>paginacion(Pageable pageable);
    public Page<Client>byCreateTime(Pageable pageable);
    public Page<Client>findByMunicipio(Long idMunicipio, Pageable pageable);
    public List<Client>findByName(String name);
    public Page<Client>findByRegdate(Pageable pageable);
    public List<Client>buscarPorDesactivado(boolean active);
    public Page<Client> buscarPorUsuario(Long id,Pageable pageable);
    public List<Client>findByRegdate();
}
