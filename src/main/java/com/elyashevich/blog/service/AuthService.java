package com.elyashevich.blog.service;

import com.elyashevich.blog.dto.PersonDto;
import com.elyashevich.blog.model.Person;

public interface AuthService {
    Person createNewPerson(PersonDto personDto);
    Person resetPassword(PersonDto personDto, String newPassword);
}
