import database.DatabaseConnectionFactory;
import model.AudioBook;
import model.Book;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.audiobook.AudioBookRepository;
import repository.audiobook.AudioBookRepositoryCacheDecorator;
import repository.audiobook.AudioBookRepositoryMySQL;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.Cache;
import repository.ebook.EBookRepository;
import repository.ebook.EBookRepositoryCacheDecorator;
import repository.ebook.EBookRepositoryMySQL;
import service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        BookService bookService = new BookServiceImpl(bookRepository);


        Book book = new BookBuilder()
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();


        System.out.println(book);

        bookService.save(book);

        System.out.println(bookService.findAll());

        System.out.println(bookService.findAll());
        System.out.println(bookService.findAll());



        EBookRepository ebookRepository = new EBookRepositoryCacheDecorator(
                new EBookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        EBookService ebookService = new EBookServiceImpl(ebookRepository);


        EBook ebook = new EBookBuilder()
                .setTitle("Harry Potter And The Deadly Hallows")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .setFormat("BBeB")
                .build();


        System.out.println(ebook);

        ebookService.save(ebook);


        System.out.println(ebookService.findAll());

        System.out.println(ebookService.findAll());
        System.out.println(ebookService.findAll());



        AudioBookRepository audiobookRepository = new AudioBookRepositoryCacheDecorator(
                new AudioBookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        AudioBookService audiobookService = new AudioBookServiceImpl(audiobookRepository);


        AudioBook audiobook = new AudioBookBuilder()
                .setTitle("Harry Potter and the Order of the Phoenix")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .setRunTime(1740)
                .build();


        System.out.println(audiobook);

        audiobookService.save(audiobook);


        System.out.println(audiobookService.findAll());

        System.out.println(audiobookService.findAll());
        System.out.println(audiobookService.findAll());




    }
}
