package jpabook.jpashop_practice.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
}
