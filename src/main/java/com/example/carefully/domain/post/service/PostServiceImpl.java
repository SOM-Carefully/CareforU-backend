package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.PostDto;
import com.example.carefully.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Long tempUserId = 1L;

    @Transactional
    public PostDto.CreateResponse createNewPost(PostDto.CreateRequest request, String postRole) {
        Post savedPost = postRepository.save(request.toEntity(PostRole.valueOf(postRole), tempUserId));
        return new PostDto.CreateResponse(savedPost.getId());
    }
}
