package com.hac.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복기능_테스트(){
        String name = "하창진";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);      //assertThat 테스트 검증 메소드, isEqualTo 동등비교메소드 같을때만 성공
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
