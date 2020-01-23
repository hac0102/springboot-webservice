package com.hac.springboot.config.auth;

import com.hac.springboot.config.auth.dto.OAuthAttributes;
import com.hac.springboot.config.auth.dto.SessionUser;
import com.hac.springboot.domain.user.User;
import com.hac.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스를 구분하는 코드 , 이후 네이버/구글 로그인인지 구분하기위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가되는 필드값 Primary Key 의미
        //구글은 기본적으로 지원 기본코드 sub, 네이버 카카오는 기본 지원하지않음,  이후 네이버로그인 구글 로그인을 동시 지원할때사용
        String userNameAttributeNAme = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스, 다른 소셜 로그인도 이클래스를 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeNAme, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //세션에 사용자 정보를 저장하기 위한 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
