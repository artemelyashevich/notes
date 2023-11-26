package com.elyashevich.blog.controller;

import com.elyashevich.blog.dto.PersonDto;
import com.elyashevich.blog.model.Person;
import com.elyashevich.blog.service.AuthService;
import com.elyashevich.blog.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PersonService personService;

    @GetMapping("/sign-up")
    public String signUp(final Model model) {
        PersonDto personDto = new PersonDto();
        model.addAttribute("title", "Sign Up");
        model.addAttribute("person", personDto);
        return "auth";
    }

    @PostMapping("/sign-up")
    public String createNewPerson(
            final @Valid @ModelAttribute("person") PersonDto personDto,
            final BindingResult result,
            final Model model
            ) {
        Person person = personService.findByUsername(personDto.getUsername());
        if (person != null && person.getUsername() != null && !person.getUsername().isEmpty()) {
            result.rejectValue(
                    "username",
                    null,
                    "There is already an account registered with the same username"
            );
        }
        if (result.hasErrors()) {
            model.addAttribute("person", personDto);
            return "auth";
        }
        authService.createNewPerson(personDto);
        return "redirect:/";
    }

    @GetMapping("/sign-in")
    public String signIn(final Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDto personDto = new PersonDto();
        model.addAttribute("title", "Sign In");
        model.addAttribute("person", personDto);
        return "auth";
    }

    @GetMapping("/reset-password")
    public String getResetForm(final Model model) {
        model.addAttribute("title", "Sign Up");
        return "/reset-password";
    }

    /*@PostMapping("/reset-password")
    public String resetPassword(
            final @RequestParam String username,
            final @RequestParam String oldPassword,
            final @RequestParam String newPassword
    ) {
        PersonDto personDto = new PersonDto(username, oldPassword);
        log.info("RESET PASS");
        authService.resetPassword(personDto, newPassword);
        return "reset-password";
    }*/
}
