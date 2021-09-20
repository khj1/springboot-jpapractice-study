package jpabook.jpashop_practice.repository;

import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void 멤버_저장_조회() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(saveId);

        //then
        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getName()).isEqualTo(findMember.getName());

        // 영속성 컨텍스트는 같은 트랜잭션 안에서 식별자 값이 같으면 같은 엔티티로 인식한다.
        assertThat(findMember).isEqualTo(member);
    }
}