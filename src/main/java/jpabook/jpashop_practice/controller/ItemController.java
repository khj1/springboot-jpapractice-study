package jpabook.jpashop_practice.controller;

import jpabook.jpashop_practice.domain.Book;
import jpabook.jpashop_practice.domain.Item;
import jpabook.jpashop_practice.service.ItemService;
import jpabook.jpashop_practice.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form, Model model) {
        Book book = Book.createBook(form);
        itemService.save(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book book = (Book) itemService.findOne(itemId);
        BookForm.createBookForm(book);
        model.addAttribute("form", book);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, BookForm form) {
        Book book = Book.createBook(form);
        itemService.updateItem(itemId, book);
        return "redirect:/items";
    }
}
