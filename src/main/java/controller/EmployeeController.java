package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.EmployeeView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final BookService bookService;
    private final List<User> selectedUsers;
    private int finishCounter =0;
    List<Book> addedToCartBooks = new ArrayList<>();

    public EmployeeController(EmployeeView employeeView, BookService bookService, List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.employeeView = employeeView;
        this.bookService = bookService;

        this.employeeView.addCreateButtonListener(new EmployeeController.CreateButtonHandler());
        this.employeeView.addRetrieveButtonListener(new EmployeeController.RetrieveButtonHandler());
        this.employeeView.addUpdateButtonListener(new EmployeeController.UpdateButtonHandler());
        this.employeeView.addDeleteButtonListener(new EmployeeController.DeleteButtonHandler());
        this.employeeView.addShowAllListener(new EmployeeController.ShowAllHandler());
        this.employeeView.addSellButtonListener(new EmployeeController.SellButtonHandler());
    }


    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            books = bookService.findAll();
            employeeView.setBooksData(books);
        }
    }

    public class CreateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            //List<Book> books;
            //books = bookService.findAll();
            //int ok = 0;

            String title = employeeView.getTitle();
            String author = employeeView.getAuthor();
            //String date = employeeView.getDate();
            int price = Integer.parseInt(employeeView.getPrice());
            int stock = Integer.parseInt(employeeView.getStock());
            //LocalDate datesql = new java.sql.Date(2002,10,14).toLocalDate();


            //String datestr = employeeView.getDate();
            //LocalDate date = new LocalDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = employeeView.getDate();;
            LocalDate localDate = LocalDate.parse(date, formatter);

            bookService.addNewBook(title,author,localDate,price,stock);

            List<Book> books = bookService.findAll();
            employeeView.setBooksData(books);

            employeeView.setActionTarget1("Register successful!");

        }
    }

    private class RetrieveButtonHandler implements EventHandler<ActionEvent> {

        Book book;
        String title;

        @Override
        public void handle(ActionEvent event) {

            if (Objects.equals(employeeView.getId(), "Id not found!")){
                employeeView.setRetriveTextArea("Id not found!");
            }
            else {
                Long id = Long.valueOf(employeeView.getId());
                book = bookService.findById(id);
                title = book.getTitle();
                employeeView.setRetriveTextArea(title);
            }
        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        Book book;
        int price;
        int stock;
        @Override
        public void handle(ActionEvent event) {

            //List<User> users;
            //users = authenticationService.findAll();

            if (Objects.equals(employeeView.getIdUpdate(), "Id not found!")) {

                employeeView.setIdUpdate("Id not found!");
            }
            else {
                Long id = Long.parseLong(employeeView.getIdUpdate());
                book = bookService.findById(id);
                price = Integer.parseInt(employeeView.getUpdatePrice());
                stock = Integer.parseInt(employeeView.getUpdateStock());

               // boolean registerNotification = bookService.updateEmployee(id, username, password);

                //if (registerNotification){
//                    employeeView.setActionTarget2("Something went wrong!");
//                }
//                else {
//                    employeeView.setActionTarget2("Price and Stock updated!");
                //}

                bookService.updateDatabasePS(book.getId(), price, stock);
                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);
            }

        }
    }

    public class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            Book selectedBook = employeeView.getSelectedBook();

            bookService.remove(selectedBook.getId());

            List<Book> books = bookService.findAll();
            employeeView.setBooksData(books);

        }
    }

    public class SellButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Book selectedBook = employeeView.getSelectedBook();
            int stock = selectedBook.getStock();
            String title = selectedBook.getTitle();
            if (stock<1){
                employeeView.setActionTarget3("Not enough books!");
            }
            else {
                int newStock = stock-1;
                if(newStock == 0){
                    employeeView.setActionTarget3("You sold the last book. No books left!");
                }
                else employeeView.setActionTarget3("Sold.");

                bookService.updateDatabse(selectedBook.getId(), newStock, title);

                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);
            }
        }
    }


}
