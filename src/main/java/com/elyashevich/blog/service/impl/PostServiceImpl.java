package com.elyashevich.blog.service.impl;

import com.elyashevich.blog.model.Post;
import com.elyashevich.blog.repository.PostRepository;
import com.elyashevich.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private static final String UPLOAD_DIRECTORY = "src\\main\\resources\\static\\images";

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post create(String title, String body, MultipartFile file) throws Exception {
        StringBuilder fileNames = new StringBuilder();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = null;

        try {
            path = Paths.get(UPLOAD_DIRECTORY + "\\" + fileName);
            log.info("\t\t\t\t\t\t\tFile" + path);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Post newPost = Post
                .builder()
                .title(title)
                .body(body)
                .imagePath(fileName.toString())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public Post findByTitle(String title) {
        return postRepository.findByTitle(title).orElseThrow();
    }

    @Override
    public Post update(Post post) {
        final Post post1 = postRepository.findById(post.getId()).orElseThrow();
        post1.setBody(post.getBody());
        post1.setTitle(post.getTitle());
        return postRepository.save(post1);
    }

    @Override
    public void delete(Long id) {
        final Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }
}
