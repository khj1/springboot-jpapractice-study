package jpabook.jpashop_practice.domain;

import jpabook.jpashop_practice.web.BookForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
public class Book extends Item{
    private String author;
    private String isbn;

    //==생성 메서드==//
    public static Book createBook(BookForm form) {
        Book book = new Book();

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        return book;
    }
}
