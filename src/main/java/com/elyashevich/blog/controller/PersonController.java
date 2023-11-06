package com.elyashevich.blog.controller;

import com.elyashevich.blog.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

}
