package com.hac.springboot.service.posts;

import com.hac.springboot.domain.posts.Posts;
import com.hac.springboot.domain.posts.PostsRepository;
import com.hac.springboot.web.dto.PostsListResponseDto;
import com.hac.springboot.web.dto.PostsResponseDto;
import com.hac.springboot.web.dto.PostsSaveRequestDto;
import com.hac.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    // bean 주입방식 @Autowired 권장 하지 않음
    // 생성자로 Bean 객체를 받오록하면 @Autowired 동일함,  생성자는 클래스에 @RequiredArgsConstructor이 대신 생성 해줌
    // @RequiredArgsConstructor -- final이 선언된 모든 필드를 인자값으로 하는 생성자를  생성해줌
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. ID = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //readOnly 트랜잭션 범위는 유지하고 조회기능만 유지->조회속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)     //.map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID = " + id));
        postsRepository.delete(posts);
    }


}
