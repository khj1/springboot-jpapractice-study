package jpabook.jpashop_practice.api;

import jpabook.jpashop_practice.domain.Order;
import jpabook.jpashop_practice.repository.OrderRepository;
import jpabook.jpashop_practice.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop_practice.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop_practice.web.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 컬렉션이 아닌 단순 엔티티 연관관계( X to One )
 * - Order
 * - Order -> Member
 * - Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /*
    엔티티 원형을 API로 반환하면 안되는 이유
        1. 양방향 연관관계로 묶여있는 경우 무한루프가 발생한다.
            - @JsonIgnore로 두 연관관계 중 한곳을 API에서 호출되지 않도록 설정해주면 해결할 수 있다.
        2. 지연 로딩으로 설정되어있는 경우 프록시 객체가 API에 반환되므로 에러가 발생한다.
            - Hibernate5 Module을 설치하면 에러가 발생되진 않지만 프록시 객체들이 null로 반환된다.
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    /*
    ORDER 2건 조회
        ORDER 한건 당 회원 조회 + 배송 조회
        총 5번의 쿼리문이 나간다.( N + 1 문제 발생 )

    EAGER로 설정하면 최적화 되지 않을까? 안된다.
    EAGER로 설정하면 예상치 못한 쿼리들이 나간다.
        JPQL로 ORDER만 조회하면 먼저 ORDER 조회 쿼리문을 만든다.
        JPA는 뒤늦게 EAGER 설정을 확인하고 그제서야 EAGER 설정이 걸린 연관관계 데이터를 한꺼번에 호출한다.
     */
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());

        List<OrderSimpleQueryDto> result = orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderSimpleQueryDto> result = orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());

        return result;
    }

    /*
    V3보다 성능적으로 좀 더 최적화된 방식인건 맞다. 하지만 성능 차이가 미비하다.
    또한 해당 방식은 repository의 재사용성이 떨어지고 API 스펙에 맞춘 코드를 repository에서 사용해야하기때문에
    고민해야 할 문제가 많아진다.
        - 성능 최적화 쿼리용( DTO 바로 조회 등 ) 리포지토리를 따로 만드는 것도 방법이될 수 있다.
        - 기존 리포지토리는 엔티티만 반환하는 용도로 사용한다.

    트래픽이 굉장히 많고 데이터 필드가 너무 많다면 DTO를 JPQL에서 바로 조회하는 방식을
    고려해 볼 필요가 있다.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }



}
