package service.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);

    int getAgeOfBook(Long id);

    boolean updateDatabse(Long id, int stock, String title);

    Book addNewBook(String author, String title, LocalDate date, int price, int quantity);

    void remove(Long id);

    boolean updateDatabasePS(Long id, int price, int stock);


}
