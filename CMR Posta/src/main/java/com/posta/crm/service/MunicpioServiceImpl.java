
package com.posta.crm.service;

import com.posta.crm.entity.Municipio;
import com.posta.crm.repository.MunicipioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicpioServiceImpl implements IMunicipioService{

    @Autowired
    private MunicipioRepository municipioRepository ;
    
    @Override
    public List<Municipio> findAll() {
        return municipioRepository.findAll();
    }
    
}
