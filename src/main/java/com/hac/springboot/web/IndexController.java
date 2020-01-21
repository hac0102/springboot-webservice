package com.hac.springboot.web;

import com.hac.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    // 머스테치 스타터 때문에 문자열로 반환할때 자동으로 앞의 경로와 .mustache 확장자가 붙음 View Resolver 처리
    // view resolver Url 요청의 결과를 전달할 타입과 값을 지정하는 관리자 격
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
