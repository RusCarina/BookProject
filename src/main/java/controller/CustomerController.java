package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import view.CustomerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerController {
    private final CustomerView customerView;
    private final BookService bookService;
    List<Book> booksInCart = new ArrayList<>();

    public CustomerController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        this.customerView.addBuyButtonListener(new BuyBook());
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
        private int quantity = 0;
        private String author;
        //private ObservableList<Book> books;
        @Override
        public void handle(ActionEvent event) {
            selectedBook = customerView.getSelectedBook();
            booksInCart.add(selectedBook);
            title = selectedBook.getTitle();
            stock = selectedBook.getStock();
            author = selectedBook.getAuthor();


            if(stock > 0){
                if(Objects.equals(customerView.getQuantityText(), "")){
                    customerView.setCartArea("Introduce a quantity!\n");
                }
                else {
                    quantity = Integer.parseInt(customerView.getQuantityText());
                    customerView.setCartArea(title + ", " + author + ", Quantity: " + quantity + "\n");

                    price += selectedBook.getPrice() * quantity;
                    customerView.setPriceTotal(price);

                    selectedBook.setStock(stock - quantity);

                    //bookService.findById(selectedBook.getId()).setStock(stock - quantity);
                    bookService.updateDatabse(selectedBook.getId(), stock - quantity, title);
                    List<Book>books = bookService.findAll();
                    customerView.setBooksData(books);
                }

            }
            else {
                customerView.setCartArea("Sold out!");
            }
        }
    }

}
