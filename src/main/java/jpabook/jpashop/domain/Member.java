package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  래퍼클래스인 Long을 사용해주는 이유
 *      엔티티를 처음 new로 생성하는 시점에는 값이 없으므로 null인 상태가 필요합니다.
 *      따라서 기본형 long은 null 값을 가질 수 없으므로 참조형인 Long 을 사용합니다
 *
 *  Setter는 닫아주는 것이 좋다.
 *      왠만한 상황에선 생성자를 통해 값을 변경해주는 것이 좋은 방법이다.
 *      값을 변경해야 하는 일이 생긴다면 해당 로직에 관련된 메서드 명을 적절히
 *      사용하여 값을 변경해주는 메서드를 정의해주면 된다.
 *
 *      Setter를 닫아주는 이유에는 데이터를 함부로 변경하지 못하게 하기
 *      위함도 있지만 필드 변수명이 외부로 노출되는 것을 막기 위함도 있다.
 */
@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 연관 관계의 주인이 아님을 표시
    private List<Order> orders = new ArrayList<>();

}
