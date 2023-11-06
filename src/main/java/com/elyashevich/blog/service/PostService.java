package com.elyashevich.blog.service;

import com.elyashevich.blog.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post create(String title, String body, MultipartFile file) throws Exception;
    Post findById(Long id);
    Post findByTitle(String title);
    Post update(Post post);
    void delete(Long id);
}
