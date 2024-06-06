package assignments.assignment4.page;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment1.OrderGenerator;
import assignments.assignment3.*;
import assignments.assignment3.Menu;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private Scene deleteRestaurantScene;
    private Scene viewMenuScene;
    private static List<Restaurant> restoList = DepeFood.getRestoList();
    private MainApp mainApp; // Reference to MainApp instance
    private List<Button> restoButtons = new ArrayList<>();
    private static VBox restoBtnVBox = new VBox();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        mainApp.setScene(this.scene);
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
        this.deleteRestaurantScene = createDeleteRestaurantForm();
    }


    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Admin
        AnchorPane adminMenuLayout = new AnchorPane();
        adminMenuLayout.setPrefSize(500,700);
        adminMenuLayout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        adminMenuLayout.getChildren().add(userName);

        Button logOutBtn = new Button("Log Out");
        logOutBtn.setLayoutX(23);
        logOutBtn.setLayoutY(21);
        logOutBtn.setFont(new Font("System Bold", 12));
        logOutBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #f0b003; -fx-border-width: 2;");
        logOutBtn.setOnAction(e-> mainApp.setScene(mainApp.getScene("Login")));
        adminMenuLayout.getChildren().add(logOutBtn);

        Text mainText = new Text("Admin Menu");
        mainText.setLayoutX(101);
        mainText.setLayoutY(196);
        mainText.setStrokeType(StrokeType.OUTSIDE);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));

        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(5);
        buttonGrid.setVgap(5);
        buttonGrid.setLayoutX(87);
        buttonGrid.setLayoutY(228);
        buttonGrid.setPrefSize(222, 286);
        buttonGrid.setPadding(new Insets(10));
        buttonGrid.getColumnConstraints().addAll(new ColumnConstraints(150),new ColumnConstraints(150));
        buttonGrid.getRowConstraints().addAll(new RowConstraints(150),new RowConstraints(150));

        Button addRestaurantButton = new Button("Tambah Restaurant");
        //TODO: set button image
        addRestaurantButton.setPrefHeight(140.0);
        addRestaurantButton.setPrefWidth(140.0);
        addRestaurantButton.setAlignment(Pos.CENTER);
        addRestaurantButton.setTextAlignment(TextAlignment.CENTER);
        addRestaurantButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        addRestaurantButton.setOnAction(e -> mainApp.setScene(this.addRestaurantScene));
        buttonGrid.add(addRestaurantButton, 0, 0);

        Button addMenuButton = new Button("Tambah Menu");
        //TODO: set button image
        addMenuButton.setMnemonicParsing(false);
        addMenuButton.setPrefHeight(140.0);
        addMenuButton.setPrefWidth(140.0);
        addMenuButton.setAlignment(Pos.CENTER);
        addMenuButton.setTextAlignment(TextAlignment.CENTER);
        addMenuButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        addMenuButton.setOnAction(e -> mainApp.setScene(this.addMenuScene));
        buttonGrid.add(addMenuButton,1,0);

        Button viewRestaurantButton = new Button("Lihat Restaurant");
        //TODO: set button image
        viewRestaurantButton.setMnemonicParsing(false);
        viewRestaurantButton.setPrefHeight(140.0);
        viewRestaurantButton.setPrefWidth(140.0);
        viewRestaurantButton.setAlignment(Pos.CENTER);
        viewRestaurantButton.setTextAlignment(TextAlignment.CENTER);
        viewRestaurantButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        viewRestaurantButton.setOnAction(e -> mainApp.setScene(this.viewRestaurantsScene));
        buttonGrid.add(viewRestaurantButton, 0, 1);

        Button deleteRestaurantButton = new Button("Hapus Restaurant");
        //TODO: set button image
        deleteRestaurantButton.setMnemonicParsing(false);
        deleteRestaurantButton.setPrefHeight(140.0);
        deleteRestaurantButton.setPrefWidth(140.0);
        deleteRestaurantButton.setAlignment(Pos.CENTER);
        deleteRestaurantButton.setTextAlignment(TextAlignment.CENTER);
        deleteRestaurantButton.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        deleteRestaurantButton.setOnAction(e -> mainApp.setScene(this.deleteRestaurantScene));
        buttonGrid.add(deleteRestaurantButton,1,1);

        adminMenuLayout.getChildren().addAll(mainText, buttonGrid);

        return new Scene(adminMenuLayout);
    }

    private Scene createAddRestaurantForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah restoran
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        Text mainText = new Text("Add New Restaurant");
        mainText.setLayoutX(92);
        mainText.setLayoutY(196);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setWrappingWidth(316.53968811035156);
        mainText.setFont(new Font("Century Gothic Bold", 38));
        layout.getChildren().add(mainText);

        Text restaurantText = new Text("Restaurant name: ");
        restaurantText.setLayoutX(49);
        restaurantText.setLayoutY(333);
        restaurantText.setTextAlignment(TextAlignment.CENTER);
        restaurantText.setFont(new Font("century Gothic Bold", 15));
        layout.getChildren().add(restaurantText);

        Text inputErrorMsg = new Text();
        inputErrorMsg.setTextAlignment(TextAlignment.CENTER);
        inputErrorMsg.setLayoutX(198);
        inputErrorMsg.setLayoutY(363);
        layout.getChildren().add(inputErrorMsg);

        TextField restoNameField = new TextField();
        restoNameField.setPromptText("Min. 4 Letter...");
        restoNameField.setLayoutX(191);
        restoNameField.setLayoutY(314);
        restoNameField.setPrefSize(259,25);
        restoNameField.setAlignment(Pos.CENTER);
        restoNameField.setOnKeyPressed(e -> inputErrorMsg.setText(""));
        layout.getChildren().add(restoNameField);

        Button submitBtn = new Button("Submit");
        submitBtn.setLayoutX(180);
        submitBtn.setLayoutY(384);
        submitBtn.setPrefSize(139,31);
        submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        submitBtn.setFont(new Font ("System Bold", 12));
        submitBtn.setOnAction(event -> handleTambahRestoran(restoNameField.getText(), inputErrorMsg, submitBtn));
        layout.getChildren().add(submitBtn);

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(180);
        backBtn.setLayoutY(433);
        backBtn.setPrefSize(139,31);
        backBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        backBtn.setFont(new Font ("System Bold", 12));
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        return new Scene(layout);
    }

    private Scene createAddMenuForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah menu restoran
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        Text mainText = new Text("Add Menu");
        mainText.setLayoutX(128);
        mainText.setLayoutY(154);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));
        layout.getChildren().add(mainText);

        Text restoNameText = new Text("Restaurant Name: ");
        restoNameText.setLayoutX(49);
        restoNameText.setLayoutY(261);
        restoNameText.setTextAlignment(TextAlignment.CENTER);
        restoNameText.setFont(new Font("System Bold", 15));
        layout.getChildren().add(restoNameText);

        Text menuNameText = new Text("Menu Name         : ");
        menuNameText.setLayoutX(49);
        menuNameText.setLayoutY(318);
        menuNameText.setTextAlignment(TextAlignment.CENTER);
        menuNameText.setFont(new Font("System Bold", 15));
        layout.getChildren().add(menuNameText);

        Text priceText = new Text("Price                    :");
        priceText.setLayoutX(49);
        priceText.setLayoutY(375);
        priceText.setTextAlignment(TextAlignment.CENTER);
        priceText.setFont(new Font("System Bold", 15));
        layout.getChildren().add(priceText);

        Text rupiah = new Text("Rp.");
        rupiah.setLayoutX(191);
        rupiah.setLayoutY(375);
        rupiah.setTextAlignment(TextAlignment.CENTER);
        rupiah.setFont(new Font("System Bold", 12));
        layout.getChildren().add(rupiah);

        Text restoErrorMsg = new Text();
        restoErrorMsg.setTextAlignment(TextAlignment.CENTER);
        restoErrorMsg.setFill(Paint.valueOf("red"));
        restoErrorMsg.setLayoutX(191);
        restoErrorMsg.setLayoutY(280);
        layout.getChildren().add(restoErrorMsg);

        Text menuErrorMsg = new Text("");
        menuErrorMsg.setTextAlignment(TextAlignment.CENTER);
        menuErrorMsg.setLayoutX(191);
        menuErrorMsg.setLayoutY(337);
        menuErrorMsg.setFill(Paint.valueOf("red"));
        layout.getChildren().add(menuErrorMsg);

        Text priceErrorMsg = new Text();
        priceErrorMsg.setTextAlignment(TextAlignment.CENTER);
        priceErrorMsg.setLayoutX(191);
        priceErrorMsg.setLayoutY(337);
        priceErrorMsg.setFill(Paint.valueOf("red"));
        layout.getChildren().add(priceErrorMsg);

        Button submitBtn = new Button("Add Menu");

        TextField restoNameField = new TextField();
        restoNameField.setPromptText("Min. 4 Letter");
        restoNameField.setLayoutX(191);
        restoNameField.setLayoutY(242);
        restoNameField.setPrefSize(259,25);
        restoNameField.setOnKeyPressed(e -> {
            restoErrorMsg.setText("");
            submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
            submitBtn.setDisable(false);
        });
        layout.getChildren().add(restoNameField);

        TextField menuNameField = new TextField();
        menuNameField.setLayoutX(191);
        menuNameField.setLayoutY(299);
        menuNameField.setPrefSize(259,25);
        menuNameField.setOnKeyPressed(e ->{
            menuErrorMsg.setText("");
            submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
            submitBtn.setDisable(false);
        });
        layout.getChildren().add(menuNameField);

        TextField priceField = new TextField();
        priceField.setPromptText("Muset be more than zero...");
        priceField.setLayoutX(214);
        priceField.setLayoutY(347);
        priceField.setPrefSize(236,25);
        priceField.setOnKeyPressed(e -> {
            priceErrorMsg.setText("");
            submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
            submitBtn.setDisable(false);
        });
        layout.getChildren().add(priceField);

        submitBtn.setLayoutX(180);
        submitBtn.setLayoutY(409);
        submitBtn.setPrefSize(139,31);
        submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        submitBtn.setFont(new Font ("System Bold", 12));
        submitBtn.setOnAction(e -> handleTambahMenuRestoran(restoNameField.getText(), menuNameField.getText(),priceField.getText(), restoErrorMsg, menuErrorMsg, priceErrorMsg, submitBtn));
        layout.getChildren().add(submitBtn);

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(180);
        backBtn.setLayoutY(458);
        backBtn.setPrefSize(139,31);
        backBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        backBtn.setFont(new Font ("System Bold", 12));
        backBtn.setOnAction(e -> mainApp.setScene(scene));
        layout.getChildren().add(backBtn);


        return new Scene(layout);
    }
    
    
    private Scene createViewRestaurantsForm() {
        // TODO: Implementasikan method ini untuk menampilkan page daftar restoran
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);
        Button backBtn = new Button("Back");
        backBtn.setLayoutX(23);
        backBtn.setLayoutY(21);
        backBtn.setFont(new Font("System Bold", 12));
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        Text mainText = new Text("Restaurant List");
        mainText.setLayoutX(87);
        mainText.setLayoutY(130);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));
        layout.getChildren().add(mainText);

        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setLayoutX(55);
        sp.setLayoutY(144);
        sp.setPrefSize(389,486);
        sp.setStyle("-fx-background-color: #ffffff;");
        layout.getChildren().add(sp);

        restoBtnVBox = new VBox();
        restoBtnVBox.setPrefSize(387,485);
        restoBtnVBox.setStyle("-fx-background-color: #ffffff;");
        System.out.println(restoButtons.toString());
        //restoBtnVBox.getChildren().addAll(restoButtons);
        sp.setContent(restoBtnVBox);

        return new Scene(layout);
    }

    private Scene createDeleteRestaurantForm(){
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        Text mainText = new Text("Delete Restaurant");
        mainText.setLayoutX(120);
        mainText.setLayoutY(152);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));
        mainText.setWrappingWidth(259.33333333333326);
        layout.getChildren().add(mainText);

        Text restaurantText = new Text("Restaurant name: ");
        restaurantText.setLayoutX(49);
        restaurantText.setLayoutY(316);
        restaurantText.setTextAlignment(TextAlignment.CENTER);
        restaurantText.setFont(new Font(15));
        layout.getChildren().add(restaurantText);

        Text inputErrorMsg = new Text("*Restaurant is already registered.");
        inputErrorMsg.setTextAlignment(TextAlignment.CENTER);
        inputErrorMsg.setFill(Paint.valueOf("white"));
        inputErrorMsg.setLayoutX(198);
        inputErrorMsg.setLayoutY(363);
        layout.getChildren().add(inputErrorMsg);

        TextField restoNameField = new TextField();
        restoNameField.setPromptText("Min. 4 Letter...");
        restoNameField.setLayoutX(191);
        restoNameField.setLayoutY(297);
        restoNameField.setPrefSize(259,25);
        restoNameField.setOnKeyPressed(e -> inputErrorMsg.setText(""));
        layout.getChildren().add(restoNameField);

        Button deleteBtn = new Button("Delete");
        deleteBtn.setLayoutX(180);
        deleteBtn.setLayoutY(384);
        deleteBtn.setPrefSize(139,31);
        deleteBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        deleteBtn.setFont(new Font ("System Bold", 12));
        deleteBtn.setOnAction(event -> handleHapusRestoran(restoNameField.getText(), inputErrorMsg, deleteBtn));
        layout.getChildren().add(deleteBtn);

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(180);
        backBtn.setLayoutY(433);
        backBtn.setPrefSize(139,31);
        backBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        backBtn.setFont(new Font ("System Bold", 12));
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        return new Scene(layout);
    }

    private Scene createViewMenuForm(Restaurant restaurant) {
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName= new Label(this.user.getNama());
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        String[] nama = restaurant.getNama().split(" ");
        StringBuilder namaFormatted = new StringBuilder();
        for(String kata : nama){
            if(namaFormatted.length()>=39){
                namaFormatted = new StringBuilder(namaFormatted.substring(0, 39) + "...");
                break;
            }
            if(kata.length()>21){
                namaFormatted.append(kata, 0, 21).append(" ").append(kata.substring(21));
            }else{
                namaFormatted.append(kata);
            }
        }

        Label restoName = new Label(namaFormatted.toString());
        restoName.setLayoutX(55);
        restoName.setLayoutY(56);
        restoName.setPrefSize(88,389);
        restoName.setMaxSize(88,389);
        restoName.setTextAlignment(TextAlignment.CENTER);
        restoName.setWrapText(true);
        restoName.setFont(new Font("Century Gothic Bold", 33));
        layout.getChildren().add(restoName);

        ScrollPane menuScroll = new ScrollPane();
        menuScroll.setLayoutX(55);
        menuScroll.setLayoutY(144);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuScroll.setPrefSize(389,486);
        menuScroll.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffdbaa; -fx-border-width: 3;");
        layout.getChildren().add(menuScroll);

        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(3);
        menuGrid.setVgap(3);
        menuGrid.setPrefSize(387,486);
        menuGrid.setStyle("-fx-background-color: #fbbb7a; -fx-border-color: #ffdbaa; -fx-border-width: 5;");
        menuGrid.setPadding(new Insets(2));
        menuScroll.setContent(menuGrid);

        Label menuLabel = new Label("Menu");
        menuLabel.setPrefSize(193,24);
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setStyle("-fx-background-color: #ffffff;");
        menuLabel.setFont(new Font("System Bold", 12));
        menuGrid.add(menuLabel,0,0 );
        GridPane.setMargin(menuLabel, new Insets(2));

        Label priceLabel = new Label("Price");
        priceLabel.setPrefSize(193,24);
        priceLabel.setAlignment(Pos.CENTER);
        priceLabel.setStyle("-fx-background-color: #ffffff;");
        priceLabel.setFont(new Font("System Bold", 12));
        menuGrid.add(priceLabel,1,0 );
        GridPane.setMargin(priceLabel, new Insets(2));

        ArrayList<Menu> menuList = restaurant.getMenu();
        for(int i=0; i<menuList.size(); i++){
            Menu menu = menuList.get(i);
            Label menuName = new Label(menu.getNamaMakanan());
            menuName.setAlignment(Pos.CENTER);
            menuName.setPrefSize(193,24);
            menuName.setStyle("-fx-background-color: #ffffff;");
            menuName.setFont(new Font("System Bold", 12));
            menuName.setTextOverrun(OverrunStyle.ELLIPSIS);
            menuGrid.add(menuName, 0,i+1);
            GridPane.setMargin(menuName, new Insets(2));

            Label price = new Label(Double.toString(menu.getHarga()));
            price.setAlignment(Pos.CENTER);
            price.setPrefSize(193,24);
            price.setStyle("-fx-background-color: #ffffff;");
            price.setFont(new Font("System Bold", 12));
            price.setTextOverrun(OverrunStyle.ELLIPSIS);
            menuGrid.add(price, 1,i+1);
            GridPane.setMargin(price, new Insets(2));
        }

        Button backBtn = new Button("Back");
        backBtn.setLayoutX(23);
        backBtn.setLayoutY(21);
        backBtn.setFont(new Font("System Bold", 12));
        backBtn.setOnAction(e -> mainApp.setScene(this.viewRestaurantsScene));
        layout.getChildren().add(backBtn);

        return new Scene(layout);
    }

    private void createDeleteConfirmationWindow(Stage popUpStage,Button yesBtn, Button noBtn){
        AnchorPane popUp = new AnchorPane();
        popUp.setPrefSize(450,300);

        Text prompt = new Text("Are you sure you want to delete this Restaurant?");
        prompt.setLayoutX(35);
        prompt.setLayoutY(87);
        prompt.setFont(new Font("Century Gothic Bold", 16));
        popUp.getChildren().add(prompt);

        yesBtn.setLayoutX(87);
        yesBtn.setLayoutY(140);
        yesBtn.setPrefSize(89,45);
        yesBtn.setTextFill(Paint.valueOf("white"));
        yesBtn.setStyle("-fx-background-color: #df0020; -fx-border-color: #ca0020; -fx-border-width: 2;");
        yesBtn.setFont(new Font("System Bold", 24));
        popUp.getChildren().add(yesBtn);

        noBtn.setLayoutX(274);
        noBtn.setLayoutY(140);
        noBtn.setPrefSize(89,45);
        noBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #f0b003; -fx-border-width: 2;");
        noBtn.setFont(new Font("System Bold", 24));
        popUp.getChildren().add(noBtn);

        popUpStage.setScene(new Scene(popUp));
        popUpStage.show();
    }

    private void handleTambahRestoran(String nama, Text text, Button button) {
        //TODO: Implementasi validasi isian nama Restoran
        button.setDisable(true);
        button.setStyle("-fx-background-color: #997202; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        if (DepeFood.findRestaurant(nama) != null){
            text.setText("*Restaurant is already registered");
        }else if (!OrderGenerator.validateRestaurantName(nama)){
            text.setText("*Name must have 4 or more character");
        }else{
            restoList.add(new Restaurant(nama));
            Button restoBtn = new Button();
            restoBtn.setText(nama);
            restoBtn.setPrefSize(373,25);
            restoBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 20; -fx-border-color: #ffd573; -fx-border-width: 3; -fx-border-radius: 17;");
            restoBtn.setFont(new Font("Century Gothic Bold", 20));
            restoBtn.setOnAction(event -> mainApp.setScene(createViewMenuForm(restoList.getLast())));
            //restoButtons.add(restoBtn);
            restoBtnVBox.getChildren().add(restoBtn);

            Alert orderSuccessMsg = new Alert(Alert.AlertType.INFORMATION);
            orderSuccessMsg.setTitle("Register Success");
            orderSuccessMsg.setHeaderText(null);
            orderSuccessMsg.setContentText("Restaurant is succesfully registered!");
            orderSuccessMsg.showAndWait();
        }
        button.setDisable(false);
        button.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
    }

    private void handleTambahMenuRestoran(String restaurant, String itemName, String price, Text restoText, Text menuText, Text priceText, Button submitBtn) {
        //TODO: Implementasi validasi isian menu Restoran
        submitBtn.setDisable(true);
        submitBtn.setStyle("-fx-background-color: #997202; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
        Restaurant resto = DepeFood.findRestaurant(restaurant);
        boolean inputError = false;
        if (resto == null) {
            restoText.setText("*Restaurant not found.");
            inputError = true;
        }
        List<Menu> menuList = resto.getMenu();
        if(itemName.isEmpty()){
            menuText.setText("*Menu name can't be empty.");
            inputError = true;
        }else if (menuList != null && menuList.stream().map(Menu::getNamaMakanan).anyMatch(itemName::equals)){
            menuText.setText("*Menu is already registered.");
            inputError = true;
        }

        double priceDouble;
        try{
            priceDouble = Double.parseDouble(price);
        }catch(NumberFormatException e){
            priceDouble = 0;
        }

        if (priceDouble < 1 || (priceDouble != (int) priceDouble)){
             priceText.setText("*Price must be an integer bigger than zero.");
             inputError = true;
        }
        if(!inputError){
            resto.addMenu(new Menu(itemName, priceDouble));
            submitBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10;");
            submitBtn.setDisable(false);

            Alert orderSuccessMsg = new Alert(Alert.AlertType.INFORMATION);
            orderSuccessMsg.setTitle("Menu Status");
            orderSuccessMsg.setHeaderText(null);
            orderSuccessMsg.setContentText("Menu is succesfully added!");
            orderSuccessMsg.showAndWait();
        }
    }

    private void handleHapusRestoran(String nama, Text invalidNameMsg, Button deleteBtn){
        deleteBtn.setDisable(true);
        deleteBtn.setStyle("-fx-background-color: #997202;-fx-border-color: #ffd573;");
        Restaurant resto = DepeFood.findRestaurant(nama);
        if(resto == null){
            invalidNameMsg.setText("*Restaurant not found.");
        }else{
            Stage popUpStage = new Stage();

            Button yesBtn = new Button("YES");
            yesBtn.setOnAction(e -> {
                deleteRestaurant(resto);
                popUpStage.close();
            });

            Button noBtn = new Button("NO");
            noBtn.setOnAction(e -> popUpStage.close());

            createDeleteConfirmationWindow(popUpStage, yesBtn, noBtn);

            deleteBtn.setDisable(false);
            deleteBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #ffd573;");
        }
    }

    public static List<Restaurant> getRestoList(){
        return restoList;
    }

    private void deleteRestaurant(Restaurant resto){
        //restoButtons.remove(restoList.indexOf(resto));
        restoBtnVBox.getChildren().remove(restoList.indexOf(resto));
        restoList.remove(resto);
        System.out.println(restoButtons.toString());
        System.out.println(restoList.toString());
    }
}
