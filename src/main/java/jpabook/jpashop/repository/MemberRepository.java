package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    /**
     * 해당 코드는 스프링 부트에서 생성자 주입 방식으로 변경할 수 있다.
     *      RequiredArgsConstructor 어노테이션 를 붙인 후
     *      private final EntityManager em;으로 변경
     */
    @PersistenceContext // 스프링이 EntityManager을 만들어서 주입해주게 한다.
    private EntityManager em;

    /*
    persist(member)를 하면 member가 영속성 컨텍스트 위에 올려진다.
    영속성 컨텍스트는 key, value 값을 가지는데 여기서 key 값은 해당
    entity의 primary key 값이 된다.( 여기선 member_id )
     */
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /*e
    JQPL은 SQL과 기본적으로 기능이 동일하다. 어차피 JPQL이 SQL로 해석되기 떄문!
    차이점은 SQL은 테이블에 바로 적용되지만 JPQL은 Entity 객체에 바로 적용된다,
     */
    public List<Member> findAll() { // ( 쿼리, 반환 타입 )
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
