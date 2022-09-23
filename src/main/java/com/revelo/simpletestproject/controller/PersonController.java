package com.revelo.simpletestproject.controller;

import com.revelo.simpletestproject.model.Person;
import com.revelo.simpletestproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public Long createPerson(@RequestBody Person person) {
        return personService.create(person);
    }

    @GetMapping("/person/{personId}")
    public Person retrievePersonById(@PathVariable Long personId) {
        return personService.retrieveById(personId);
    }

    @GetMapping("/person")
    public List<Person> retrievePersonList() {
        return personService.retrieveAll();
    }

    @PutMapping("/person/{personId}")
    public Long updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        return personService.update(personId, person) ? personId : null;
    }

    @DeleteMapping("/person/{personId}")
    public boolean deletePerson(@PathVariable Long personId) {
        return personService.delete(personId);
    }


}
