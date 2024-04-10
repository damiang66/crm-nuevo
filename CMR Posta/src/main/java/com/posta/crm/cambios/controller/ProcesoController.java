package com.posta.crm.cambios.controller;



import com.posta.crm.cambios.entity.Proceso;

import com.posta.crm.cambios.service.ProcesoService;
import com.posta.crm.entity.Client;
import com.posta.crm.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/procesos")
public class ProcesoController {
    @Autowired
    private ProcesoService procesoService;



    @Autowired
   private ClientServiceImpl clientService;


    @GetMapping

    public ResponseEntity<?>findAll(){
        return ResponseEntity.ok().body(procesoService.findAll());

    }
    @GetMapping("/ultimo")
    public ResponseEntity<?>findAllUltimo(){
        return ResponseEntity.ok().body(procesoService.findAllUltimo());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>findById(@PathVariable String id){
        Optional<Proceso> optionalProcess = procesoService.findById(id);
        if(optionalProcess.isPresent()){
            return ResponseEntity.ok().body(optionalProcess.get());
        }
        return  ResponseEntity.notFound().build();
    }
    @GetMapping("/buscarPorNombre/{termino}")
    public ResponseEntity<?>buscarPorNombre(@PathVariable String termino){
        return ResponseEntity.ok().body(procesoService.findByNombreCliente(termino));
    }
    @GetMapping("/buscarPorType/{type}")
    public ResponseEntity<?>buscarPorTipo(@PathVariable String type){
        return ResponseEntity.ok().body(procesoService.findByTypeCliente(type));
    }
    @GetMapping("/buscarPorTerminado/{terminado}")
    public ResponseEntity<?>buscarPorTermiando(@PathVariable Boolean terminado){
        return ResponseEntity.ok().body(procesoService.findByTerminado(terminado));
    }
    @GetMapping("/buscarPorEstado/{estado}")
    public ResponseEntity<?>buscarPorEstado(@PathVariable String estado){
        return ResponseEntity.ok().body(procesoService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<?>save(@RequestBody Proceso proceso){
        System.out.println("entroooo");
        Optional<Client> cliente = clientService.findById(proceso.getCliente().getId());
        if ( cliente.isPresent()){
            proceso.setCliente(cliente.get());
            proceso.setIdCliente(cliente.get().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(procesoService.save(proceso));

        }
        return ResponseEntity.notFound().build();

    }


    @PutMapping("/editar/procesoEmprendedor")
    public ResponseEntity<?>updateProcesoEmprendedor(@RequestBody Proceso proceso){
        System.out.println(proceso);
        Optional<Proceso> procesoOptional = procesoService.findById(proceso.getId());
        Proceso procesoDb = null;
        if(procesoOptional.isPresent()){
            procesoDb = procesoOptional.get();
            procesoDb.setEstadoDelProceso(proceso.getEstadoDelProceso());
            procesoDb.setProcesoEmprendedor(proceso.getProcesoEmprendedor());

            return ResponseEntity.status(201).body(procesoService.save(procesoDb));
        }
        return ResponseEntity.notFound().build();

    }


    @PutMapping("/editar/procesoEmpresario")
    public ResponseEntity<?>updateProcesoEmpresario(@RequestBody Proceso proceso){
        Optional<Proceso> procesoOptional = procesoService.findById(proceso.getId());
        Proceso procesoDb = null;
        if(procesoOptional.isPresent()){
            procesoDb = procesoOptional.get();
            procesoDb.setEstadoDelProceso(proceso.getEstadoDelProceso());
            procesoDb.setProcesoEmpresario(proceso.getProcesoEmpresario());

            return ResponseEntity.status(201).body(procesoService.save(procesoDb));
        }
        return ResponseEntity.notFound().build();

    }

}
