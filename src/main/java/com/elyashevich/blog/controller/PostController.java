package com.elyashevich.blog.controller;

import com.elyashevich.blog.model.Post;
import com.elyashevich.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getAllPosts(final Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getOnePost(final @PathVariable(value = "id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/posts/create")
    public String createForm() {
        return "create-post";
    }

    @PostMapping("/posts/create")
    public String create(
            final @RequestParam String title,
            final @RequestParam String body,
            final @RequestParam("image") MultipartFile file) throws Exception {

        Post post = postService.create(title, body, file);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/edit/{id}")
    public String editForm(final @PathVariable Long id, final Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post-edit";
    }

    @PutMapping("/posts/edit/{id}")
    public String edit(
            final @PathVariable Long id,
            final @RequestParam String title,
            final @RequestParam String body,
            final @RequestParam MultipartFile image
    ) throws Exception {
        log.info("TITLE = " + title);
        log.info("FILE = " + image.getName());
        log.info("BODY = " + body.isEmpty());
        Post post = postService.update(id, title, body, image);
        return "redirect:/posts/" + post.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteOnePost(final @PathVariable(value = "id") Long id) {
        postService.delete(id);
        return "redirect:/";
    }
}
