package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    /**
     * Form( 또는 DTO )과 Entity로 분리한 이유
     *      폼값에서 받은 내용을 바로 Entity로 넘길 수 도 있다.
     *      하지만 실무에서는 Form에서의 요구사항이 훨씬 복잡하고 방대하다.
     *      그렇기 때문에 Form과 Entity를 분리하지 않으면 Entity에 점점
     *      화면과 관련된 로직들이 많아지고 유지 보수성이 떨어지게 된다.
     *
     *      따라서 JPA를 사용할 때 Entity를 최대한 순수하게 유지하는게 중요하다.
     *      반대로 DB에서 데이터를 화면에 뿌릴 때도 엔티티를 그대로 가져오기 보다는
     *      데이터를 DTO로 변환해서 가져오는 것이 좋다.
     *
     *          API를 작성할 때 Entity를 절대로 웹에 반환해서는 안된다.
     *          password 같은 민감한 데이터가 노출될 수 있을 뿐만 아니라.
     *          엔티티가 변경되었을 때 API 스펙도 변해버리게 된다.
     *          이로인해 불완전한 API 스펙이 되어버린다.
     */
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
