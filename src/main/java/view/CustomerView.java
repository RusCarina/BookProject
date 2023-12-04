package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;

import java.time.LocalDate;
import java.util.List;

public class CustomerView {
    private Button addToCartButton;
    private Button viewCartButton;
    private Button showAllBooksButton;
    private Button buyButton;
    private TextArea cartArea;
    private Label totalPriceLabel;
    private TableView<Book> table;
    private TableColumn<Book, Long> idColumn;
    private TableColumn<Book, Integer>stockColumn;
    private TableColumn<Book, String> titleColumn, authorColumn;
    private TableColumn<Book, Integer> priceColumn;
    private TableColumn<Book, LocalDate> pubDateColumn;
    private TableColumn<Book, Button> buyColumn;
    private ObservableList<Book> booksList;
    private EventHandler<ActionEvent> buyHandler;
    //final Button buyButton = new Button("Buy");
    private TextField quantityField;

    public CustomerView(Stage primaryStage) {
        primaryStage.setTitle("Book Store");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        initializeSceneTitle(gridPane);

        initializeTableView(gridPane);

        initializeFields(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Our book collection:");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 1, 2);
    }

    private void initializeTableView(GridPane gridPane) {
        table = new TableView<>();

        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");
        pubDateColumn = new TableColumn<>("Published Date");
        priceColumn = new TableColumn<>("Price");
        stockColumn = new TableColumn<>("Stock");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        pubDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));


        table.getColumns().add(idColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(authorColumn);
        table.getColumns().add(pubDateColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(stockColumn);
        gridPane.add(table,0, 7, 25,25);//1:0

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(table);

        showAllBooksButton = new Button("See out book collection");

        HBox viewAllBooksHBox = new HBox(10);
        viewAllBooksHBox.setAlignment(Pos.BOTTOM_LEFT);
        viewAllBooksHBox.getChildren().add(showAllBooksButton);
        gridPane.add(viewAllBooksHBox, 0, 3);



        //HBox buyButtonHBox = new HBox(10);
        //buyButtonHBox.setAlignment(Pos.BOTTOM_LEFT);

        //gridPane.add(buyButtonHBox, 0,20,2,1);
        gridPane.add(tableViewBox, 0, 4, 10, 10);

    }

    private void initializeFields(GridPane gridPane) {
        buyButton = new Button("Buy");
        HBox buyBooksHBox = new HBox(10);
        buyBooksHBox.setAlignment(Pos.BOTTOM_LEFT);
        buyBooksHBox.getChildren().add(buyButton);
        gridPane.add(buyBooksHBox, 3, 17);

        Label quantityLabel = new Label("Quantity: ");
        gridPane.add(quantityLabel, 0 ,17);

        quantityField = new TextField();
        gridPane.add(quantityField, 1,17);

        cartArea = new TextArea();
        cartArea.setEditable(false);
        cartArea.setText("Your cart: \n");

        HBox buttonsHBox = new HBox(5);
        buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsHBox.getChildren().addAll(cartArea);
        gridPane.add(buttonsHBox, 0,18, 2, 10);

        totalPriceLabel = new Label("Total price: ");
        gridPane.add(totalPriceLabel, 0, 29, 5, 1);
    }

    public void setBooksData(List<Book> books) {
        booksList = FXCollections.observableArrayList(books);
        table.setItems(booksList);
    }

    public void showAllBooks(EventHandler<ActionEvent> show){
        showAllBooksButton.setOnAction(show);
    }

    public void setCartArea(String msj){
        cartArea.appendText(msj);
    }

    public void setPriceTotal(int price){
        totalPriceLabel.setText("Total price: " + price);
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonHandler) {
        buyButton.setOnAction(buyButtonHandler);
    }

//    public void setBuyButtonHandler(EventHandler<ActionEvent> buyButtonHandler) {
//        buyColumn.setOnAction(buyButtonHandler);
//    }

    public void setBuyEvent(EventHandler<ActionEvent> buyButtonHandler){
        buyHandler = buyButtonHandler;
    }

    public Book getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }

    public String getQuantityText(){
        return quantityField.getText();
    }

}

















