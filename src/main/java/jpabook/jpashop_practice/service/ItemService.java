package jpabook.jpashop_practice.service;

import jpabook.jpashop_practice.domain.Item;
import jpabook.jpashop_practice.repository.ItemRepository;
import jpabook.jpashop_practice.web.ItemDto;
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
    public void saveOrUpdate(Item item) {
        itemRepository.saveOrUpdate(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
