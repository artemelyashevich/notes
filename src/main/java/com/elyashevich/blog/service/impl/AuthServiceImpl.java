package com.elyashevich.blog.service.impl;

import com.elyashevich.blog.dto.PersonDto;
import com.elyashevich.blog.model.Person;
import com.elyashevich.blog.model.Role;
import com.elyashevich.blog.repository.PersonRepository;
import com.elyashevich.blog.repository.RoleRepository;
import com.elyashevich.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public Person createNewPerson(PersonDto personDto) {
        Person person = Person
                .builder()
                .username(personDto.getUsername())
                .build();
        person.setPassword(passwordEncoder.encode(personDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        person.setRoles(Collections.singletonList(role));
        return personRepository.save(person);
    }

    @Override
    public Person resetPassword(PersonDto personDto, String newPassword) {
        Person person = personRepository.findByUsername(personDto.getUsername());
        if (passwordEncoder.matches(personDto.getPassword(), person.getPassword())) {
            person.setPassword(passwordEncoder.encode(newPassword));
        } else {
            log.info(String.format("\t\t\tPassword %s != %s", personDto.getPassword(), person.getPassword()));
        }
        return personRepository.save(person);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}