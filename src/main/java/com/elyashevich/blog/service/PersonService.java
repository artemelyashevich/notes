package com.elyashevich.blog.service;

import com.elyashevich.blog.model.Person;

public interface PersonService {
    Person create(Person person);
    Person findById(Long id);
    Person findByUsername(String username);
    Person update(Person person);
    void delete(Long id);
}
