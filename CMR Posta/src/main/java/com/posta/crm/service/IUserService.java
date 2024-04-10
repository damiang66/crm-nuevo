
package com.posta.crm.service;

import com.posta.crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    public Page<User>findAll(Pageable pageable);
    public List<User> findAll();
    public Optional<User> findById(Long id);
    public User save(User user);
    public void activateDeactivate(Long id);
    public Optional<User> findByEmail(String email);
    
}
