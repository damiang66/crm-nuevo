
package com.posta.crm.repository;

import com.posta.crm.entity.Client;
import com.posta.crm.enums.Gender;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    
    @Query("SELECT a FROM Client a WHERE a.gender=:gender")
    public Page<Client> findByGender(@Param("gender") Gender gender, Pageable pageable);
    public Page<Client> findByType(String type, Pageable pageable);
    @Query("SELECT a FROM Client a WHERE a.active = true ORDER BY a.regdate DESC LIMIT 5")
    public List<Client>findByRegdate();
    public Page<Client>findByActive(Boolean active, Pageable pageable);
    

    @Query("SELECT a FROM Client a WHERE a.active = true ORDER BY a.regdate DESC")
    public Page<Client>findByRegdate(Pageable pageable);
    
    @Query("SELECT b FROM Client b WHERE b.municipio.id=?1")
    public Page<Client>findByMunicipio(@Param("idMunicipio")Long idMunicipio, Pageable pageable);
    
    @Query("SELECt a FROM Client a WHERE a.name LIKE %?1%")
    public List<Client>findByName(String name);

    public List<Client> findByNameContainingIgnoreCase(String term);
    
   @Query("select c from Client c where c.active=true")
    public List<Client> buscarPorActivos(Pageable pageable);
    
    @Query("select c from Client c where c.active=?1")
    public List<Client> buscarPorEstado(Boolean active);
    @Query("select c from Client c where c.user.id=?1")
    public Page<Client> buscarPorUsuario(Long id,Pageable pageable);
}
