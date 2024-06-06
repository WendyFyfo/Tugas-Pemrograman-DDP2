package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameField;
    private TextField phoneNumberField;
    private Button loginButton;
    private Text inputErrorMsg;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        //TODO: Implementasi method untuk menampilkan komponen form login
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("prefHeight:\"700.0\" ;prefWidth:\"500.0\" ;-fx-background-image: url('/Image/loginFormBG.png'); -fx-background-size: cover");

        Text mainText = new Text("Welcome to DepeFood");
        mainText.setLayoutX(94);
        mainText.setLayoutY(173);
        mainText.setStrokeType(StrokeType.OUTSIDE);
        mainText.setStrokeWidth(0);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setTextOrigin(VPos.CENTER);
        mainText.setWrappingWidth(311.4700927734375);
        mainText.setFont(new Font("Century Gothic Bold Italic", 40));

        Text nameText = new Text("Name: ");
        nameText.setLayoutX(65.0);
        nameText.setLayoutY(303.0);
        nameText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        nameText.setStrokeWidth(0.0);
        nameText.setFont(new Font(20.0));

        Text phoneNumberText = new Text("Phone Number:");
        phoneNumberText.setLayoutX(65.0);
        phoneNumberText.setLayoutY(372.0);
        phoneNumberText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        phoneNumberText.setStrokeWidth(0.0);
        phoneNumberText.setFont(new Font(20.0));

        inputErrorMsg = new Text("*Name or phone number are incorrect");
        inputErrorMsg.setFill(javafx.scene.paint.Color.WHITE);
        inputErrorMsg.setLayoutX(226.0);
        inputErrorMsg.setLayoutY(399.0);
        inputErrorMsg.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        inputErrorMsg.setStrokeWidth(0.0);
        inputErrorMsg.setWrappingWidth(220.66670735677076);
        inputErrorMsg.setFont(Font.font("System Bold Italic", 12));

        nameField = new TextField();
        nameField.setLayoutX(226.0);
        nameField.setLayoutY(280.0);
        nameField.setPrefHeight(31.0);
        nameField.setPrefWidth(201.0);
        nameField.setPromptText("Input your name...");
        nameField.setOnKeyPressed(event -> keyboardPressed());

        phoneNumberField = new TextField();
        phoneNumberField.setLayoutX(226.0);
        phoneNumberField.setLayoutY(348.0);
        phoneNumberField.setPrefHeight(31.0);
        phoneNumberField.setPrefWidth(201.0);
        phoneNumberField.setPromptText("Input your phone number...");
        phoneNumberField.setOnKeyPressed(event -> keyboardPressed());

        loginButton = new Button("Login");
        loginButton.setLayoutX(180.0);
        loginButton.setLayoutY(429.0);
        loginButton.setMnemonicParsing(false);
        loginButton.setOnAction(event -> handleLogin());
        loginButton.setPrefHeight(31.0);
        loginButton.setPrefWidth(139.0);
        loginButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        loginButton.setFont(new Font("System Bold", 24.0));
        anchorPane.getChildren().addAll(mainText, nameText, nameField, phoneNumberText, phoneNumberField, inputErrorMsg, loginButton);

        return new Scene(anchorPane,500,700);
    }


    private void handleLogin() {
        System.out.println("handlelogin reached");
        loginButton.setStyle("-fx-background-color: #997202; -fx-border-color: #ffd573;");
        loginButton.setDisable(true);
        User userLoggedIn = DepeFood.getUser(nameField.getText(), phoneNumberField.getText());
        if (userLoggedIn != null) {
            loginButton.setStyle("-fx-background-color: #fcba03; -fx-border-color: #ffd573; ");
            loginButton.setDisable(false);
            if (userLoggedIn.getRole().equals("Admin")){
                AdminMenu admin = new AdminMenu(stage, mainApp, userLoggedIn);
            }else{
                CustomerMenu custmer = new CustomerMenu(stage, mainApp, userLoggedIn);
            }
            System.out.println("reach validateUser");
        } else {
            inputErrorMsg.setFill(Color.RED);
        }
    }

    public void keyboardPressed(){
        loginButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        loginButton.setDisable(false);
        inputErrorMsg.setFill(Color.WHITE);

    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}
