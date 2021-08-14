package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    /*
     반드시 ENUM 타입을 String으로 설정해야한다!
     그래야 중간에 다른 Enum 값이 삽입되도 순서가 뒤바뀌는 일이 없다.
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
