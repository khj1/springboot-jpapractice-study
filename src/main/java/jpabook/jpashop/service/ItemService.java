package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Transactional을 Service에서 처리해주는 이유
 *      비즈니스 로직은 보통 여러 repository를 호출한다.
 *      만약 해당 비즈니스 로직에 문제가 생긴다면, 해당 비즈니스 로직에 관련된
 *      부분을 모두 롤백해야한다. 그래서 일반적으로 비즈니스 로직의 시작점인
 *      서비스에 트랜잭션을 사용한다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // readOnly 일땐 저장이 안된다.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * EntityManager의 merge() 를 코드로 풀면 아래와 동일하게 동작한다.
     *      영속 엔티티의 값을 변경해주는 것 만으로도
     *      JPA 에서는 변경 감지가 이루어져 바뀐 데이터가 자동으로 업데이트 된다.
     *      ( 트랜잭션이 커밋될 때 dirtyChecking이 이루어져 자동으로 변경된다. )
     *
     * merge의 문제점
     *      기존 DB의 내용이 모두 변경된다는 문제점이있다.
     *      merge로 넘겨준 준영속 엔티티에서 예를들어 price 데이터가 빠져있으면
     *      기존 DB에 있던 price 데이터가 null 값으로 변경될 위험이 있다.
     *
     * 준영속 엔티티란?
     *      영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
     *      예를 들어 컨트롤러에서 임의로 엔티티를 생성하고, 해당 엔티티에 기존
     *      식별자를 삽입해주면 준영속 엔티티로 볼 수 있다.
     *      DB에서 바로 호출된 엔티티가 아니면 준영속 엔티티라고 봐도 무방한 것 같다.
     *
     * Entity를 변경할 때는 반드시 변경감지를 활용해라
     *      따라서 일반적인 경우에는 merge 보다는 귀찮더라도 변경감지를 활용해서
     *      원하는 데이터만 변경할 수 있게끔 설계해줘야 한다. 변경 설계를 할때 아래와 같이
     *      setter를 나열하는 방식은 좋은 방법이라고 할 수 없다. 정적 팩토리 메서드를
     *      활용해서 의미있는 메서드를 만들어 주는 것이 더 좋은 방법이다.
     *
     *      예) findItem.change(price, name, stockQuantity); -> 엔티티에서 정적 팩토리 메서드 정의
    */
    @Transactional
    public Item updateItem(Long itemId, int price, String name, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        return findItem;
    }

    public Item findOne(Long item_id) {
        return itemRepository.findOne(item_id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

}
