package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.EmployeeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final BookService bookService;
    private final List<Book> selectedBooks;

    private final String pdfFilePath = "Raport_Vanzari_1" +  ".pdf";

    private List<Book> selectedBooksList;



    public EmployeeController(EmployeeView employeeView, BookService bookService, List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
        this.employeeView = employeeView;
        this.bookService = bookService;

        this.employeeView.addCreateButtonListener(new EmployeeController.CreateButtonHandler());
        this.employeeView.addRetrieveButtonListener(new EmployeeController.RetrieveButtonHandler());
        this.employeeView.addUpdateButtonListener(new EmployeeController.UpdateButtonHandler());
        this.employeeView.addDeleteButtonListener(new EmployeeController.DeleteButtonHandler());
        this.employeeView.addShowAllListener(new EmployeeController.ShowAllHandler());
        this.employeeView.addSellButtonListener(new EmployeeController.SellButtonHandler());
        this.employeeView.addGenerateReportListener(new EmployeeController.ReportHandler());

    }

    private class ReportHandler implements EventHandler<ActionEvent> {

        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            System.out.println(selectedBooks);
            try {
                PdfWriter writer = new PdfWriter(pdfFilePath);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("Sold Books Report"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                for (Book soldBook : selectedBooks) {
                    document.add(new Paragraph("Title: " + soldBook.getTitle()));
                    document.add(new Paragraph("Author: " + soldBook.getAuthor()));
                    document.add(new Paragraph("Price: " + soldBook.getPrice()));
                    document.add(new Paragraph("-------------"));
                }

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            String title = employeeView.getTitle();
            String author = employeeView.getAuthor();
            int price = Integer.parseInt(employeeView.getPrice());
            int stock = Integer.parseInt(employeeView.getStock());

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

            if (Objects.equals(employeeView.getIdUpdate(), "Id not found!")) {

                employeeView.setIdUpdate("Id not found!");
            }
            else {
                Long id = Long.parseLong(employeeView.getIdUpdate());
                book = bookService.findById(id);
                price = Integer.parseInt(employeeView.getUpdatePrice());
                stock = Integer.parseInt(employeeView.getUpdateStock());

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
            String author = selectedBook.getAuthor();
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

                selectedBooks.add(selectedBook);


                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);
            }
        }
    }




}
