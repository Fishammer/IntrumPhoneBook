package com.example.phonebook.services;

import com.example.phonebook.entities.Phone;
import com.example.phonebook.exceptions.CustomResultNotFoundNotFoundException;
import com.example.phonebook.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneService extends AbstractService<Phone> {
    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public List<Phone> getAll() {
        List<Phone> phones = new ArrayList<>();
        phoneRepository.findAll().forEach(phones::add);
        return phones;
    }

    @Override
    public Phone getById(Long id) {
        entityExists(id);
        return phoneRepository.findById(id).get();
    }

    @Override
    public void save(Phone phone) {
        try {
            phoneRepository.save(phone);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Can't add a phone to a non-existing person");
        }
    }

    @Override
    public void update(Phone phone, Long id) {
        entityExists(id);
        phone.setId(id);
        phoneRepository.save(phone);
    }

    @Override
    public void delete(Long id) {
        entityExists(id);
        phoneRepository.deleteById(id);
    }

    @Override
    public void entityExists(Long id) {
        if (!phoneRepository.existsById(id))
            throw new CustomResultNotFoundNotFoundException("Phone with id: " + id + "doesn't exists.");
    }
}
