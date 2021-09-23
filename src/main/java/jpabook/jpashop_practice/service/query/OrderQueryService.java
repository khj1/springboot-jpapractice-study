package jpabook.jpashop_practice.service.query;

import jpabook.jpashop_practice.domain.Order;
import jpabook.jpashop_practice.domain.OrderItem;
import jpabook.jpashop_practice.repository.OrderRepository;
import jpabook.jpashop_practice.web.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> ordersV1() {

        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return all;
    }
}
