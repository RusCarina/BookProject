package repository.book;

import model.Book;

import java.time.LocalDate;

public abstract class BookRepositoryDecorator implements BookRepository{
    protected BookRepository decoratedRepository;

    public BookRepositoryDecorator(BookRepository bookRepository){
        this.decoratedRepository = bookRepository;
    }

    @Override
    public Book addNewBook(String author, String title, LocalDate date, int price, int quantity) {
        return null;
    }

    @Override
    public void remove(Long id) {}

}
