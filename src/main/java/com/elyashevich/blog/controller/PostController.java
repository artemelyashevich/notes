package com.elyashevich.blog.controller;

import com.elyashevich.blog.model.Post;
import com.elyashevich.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("title", "All Notes");
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getOnePost(final @PathVariable(value = "id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("title", "Note");
        return "post";
    }

    @GetMapping("/posts/create")
    public String createForm(final Model model) {
        model.addAttribute("title", "Crete new Note");
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
        model.addAttribute("title", "Edit Note");
        return "post-edit";
    }

    @PutMapping("/posts/edit/{id}")
    public String edit(
            final @PathVariable Long id,
            final @RequestParam String title,
            final @RequestParam String body,
            final @RequestParam MultipartFile image
    ) throws Exception {
        Post post = postService.update(id, title, body, image);
        return "redirect:/posts/" + post.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteOnePost(final @PathVariable(value = "id") Long id) {
        postService.delete(id);
        return "redirect:/";
    }
}
