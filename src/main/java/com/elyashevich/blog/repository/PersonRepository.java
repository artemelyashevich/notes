package com.elyashevich.blog.repository;


import com.elyashevich.blog.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);
}
