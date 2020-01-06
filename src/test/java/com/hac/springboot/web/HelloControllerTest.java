package com.hac.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    //테스트를 진행할때 Junit에 내장된 실행자 외에 다른 실행자를 실행시킴 springrunner라는 스프링 실행자를 사용, => 스프링 부트와 junit 사이의 연결자 역할
@WebMvcTest                     //여러 스프링 테스트 @중 web집중, @Controller, @ControllerAdvice 사용가능. 단, @Service @Component @Repository 사용못함
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;        //웹 api 테스트할때 사용, 스프링mvc 시작점, http get post 등에대한 api 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))           //체이닝 지원 여러검증 기능을 이어서 선언 가능
                .andExpect(status().isOk())             //mvc perform 결과 검증 200,404, 500 상태 검증
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)            //api테스트할때 요청 파라미터 설정, string만 가능, 숫자/날짜 모두 string으로 변경해야 가능
                        .param("amount", String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(name)))    //jaonPath json 응답값을 필드별로 검증할수있는 메소드, $기준으로 필드명 명시
            .andExpect(jsonPath("$.amount", is(amount)));

    }
}

