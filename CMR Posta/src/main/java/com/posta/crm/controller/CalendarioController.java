/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.controller;

import com.posta.crm.entity.Calendario;
import com.posta.crm.repository.CalendarioRepository;
import com.posta.crm.service.CalendarioServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author crowl
 */
@RestController
@RequestMapping("/calendario")
@CrossOrigin(origins = "*")
public class CalendarioController {
    
    @Autowired
    private CalendarioServiceImpl calendarioServiceImpl;
    @Autowired
    private CalendarioRepository calendarioRepository;
    
    @GetMapping("/porUsuario/{id}")
    public ResponseEntity<?>findAllUser(@PathVariable Long id){
       List<Calendario> encontrada=calendarioServiceImpl.findByUserId(id);
       if(encontrada!=null){
           return ResponseEntity.ok(encontrada);
       }
       return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/save")
    public ResponseEntity<?>crearEvento(@RequestBody Calendario calendario){
        return ResponseEntity.ok(calendarioServiceImpl.save(calendario));
        
    }
    @PutMapping("update/{id}")
     public ResponseEntity<?>actualziarEvento(@RequestBody Calendario calendario,@PathVariable Long id){
        Calendario calendarioUpdate=calendarioRepository.findById(id).get();
        if(calendario!=null){
            calendarioUpdate.setContenido(calendario.getContenido());
            calendarioUpdate.setTitulo(calendario.getTitulo());
            calendarioUpdate.setFecha(calendario.getFecha());
            return ResponseEntity.ok(calendarioServiceImpl.save(calendarioUpdate));
        }
        return ResponseEntity.notFound().build();
        
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>eliminarEvento( @PathVariable Long id){
        Calendario calendarioUpdate=calendarioRepository.findById(id).get();
        if(calendarioUpdate!=null){
            return ResponseEntity.ok(calendarioUpdate);
        }
        return ResponseEntity.notFound().build();
        
    }
    
}
