package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AdministratorView {
    private Button showAllEmployeesButton;
    private Button createEmployeeButton;
    private TextField createUsernameTextField;
    private TextField createPasswordTextField;

    private Button retriveButton;
    private TextField retriveByIdTextField;
    private TextArea retriveTextArea;

    private Button updateButton;
    private TextField updateIdTextField;
    private TextField updateUsernameTextField;
    private PasswordField updatePasswordTextField;

    private Button deleteButton;
    private TextField deleteIdTextField;

    private TableView<User> table;
    private TableColumn<User, Long> idColumn;
    private TableColumn<User, Integer> usernameColumn;
    private ObservableList<User> userList;

    private Text actiontarget1;
    private Text actiontarget2;

    private Button generateReportButton;


    public AdministratorView(Stage primaryStage) {
        primaryStage.setTitle("Book Store - Administrator");

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
        Text sceneTitle = new Text("Hello Admin!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 1, 2);
    }

    private void initializeTableView(GridPane gridPane) {
        table = new TableView<>();

        idColumn = new TableColumn<>("ID");
        usernameColumn = new TableColumn<>("Username");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));

        table.getColumns().add(idColumn);
        table.getColumns().add(usernameColumn);

        table.setPrefSize(50,100);
        gridPane.add(table, 0, 4, 3, 3);//1:0

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(table);

        showAllEmployeesButton = new Button("See all employees");

        HBox viewAllBooksHBox = new HBox(10);
        viewAllBooksHBox.setAlignment(Pos.BOTTOM_LEFT);
        viewAllBooksHBox.getChildren().add(showAllEmployeesButton);
        gridPane.add(viewAllBooksHBox, 0, 3);

        gridPane.add(tableViewBox, 0, 4, 3, 3);
    }

    private void initializeFields(GridPane gridPane) {
        Label create = new Label("CREATE NEW EMPLOYEE");
        gridPane.add(create, 0, 9);

        Label username = new Label("Username:");
        gridPane.add(username, 0, 10);
        createUsernameTextField = new TextField();
        gridPane.add(createUsernameTextField, 1, 10);

        Label pass = new Label("Password:");
        gridPane.add(pass, 0, 11);
        createPasswordTextField = new PasswordField();
        gridPane.add(createPasswordTextField, 1, 11);

        actiontarget1 = new Text();
        actiontarget1.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget1, 1, 12);

        createEmployeeButton = new Button("Create");
        gridPane.add(createEmployeeButton, 0, 13);

        Label retrive = new Label("RETRIVE EMPLOYEE");
        gridPane.add(retrive, 0, 14);

        Label id = new Label("ID:");
        gridPane.add(id, 0, 15);
        retriveByIdTextField = new TextField();
        gridPane.add(retriveByIdTextField, 1, 15);

        retriveTextArea = new TextArea();
        retriveTextArea.setEditable(false);
        retriveTextArea.setPrefRowCount(1);
        retriveTextArea.setWrapText(true);
        gridPane.add(retriveTextArea, 1, 16, 1, 1);

        retriveButton = new Button("Retrive");
        gridPane.add(retriveButton,0,17);

        Label update = new Label("UPDATE EMPLOYEE");
        gridPane.add(update, 0, 18);

        Label idc = new Label("ID:");
        gridPane.add(idc, 0, 19);
        updateIdTextField = new TextField();
        gridPane.add(updateIdTextField, 1, 19);

        Label usernamec = new Label("New Username:");
        gridPane.add(usernamec, 0, 20);
        updateUsernameTextField = new TextField();
        gridPane.add(updateUsernameTextField, 1, 20);

        Label passc = new Label("New Password:");
        gridPane.add(passc, 0, 21);
        updatePasswordTextField = new PasswordField();
        gridPane.add(updatePasswordTextField, 1, 21);

        actiontarget2 = new Text();
        actiontarget2.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget2, 1, 22);

        updateButton = new Button("Update");
        gridPane.add(updateButton,0,23);

        Label delete = new Label("DELETE EMPLOYEE");
        gridPane.add(delete, 0, 24);

        Label idcc = new Label("Click on the employee you want to delete");
        gridPane.add(idcc, 0, 25);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton,0,26);

        generateReportButton = new Button("Report");
        gridPane.add(generateReportButton,0,34);

    }

    public void addGenerateReportListener(EventHandler<ActionEvent> reportHandler) {
        generateReportButton.setOnAction(reportHandler);
    }

    public void addShowAllListener(EventHandler<ActionEvent> showAllHandler) {
        showAllEmployeesButton.setOnAction(showAllHandler);
    }

    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonHnadler) {
        createEmployeeButton.setOnAction(createButtonHnadler);
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

    public String getUsernameCreate() {
        return createUsernameTextField.getText();
    }

    public String getPasswordCreate() {
        return createPasswordTextField.getText();
    }

    public String getUsernameUpdate() {
        return updateUsernameTextField.getText();
    }

    public String getPasswordUpdate() {
        return updatePasswordTextField.getText();
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


    public void setUsersData(List<User> users) {
        userList = FXCollections.observableArrayList(users);
        table.setItems(userList);
    }

    public void setRetriveTextArea(String msj){
        retriveTextArea.setText(msj);
    }

    public void setIdUpdate(String msj){
        updateIdTextField.setText(msj);
    }

    public User getSelectedUser(){
        return table.getSelectionModel().getSelectedItem();
    }


}