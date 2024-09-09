package com.booksn.post.service;

import com.booksn.post.dto.PageResponse;
import com.booksn.post.dto.request.PostRequest;
import com.booksn.post.dto.response.PostResponse;
import com.booksn.post.dto.response.UserProfileResponse;
import com.booksn.post.entity.Post;
import com.booksn.post.exception.AppException;
import com.booksn.post.exception.ErrorCode;
import com.booksn.post.mapper.PostMapper;
import com.booksn.post.repository.PostRepository;
import com.booksn.post.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;
    DateTimeFormatter dateTimeFormatter;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        post = postRepository.save(post);

        return postMapper.toPostResponse(post);
    }

    public PageResponse<PostResponse> getMyPosts(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        var userProfile = profileClient.getUserById(userId);

        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = postRepository.findAllByUserId(userId, pageable);

        String username = (userProfile != null && userProfile.getResult() != null) ? userProfile.getResult().getUsername() : null;

        var postList = pageData.getContent().stream().map(post -> {
            PostResponse postResponse = postMapper.toPostResponse(post);
            postResponse.setUsername(username);
            postResponse.setCreatedDateStr(dateTimeFormatter.formatDateTime(post.getCreatedDate()));
            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }
}
