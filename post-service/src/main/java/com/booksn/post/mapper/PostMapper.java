package com.booksn.post.mapper;

import com.booksn.post.dto.response.PostResponse;
import com.booksn.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
