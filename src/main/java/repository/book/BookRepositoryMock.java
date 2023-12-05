package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{

    private List<Book> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateDatabse(Long id, int stock, String title){
        return false;
    }

    @Override
    public Book addNewBook(String author, String title, LocalDate date, int price, int quantity) {
        return null;
    }

    @Override
    public void remove(Long id) {}

    @Override
    public boolean updateDatabasePS(Long id, int price, int stock) {
        return false;
    }

}
