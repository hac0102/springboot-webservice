package com.hac.springboot.config.auth;

import com.hac.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity          //스프링시큐리티 설정을 활성화 시킴
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()     //h2-console 화면을 사용하기 위해 해당옵션 disable
                    .and()
                        .authorizeRequests()            //URL별 권한관리를 설정하는 옵션의 시작점,authorizeRequests 선언되어야만 antMatchers옵션 사용 가능
                        .antMatchers("/", "/css/**", "/images/**"   //antMatchers 권한관리대상 지정
                                , "/js/**", "/h2-console/**").permitAll()       //permitAll 해당 URL 전체열람권한 줌
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // POST 메소드이면서 /api/v1/** 주소를 가진 API는 USER권한을 가진 사람만 가능
                        .anyRequest().authenticated()   //설정값들 나머지 이외 URL, authenticated 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용 (인증된 사용자=로그인사용자)
                    .and()
                        .logout()
                            .logoutSuccessUrl("/")  // 로그아웃 성공시 "/" 주소로 이동
                    .and()
                        .oauth2Login()
                            .userInfoEndpoint()     // 로그인 성공 이후 사용자 정보를 가져올때 설정
                                .userService(customOAuth2UserService);  //소셜 로그인 성공시 후속조지를 진행할 UserService 인터페이스의 구현체를 등록
                                                                        // 리소스서버(소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 할 수 있음


    }

}
