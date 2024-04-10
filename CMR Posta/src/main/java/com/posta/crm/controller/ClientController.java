package com.posta.crm.controller;

import com.posta.crm.entity.Actividades;
import com.posta.crm.entity.Client;

import com.posta.crm.entity.Municipio;
import com.posta.crm.entity.SelfAssessment;
import com.posta.crm.entity.canvas.CanvasModel;
import com.posta.crm.enums.Gender;
import com.posta.crm.enums.TypeOfCompany;
import com.posta.crm.repository.ActividadesRepository;
import com.posta.crm.service.ClientServiceImpl;
import com.posta.crm.service.SelfAssessmentImpl;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins= "*")
public class ClientController {

    @Autowired
    private ClientServiceImpl clienteService;
    @Autowired
    private SelfAssessmentImpl selfAssessmentService;
    @Autowired
    private ActividadesRepository actividadesRepository;
   

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> errores = new HashMap();
        result.getFieldErrors().forEach(e -> {
            errores.put(e.getField(), "el campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }
    
    
    @ApiOperation(value = "Guardar usuario tipo Cliente")
    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody Client client, BindingResult result) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        if(client.getType().equalsIgnoreCase("entrepreneur")){
            CanvasModel canvasModel= new CanvasModel();
           // client.setCanvasModel(canvasModel);
        }
        LocalDate fechaActual=LocalDate.now();
        int edad=Period.between(client.getFechaNacimiento(), fechaActual).getYears();
        client.setEdad(edad);
        clienteService.save(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }


    
    @ApiOperation(value = "Listar todos los Clientes, paginación de 8")
    @GetMapping("/paginar/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page) {
        Pageable pageable= PageRequest.of(page,10);
        Page<Client>clients=clienteService.findByRegdate(pageable);
        
//        List<Client> reversedClients = new ArrayList<>(clients.getContent());
//        Collections.reverse(reversedClients);
//        Page<Client> reversedPage = new PageImpl<>(reversedClients, pageable, clients.getTotalElements());
        
        if(clients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    @ApiOperation(value = "Busca cliente por Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Client client = clienteService.findById(id).get();
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @ApiOperation(value = "Filtra clientes por género, paginación de 10")
    @GetMapping("/gender/{page}")
    public ResponseEntity<?> findByGender(@RequestParam("gender")Gender gender, @PathVariable Integer page) {
        Pageable pageable=PageRequest.of(page, 10);
        Page<Client> genders = clienteService.findByGender(gender, pageable);
        
        List<Client> reversedClients = new ArrayList<>(genders.getContent());
        Collections.reverse(reversedClients);
        Page<Client> reversedPage = new PageImpl<>(reversedClients, pageable, genders.getTotalElements());
        
        if (genders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reversedPage);

    }

    @ApiOperation(value = "Fltra clientes por tipo, paginacipon de 10")
    @GetMapping("/type/{page}")
    public ResponseEntity<?> findByType(@RequestParam("type") String type, @PathVariable Integer page) {
        Pageable pageable=PageRequest.of(page, 10);
        Page<Client>clients=clienteService.findByType(type, pageable);
        
        List<Client> reversedClients = new ArrayList<>(clients.getContent());
        Collections.reverse(reversedClients);
        Page<Client> reversedPage = new PageImpl<>(reversedClients, pageable, clients.getTotalElements());
        
        if(clients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
                
        return ResponseEntity.ok(reversedPage);

    }
    
    @ApiOperation(value = "Filtra clientes por su estado activo o inactivo, paginación de 10")
    @GetMapping("/state/{page}")
    public ResponseEntity<?> findByState(@RequestParam("active") Boolean active,@PathVariable Integer page) {
        Pageable pageable=PageRequest.of(page, 10);
        Page<Client> clients = clienteService.findByActive(active, pageable);
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);

    }
 
    @ApiOperation(value = "Filtra clientes por fecha de creación en forma descendiente, paginación de 6")
    @GetMapping("/byTime/{page}")
    public ResponseEntity<?>findByTime(@PathVariable Integer page){
        Pageable pageable=PageRequest.of(page, 6);
        Page<Client>clients=clienteService.byCreateTime(pageable);
        
        List<Client> reversedClients = new ArrayList<>(clients.getContent());
        Collections.reverse(reversedClients);
        Page<Client> reversedPage = new PageImpl<>(reversedClients, pageable, clients.getTotalElements());
        
        if(clients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reversedPage);
        
    }
    
    @ApiOperation(value = "Filtra clientes por ciudad, paginación de 10")
    @GetMapping("/municipios/{page}")
    public ResponseEntity<?>findByCity(@RequestParam("idMunicipio") Long idMunicipio, @PathVariable Integer page){
        Pageable pageable=PageRequest.of(page, 10);
        Page<Client> clients = clienteService.findByMunicipio(idMunicipio, pageable);
        
        List<Client> reversedClients = new ArrayList<>(clients.getContent());
        Collections.reverse(reversedClients);
        Page<Client> reversedPage = new PageImpl<>(reversedClients, pageable, clients.getTotalElements());
        
        if(clients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reversedPage);
    }
    
    @GetMapping("/municipios")
    public ResponseEntity<?>findAllMunicipios(){
        List<Municipio>municipios=clienteService.findByAllMunicipios();
        if(municipios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(municipios);
    }

    @ApiOperation(value = "Modifica cliente tipo empresario")
    @PutMapping("/client/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validation(result);
        }

        return ResponseEntity.ok(clienteService.update(client, id));
    }

//    @ApiOperation(value = "Mnodifica clientes tipo emprendedor")
//    @PutMapping("/entrepreneur/{id}")
//    public ResponseEntity<?> updateBusinessman(@Valid @RequestBody Entrepreneur client, BindingResult result, @PathVariable Long id) {
//        if (result.hasErrors()) {
//            return this.validation(result);
//        }
//
//        return ResponseEntity.ok(clienteService.update(client, id));
//    }

    @ApiOperation(value = "Activa o desactiva clientes dependiendo de su condición")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> activateDeactivate(@PathVariable Long id) {
        Optional<Client> find = clienteService.findById(id);
        if (find.isPresent()) {
            Client client = find.get();
            if (client.getActive()) {
                client.setActive(false);
                clienteService.save(client);
                return ResponseEntity.ok(client);
            } else {
                client.setActive(true);
                clienteService.save(client);
                return ResponseEntity.ok(client);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/selfAssessment")
    public ResponseEntity<?>saveSelfAssessment(@Valid @RequestBody SelfAssessment selfAssessment, BindingResult result){
        if (result.hasErrors()) {
            return this.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(selfAssessmentService.save(selfAssessment));
    }
    
    //GET enums
    @GetMapping("/enums")
    public ResponseEntity<?>enums(){
       
        TypeOfCompany[] valores= TypeOfCompany.values();
        
        return ResponseEntity.ok(valores);
    
}
    
    //Barra de Busqueda
    @GetMapping("/search/{name}")
    public ResponseEntity<?>search(@PathVariable String name){
        List<Client> found= clienteService.findByName(name);
        if(found.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }

    //GET CIIU
    @GetMapping("/actividades")
    public ResponseEntity<?>findCiiu(){
        
        List<Actividades>activ=actividadesRepository.findAll();
        if(activ.isEmpty()){
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(activ);
    }
    
    
    @GetMapping("/listar")
    public ResponseEntity<?>findLL(){

        return ResponseEntity.ok(clienteService.findAll());
    }
    @GetMapping("/activos/{active}")
    public ResponseEntity<?>buscarPorEstado(@PathVariable Boolean active){
        return ResponseEntity.ok(clienteService.buscarPorDesactivado(active));
    }

    @GetMapping("/usuario/{id}/page/{page}")
    public ResponseEntity<?>buscarPorUsuario(@PathVariable Long id, @PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Client>clientes = clienteService.buscarPorUsuario(id,pageable);
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/creacion")
    public ResponseEntity<?>porCreacion(){

        List<Client> clients = clienteService.findByRegdate();

        //List<Client> reversedClients = new ArrayList<>(clients);
      //  Collections.reverse(reversedClients);


        return ResponseEntity.ok(clients);
        //return ResponseEntity.ok(reversedClients);

    }


}
