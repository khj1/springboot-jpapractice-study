package jpabook.jpashop_practice.web;

import jpabook.jpashop_practice.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm { //예제 단순화를 위해 책만 판다고 가정한다.

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    //==생성 메서드==//
    public static BookForm createBookForm(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        return form;
    }
}
