package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    boolean save(Book book);

    void removeAll();

    boolean updateDatabse(Long id, int stock, String title);

    Book addNewBook(String author, String title, LocalDate date, int price, int quantity);

    void remove(Long id);

    boolean updateDatabasePS(Long id, int price, int stock);



}
