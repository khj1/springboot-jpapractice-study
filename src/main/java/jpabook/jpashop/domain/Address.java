package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /*
    Entity나 임베디드 타입은 생성할 때 setter가 없다면 자바 기본 생성자가 필요하다.
    따라서 public이 또는 protected 의 기본 생성자를 만들어준다.
    이는 JPA 구현 라이브러리 객체가 프록시나 리플렉션과 같은 기술을 사용할 수 있도록
    지원해야하기 때문이다.
     */
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
