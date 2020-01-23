package com.hac.springboot.web;

import com.hac.springboot.service.posts.PostsService;
import com.hac.springboot.web.dto.PostsResponseDto;
import com.hac.springboot.web.dto.PostsSaveRequestDto;
import com.hac.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //@PutMapping("/api/v1/posts") 으로 하면 안됌. 스프링부트 버전 차이?
    // http header content-type 설정 필요할듯?
    // json parse error 뜸
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PostMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}
