package com.crud.simplecrud.controller;

import com.crud.simplecrud.model.ContactModel;
import com.crud.simplecrud.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/contacts"})
public class ContactController {

    private ContactRepository repository;

    ContactController(ContactRepository contactRepository){
        this.repository = contactRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<ContactModel> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ContactModel create(
            @RequestBody ContactModel contactModel){
        return repository.save(contactModel);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactModel> update(
            @PathVariable("id") long id, @RequestBody ContactModel contactModel){
        return repository.findById(id)
                .map(record -> {
                    record.setName(contactModel.getName());
                    record.setEmail(contactModel.getEmail());
                    record.setPhone(contactModel.getPhone());
                    ContactModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
