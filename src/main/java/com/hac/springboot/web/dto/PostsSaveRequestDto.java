package com.hac.springboot.web.dto;

import com.hac.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    //@Builder
    //setter 없는 상황
    //기본적인 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입
    //생성자의 경우 지금 채워야 할 필드가 무엇인지 명확하지 않음
    // 어느 필드에 어떤값을 채워야할지 명확하게 인지 가능

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
