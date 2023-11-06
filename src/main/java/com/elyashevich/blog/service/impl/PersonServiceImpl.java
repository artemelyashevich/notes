package com.elyashevich.blog.service.impl;

import com.elyashevich.blog.model.Person;
import com.elyashevich.blog.repository.PersonRepository;
import com.elyashevich.blog.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person create(Person person) {
        final Person newPerson = Person
                .builder()
                .username(person.getUsername())
                .password(person.getPassword())
                .build();
        return personRepository.save(person);
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @Override
    public Person findByUsername(String username) {
        return personRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public Person update(Person person) {
        final Person currPerson = personRepository.findByUsername(person.getUsername()).orElseThrow();
        currPerson.setPassword(person.getPassword());
        currPerson.setUsername(person.getUsername());
        return personRepository.save(currPerson);
    }

    @Override
    public void delete(Long id) {
        final Person person = personRepository.findById(id).orElseThrow();
        personRepository.delete(person);
    }
}
