package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BookForm {

    /**
     * 상품의 공통 속성
     */
    // 상품 수정을 위해서 id 값이 있어야 한다.
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;


    /**
     * 책과 관련된 속성
     */
    private String author;
    private String isbn;

}
