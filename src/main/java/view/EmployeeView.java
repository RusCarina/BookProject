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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;
import model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EmployeeView {
    private Button showAllBooksButton;
    private Button createBookButton;
    private TextField createNameTextField;
    private TextField createAuthorTextField;
    private TextField createDateTextField;
    private TextField createPriceTextField;
    private TextField createStockTextField;

    private Button retriveButton;
    private TextField retriveByIdTextField;
    private TextArea retriveTextArea;

    private Button updateButton;
    private TextField updateIdTextField;
    private TextField updatePriceTextField;
    private TextField updateStockTextField;

    private Button deleteButton;
    private TextField deleteIdTextField;

    private TableView<Book> table;
    private TableColumn<Book, Long> idColumn;
    private TableColumn<Book, Integer>stockColumn;
    private TableColumn<Book, String> titleColumn, authorColumn;
    private TableColumn<Book, Integer> priceColumn;
    private TableColumn<Book, LocalDate> pubDateColumn;
    private ObservableList<Book> booksList;

    private Text actiontarget1;
    private Text actiontarget2;
    private Text actiontarget3;

    private Button sellButton;

    private Button generateReportButton;

    public EmployeeView(Stage primaryStage) {
        primaryStage.setTitle("Book Store - Employee");

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(scrollPane, 720, 480);
        primaryStage.setScene(scene);

        initializeSceneTitle(gridPane);
        initializeTableView(gridPane);
        initializeFields(gridPane);

        content.getChildren().add(gridPane);
        scrollPane.setContent(content);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(50, 50, 50, 50));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Hello Employee!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
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

        table.setPrefSize(100,150);
        gridPane.add(table, 0, 4, 3, 3);//1:0

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(table);

        showAllBooksButton = new Button("See all books");

        HBox viewAllBooksHBox = new HBox(10);
        viewAllBooksHBox.setAlignment(Pos.BOTTOM_LEFT);
        viewAllBooksHBox.getChildren().add(showAllBooksButton);
        gridPane.add(viewAllBooksHBox, 0, 3);

        gridPane.add(tableViewBox, 0, 4, 3, 3);
    }

    private void initializeFields(GridPane gridPane) {
        Label create = new Label("CREATE NEW BOOK");
        gridPane.add(create, 0, 9);

        Label tc = new Label("Title:");
        gridPane.add(tc, 0, 10);
        createNameTextField = new TextField();
        gridPane.add(createNameTextField, 1, 10);

        Label ac = new Label("Author:");
        gridPane.add(ac, 0, 11);
        createAuthorTextField = new TextField();
        gridPane.add(createAuthorTextField, 1, 11);

        Label dc = new Label("Published Date:");
        gridPane.add(dc, 0, 12);
        createDateTextField = new TextField();
        gridPane.add(createDateTextField, 1, 12);

        Label pc = new Label("Price:");
        gridPane.add(pc, 0, 13);
        createPriceTextField = new TextField();
        gridPane.add(createPriceTextField, 1, 13);

        Label sc = new Label("Stock:");
        gridPane.add(sc, 0, 14);
        createStockTextField = new TextField();
        gridPane.add(createStockTextField, 1, 14);

        actiontarget1 = new Text();
        actiontarget1.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget1, 1, 15);

        createBookButton = new Button("Create");
        gridPane.add(createBookButton, 0, 16);

        Label retrive = new Label("RETRIVE BOOK");
        gridPane.add(retrive, 0, 17);

        Label idr = new Label("ID:");
        gridPane.add(idr, 0, 18);
        retriveByIdTextField = new TextField();
        gridPane.add(retriveByIdTextField, 1, 18);

        retriveTextArea = new TextArea();
        retriveTextArea.setEditable(false);
        retriveTextArea.setPrefRowCount(1);
        retriveTextArea.setWrapText(true);
        gridPane.add(retriveTextArea, 1, 19, 1, 1);

        retriveButton = new Button("Retrive");
        gridPane.add(retriveButton,0,20);

        Label update = new Label("UPDATE BOOK");
        gridPane.add(update, 0, 21);

        Label idu = new Label("ID:");
        gridPane.add(idu, 0, 22);
        updateIdTextField = new TextField();
        gridPane.add(updateIdTextField, 1, 22);

        Label pu = new Label("New Price:");
        gridPane.add(pu, 0, 23);
        updatePriceTextField = new TextField();
        gridPane.add(updatePriceTextField, 1, 23);

        Label su = new Label("New Stock:");
        gridPane.add(su, 0, 24);
        updateStockTextField = new TextField();
        gridPane.add(updateStockTextField, 1, 24);

        actiontarget2 = new Text();
        actiontarget2.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget2, 1, 25);

        updateButton = new Button("Update");
        gridPane.add(updateButton,0,26);

        Label delete = new Label("DELETE BOOK");
        gridPane.add(delete, 0, 27);

        Label idcc = new Label("Click on the book you want to delete");
        gridPane.add(idcc, 0, 28);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton,0,29);

        Label sell = new Label("SELL BOOK");
        gridPane.add(sell, 0, 30);

        Label ids = new Label("Click on the book you want to sell");
        gridPane.add(ids, 0, 31);

        sellButton = new Button("Sell");
        gridPane.add(sellButton,0,32);

        actiontarget3 = new Text();
        actiontarget3.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget3, 1, 33);

        generateReportButton = new Button("Report");
        gridPane.add(generateReportButton,0,34);

    }

    public void addGenerateReportListener(EventHandler<ActionEvent> reportHandler) {
        generateReportButton.setOnAction(reportHandler);
    }


    public void addShowAllListener(EventHandler<ActionEvent> showAllHandler) {
        showAllBooksButton.setOnAction(showAllHandler);
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonHandler) {
        sellButton.setOnAction(sellButtonHandler);
    }

    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonHandler) {
        createBookButton.setOnAction(createButtonHandler);
    }

    public void addRetrieveButtonListener(EventHandler<ActionEvent> retrieveButtonHandler) {
        retriveButton.setOnAction(retrieveButtonHandler);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonHandler) {
        updateButton.setOnAction(updateButtonHandler);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonHandler) {
        deleteButton.setOnAction(deleteButtonHandler);
    }

    public String getTitle() {
        return createNameTextField.getText();
    }

    public String getAuthor() {
        return createAuthorTextField.getText();
    }

    public String getDate() {
        return createDateTextField.getText();
    }

    public String getPrice() {
        return createPriceTextField.getText();
    }

    public String getStock() {
        return createStockTextField.getText();
    }

    public String getUpdatePrice() {
        return updatePriceTextField.getText();
    }

    public String getUpdateStock() {
        return updateStockTextField.getText();
    }

    public String getId() {
        if(Objects.equals(retriveByIdTextField.getText(), "")){
            return "Id not found!";
        }
        return retriveByIdTextField.getText();
    }

    public String getIdUpdate() {
        if(Objects.equals(updateIdTextField.getText(), "")){
            return "Id not found!";
        }
        return updateIdTextField.getText();
    }

    public void setActionTarget1(String text) {
        this.actiontarget1.setText(text);
    }

    public void setActionTarget2(String text) {
        this.actiontarget2.setText(text);
    }

    public void setActionTarget3(String text) {
        this.actiontarget3.setText(text);
    }


    public void setBooksData(List<Book> books) {
        booksList = FXCollections.observableArrayList(books);
        table.setItems(booksList);
    }

    public void setRetriveTextArea(String msj){
        retriveTextArea.setText(msj);
    }

    public void setIdUpdate(String msj){
        updateIdTextField.setText(msj);
    }

    public Book getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }



}
