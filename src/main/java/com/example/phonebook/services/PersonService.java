package com.example.phonebook.services;

import com.example.phonebook.entities.Person;
import com.example.phonebook.exceptions.CustomResultNotFoundNotFoundException;
import com.example.phonebook.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService extends AbstractService<Person> {
    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll().forEach(persons::add);
        return persons;
    }

    @Override
    public Person getById(Long id) {
        entityExists(id);
        return personRepository.findById(id).get();
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void update(Person person, Long id) {
        entityExists(id);
        person.setId(id);
        personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        try {
            entityExists(id);
            personRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Can't delete person, firstly delete phones associated with the person");
        }
    }

    @Override
    public void entityExists(Long id) {
        if (!personRepository.existsById(id))
            throw new CustomResultNotFoundNotFoundException("Person with id:" + id + " doesn't exists.");
    }


}
