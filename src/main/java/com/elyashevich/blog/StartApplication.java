package com.elyashevich.blog;

import com.elyashevich.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class StartApplication {

    private final PostService postService;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}