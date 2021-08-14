package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * transaction 처리를 하면 test코드에서는 기본적으로 test가 완료된 후
 * 롤백된다. JPA는 구조적으로 transaction 이후 commit이 이루어져야
 * 영속성 컨텍스트 안에 있는 내용이 flush 되면서 DB에 해당 내용이
 * insert 혹은 update 된다.
 *
 * 따라서 해당 예제에서는 insert문을 눈으로 직접 확인하기 위해
 * rollback을 false로 처리하겠다
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 데이터 변경 시 문제가 생기면 롤백된다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); // 롤백 되기 전에 insert문을 확인하고 싶을 때
        assertEquals(member, memberRepository.findOne(saveId));

    }

    /**
     * (expected = IllegalStateException.class)를 작성해주면
     * 테스트에서 해당 예외가 떨어졌을 때 테스트가 성공처리 되게끔 한다
     */
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야한다.

        //then
        fail("예외가 발생해야 한다."); // 코드가 여기까지 도달하면 안된다!
    }
}