package jpabook.jpashop_practice.service;

import jpabook.jpashop_practice.domain.Book;
import jpabook.jpashop_practice.domain.Item;
import jpabook.jpashop_practice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    // 예제 단순화를 위해 이름, 가격, 재고 데이터만 변경하도록 설정
    @Transactional
    public void updateItem(Long itemId, Book book) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.updateItem(book);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
