package jpabook.jpashop_practice.web;

import jpabook.jpashop_practice.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    public static MemberForm create(Member member) {
        MemberForm memberForm = new MemberForm();
        memberForm.setName(member.getName());

        return memberForm;
    }
}
