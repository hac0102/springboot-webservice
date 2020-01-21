package com.hac.springboot.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass                               // JPA Entity 클래스들이 BaseTimeEtity를 상속할 경우 선언한 변수들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)  //BaseTimeEntity 클래스에 Audition 기능을 포함시킴
public abstract class BaseTimeEntity {
    //JPA Auditiong 어노테이션을 모두 활성화 할 수 있도록 Application 클래스에 @EnableJpaAudition 추가
    //단순 반복적인 코드 JPA Auditing 기능 사용,  상속받아서 사
    @CreatedDate    //Entity가 생성되어 저장될때 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate   //조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}