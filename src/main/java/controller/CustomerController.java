package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import view.CustomerView;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    private final BookService bookService;
    List<Book> booksInCart = new ArrayList<>();

    public CustomerController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        //this.customerView.buyHandler(new BuyBook());
        this.customerView.showAllBooks(new ViewAllBooks());
    }

    private class ViewAllBooks implements EventHandler<ActionEvent> {
        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            books = bookService.findAll();
            customerView.setBooksData(books);
        }
    }

    private class BuyBook implements EventHandler<ActionEvent> {
        private String title;
        private int stock;
        private Book selectedBook;
        private int price = 0;
        @Override
        public void handle(ActionEvent event) {
            selectedBook = customerView.getSelectedBook();
            //booksInCart.add(selectedBook);
            title = selectedBook.getTitle();
            stock = selectedBook.getStock();

            if(stock > 0){
                selectedBook.setStock(stock - 1);
                customerView.setCartArea(title);

                price += selectedBook.getPrice();
                customerView.setPriceTotal(price);
            }
            else {
                customerView.setCartArea("Sold out!");
            }
        }
    }

}
