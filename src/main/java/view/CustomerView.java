package view;


import controller.CustomerController;
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
        Text sceneTitle = new Text("Our book colection");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 1, 3);
    }

    private void initializeTableView(GridPane gridPane) {
        table = new TableView<>();

        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");
        pubDateColumn = new TableColumn<>("Published Date");
        priceColumn = new TableColumn<>("Price");
        stockColumn = new TableColumn<>("Stock");
        buyColumn = new TableColumn<>("Buy");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        pubDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //bibliografie pentru codul de mai jos
        //https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
        Callback<TableColumn<Book, Button>, TableCell<Book, Button>> cellFactory
                = //
                new Callback<TableColumn<Book, Button>, TableCell<Book, Button>>() {
                    @Override
                    public TableCell call(final TableColumn<Book, Button> param) {
                        final TableCell<Book, Button> cell = new TableCell<Book, Button>() {

                            final Button buyButton = new Button("Buy");

                            @Override
                            protected void updateItem(Button item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    buyButton.setOnAction(event -> {
                                        Book book = getTableView().getItems().get(getIndex());
                                        
                                    });
                                    setGraphic(buyButton);
                                }

                            }
                        };
                        return cell;
                    }
                };

        buyColumn.setCellFactory(cellFactory);

        table.getColumns().add(idColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(authorColumn);
        table.getColumns().add(pubDateColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(stockColumn);
        table.getColumns().add(buyColumn);
        gridPane.add(table,2, 4, 25,25);//1:0

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(table);

        showAllBooksButton = new Button("See out book colection");

        HBox viewAllBooksHBox = new HBox(10);
        viewAllBooksHBox.setAlignment(Pos.BOTTOM_LEFT);
        viewAllBooksHBox.getChildren().add(showAllBooksButton);
        gridPane.add(viewAllBooksHBox, 5, 1);

        //HBox buyButtonHBox = new HBox(10);
        //buyButtonHBox.setAlignment(Pos.BOTTOM_LEFT);

        //gridPane.add(buyButtonHBox, 0,20,2,1);
        gridPane.add(tableViewBox, 0, 5, 10, 20);

    }

    private void initializeFields(GridPane gridPane) {
        cartArea = new TextArea();
        cartArea.setEditable(false);
        cartArea.setText("Your cart: \n");

        HBox buttonsHBox = new HBox(5);
        buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsHBox.getChildren().addAll(cartArea);
        gridPane.add(buttonsHBox, 0, 27, 2, 5);

        totalPriceLabel = new Label("Total price: ");
        gridPane.add(totalPriceLabel, 0, 32, 5, 1);
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
        totalPriceLabel.setText(totalPriceLabel.getText() + price);
    }

    public Book getSelectedBook(){
        Book selectedItem = table.getSelectionModel().getSelectedItem();
        return selectedItem;
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonHandler) {
        //buyButton.setOnAction(buyButtonHandler);
    }

}


















