/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.controller;

import com.posta.crm.entity.Mensaje;
import com.posta.crm.entity.businessplan.BusinessPlan;
import com.posta.crm.service.MensajeServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author crowl
 */
@RestController
@RequestMapping("/mensaje")
@CrossOrigin(origins = "*")
public class MensajeControllador {
    
    @Autowired
    private MensajeServiceImpl mensajeServiceImpl;

    
    //Metodos POST
    @PostMapping("/save")
    public ResponseEntity<?> crearMensaje(@RequestBody Mensaje mensaje) {
        
        mensajeServiceImpl.save(mensaje);
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }
    
    //Metodos GET
    @GetMapping("/all")
    public ResponseEntity<?>findAll(){
        List<Mensaje>todos=mensajeServiceImpl.findAll();
        if (todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todos);
    }
        @GetMapping("/porId/{id}")
    public ResponseEntity<?>findAll(@PathVariable Long id){
        Mensaje encontrado=mensajeServiceImpl.findById(id).get();
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/porUser/{id}")
    public ResponseEntity<?>findAllUser(@PathVariable Long id){
        List<Mensaje>todosUser=mensajeServiceImpl.findByRemitenteId(id);
        if (todosUser != null) {
            return ResponseEntity.ok(todosUser);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> activateDeactivate(@PathVariable Long id) {
        Optional<Mensaje> find = mensajeServiceImpl.findById(id);
        if (find.isPresent()) {
            Mensaje mensaje = find.get();
            mensaje.setCondicion(false);
                mensajeServiceImpl.save(mensaje);
                return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }
}
