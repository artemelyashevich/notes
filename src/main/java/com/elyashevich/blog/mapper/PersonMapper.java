package com.elyashevich.blog.mapper;

import com.elyashevich.blog.dto.PersonDto;
import com.elyashevich.blog.model.Person;

public class PersonMapper {
    public Person convertToPerson(PersonDto personDto) {
        return Person
                .builder()
                .password(personDto.getPassword())
                .username(personDto.getUsername())
                .build();
    }

    public PersonDto convertToPersonDto(Person person) {
        return null;
    }
}
