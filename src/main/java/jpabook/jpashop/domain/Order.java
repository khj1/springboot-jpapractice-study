package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 주문 입장에선 주문과 회원은 다대일의 관계
    @JoinColumn(name = "member_id") // 조인 외래 키값 설정
    private Member member;


    /*
    CascadeType.ALL 로 설정하면 데이터를 persist 하거나 delete 할 때
    한꺼번에 이루어진다.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
    일대일 관계에서는 외래키를 설정할 떄 어떻게 해야할까?
    일대일 관계에서는 더 많은 엑세스가 이루어지는 테이블에
    외래키를 설정해주는 것이 좋다.
    */
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;// JAVA 8 버전 이상

    @Enumerated(EnumType.STRING)
    private OrderStatus status;// 주문상태 [ ORDER, CANCEL ]


    //==연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//

    /**
     * OrderItem... orderItems는 가변인자를 사용한 것이다.
     *      -> OrderItem orderItem1, OrderItem orderItem2, ...
     *      이처럼 동일한 타입의 여러가지 매개변수를 축약해서 표현하는 방식이다.
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * orderItem을 관리하는 로직들이다.
     * orderItem은 따로 repository와 service가 없다.
     * 이는 OrderItem에 대한 권한이 Order에만 있도록 설계했기 떄문에
     * 외부에서 Order 만으로 OrderItem을 관리할 수 있도록 하기 위함이다.
     * 따라서 외부에서는 OrderItem에 대해서 몰라도 Order만 사용해서
     * 비즈니스 로직을 수행할 수 있다.
     */
    /**
     * 주문취소
     */
    public void cancel() {
        System.out.println("Order.cancel");
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    
    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
