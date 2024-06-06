package assignments.assignment4.page;

import assignments.assignment1.OrderGenerator;
import assignments.assignment3.*;
import assignments.assignment3.Menu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment4.components.BillPrinter;
import com.sun.javafx.collections.ObservableMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import assignments.assignment4.MainApp;
import javafx.util.StringConverter;

import java.security.cert.PolicyNode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ObservableMap<Button, String> allOrderButtons = FXCollections.observableHashMap();
    private ObservableList<Button> ButtonsList = FXCollections.observableArrayList(allOrderButtons.keySet());
    private static Label label = new Label();
    private MainApp mainApp;
    private User user;
    private VBox orderBtnVBox = new VBox();
    private VBox unfinishedorderBtnVBox = new VBox();


    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();

        mainApp.setScene(this.scene);
        allOrderButtons.addListener((MapChangeListener.Change<? extends Button, ? extends String> change) -> {
            if (change.wasAdded()) {
                ButtonsList.add(change.getKey());
            } else if (change.wasRemoved()) {
                ButtonsList.remove(change.getKey());
            }
        });
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Customer
        AnchorPane menuLayout = new AnchorPane();
        menuLayout.setPrefSize(500,700);
        menuLayout.setStyle("-fx-background-image: url('/Image/loginFormBG.png');");

        Text welcomeText = new Text("Welcome,");
        welcomeText.setLayoutX(131);
        welcomeText.setLayoutY(133);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setFont(new Font("Century Gothic Bold", 48));
        menuLayout.getChildren().add(welcomeText);
        
        Label userName = new Label(user.getNama());
        userName.setTextOverrun(OverrunStyle.ELLIPSIS);
        userName.setLayoutX(99);
        userName.setLayoutY(142);
        userName.setPrefSize(301,125);
        userName.setTextAlignment(TextAlignment.CENTER);
        userName.setAlignment(Pos.CENTER);
        userName.setWrapText(true);
        userName.setFont(new Font("Century Gothic Bold", 32));
        menuLayout.getChildren().add(userName);
        
        Button logOutBtn = new Button("Log Out");
        logOutBtn.setLayoutX(23);
        logOutBtn.setLayoutY(21);
        logOutBtn.setFont(new Font("System Bold", 12));
        logOutBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #f0b003; -fx-border-width: 2;");
        logOutBtn.setOnAction(e-> mainApp.setScene(mainApp.getScene("Login")));
        menuLayout.getChildren().add(logOutBtn);

        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setPrefSize(222,286);
        menuGrid.setLayoutX(87);
        menuGrid.setLayoutY(258);
        menuGrid.setVgap(5);
        menuGrid.setHgap(5);
        menuGrid.setPadding(new Insets(10));
        menuGrid.getColumnConstraints().addAll(new ColumnConstraints(150),new ColumnConstraints(150));
        menuGrid.getRowConstraints().addAll(new RowConstraints(150),new RowConstraints(150));
        menuLayout.getChildren().add(menuGrid);

        Button addOrderBtn = new Button("Make Order");
        //TODO : add image bg
        addOrderBtn.setPrefSize(140,140);
        addOrderBtn.setAlignment(Pos.CENTER);
        addOrderBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        addOrderBtn.setOnAction(e -> mainApp.setScene(addOrderScene));
        menuGrid.add(addOrderBtn,0,0);

        Button printBillBtn = new Button("View Order");
        //TODO : add image bg
        printBillBtn.setPrefSize(140,140);
        printBillBtn.setAlignment(Pos.CENTER);
        printBillBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        printBillBtn.setOnAction(e -> mainApp.setScene(printBillScene));
        menuGrid.add(printBillBtn,1,0);

        Button payBillBtn = new Button("Pay Order");
        //TODO : add image bg
        payBillBtn.setPrefSize(140,140);
        payBillBtn.setAlignment(Pos.CENTER);
        payBillBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        payBillBtn.setOnAction(e -> mainApp.setScene(payBillScene));
        menuGrid.add(payBillBtn,0,1);

        Button cekSaldoBtn = new Button("Check Balance");
        //TODO : add image bg
        cekSaldoBtn.setPrefSize(140,140);
        cekSaldoBtn.setAlignment(Pos.CENTER);
        cekSaldoBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 10; -fx-border-color: #ffd573; -fx-border-radius: 10; -fx-border-width: 3");
        cekSaldoBtn.setOnAction(e -> mainApp.setScene(cekSaldoScene));
        menuGrid.add(cekSaldoBtn,1,1);

        return new Scene(menuLayout);
    }

    private Scene createTambahPesananForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah pesanan

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

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(23);
        backBtn.setLayoutY(21);
        backBtn.setStyle("-fx-background-color: #ffba03; -fx-border-color: #fba700; -fx-border-width: 2;");
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        Text mainText = new Text("Make Order");
        mainText.setLayoutX(110);
        mainText.setLayoutY(145);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));
        layout.getChildren().add(mainText);

        Text restaurantText = new Text("Restaurant:");
        restaurantText.setLayoutX(101);
        restaurantText.setLayoutY(192);
        restaurantText.setFont(new Font(19));
        layout.getChildren().add(restaurantText);

        Text dateText = new Text("Date:");
        dateText.setLayoutX(325);
        dateText.setLayoutY(192);
        dateText.setFont(new Font(19));
        layout.getChildren().add(dateText);

        ComboBox<Restaurant> restaurantCB = new ComboBox<>(FXCollections.observableList(AdminMenu.getRestoList()));
        restaurantCB.setPromptText("Pick Restaurant!");
        restaurantCB.setLayoutX(78);
        restaurantCB.setLayoutY(199);
        restaurantCB.setPrefWidth(150);
        restaurantCB.setConverter(new StringConverter<Restaurant>() {
            @Override
            public String toString(Restaurant restaurant) {
                return restaurant.getNama();
            }

            @Override
            public Restaurant fromString(String s) {
                return null;
            }
        });
        layout.getChildren().add(restaurantCB);

        TextField date = new TextField();
        date.setPromptText("DD/MM/YYYY");
        date.setLayoutX(273);
        date.setLayoutY(199);
        date.setAlignment(Pos.CENTER);
        layout.getChildren().add(date);

        ScrollPane menuSP = new ScrollPane();
        menuSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuSP.setLayoutX(106);
        menuSP.setLayoutY(247);
        menuSP.setPrefSize(280,320);
        layout.getChildren().add(menuSP);

        VBox menuVBox = new VBox();
        menuVBox.setPrefSize(279,321);
        menuVBox.setSpacing(5);
        menuVBox.setPadding(new Insets(5,5,5,0));
        restaurantCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            menuVBox.getChildren().clear();
            menuVBox.getChildren().addAll(createMenuCheckBox(newValue));
        });
        menuSP.setContent(menuVBox);

        Button makeOrderBtn = new Button("Order!");
        makeOrderBtn.setLayoutX(212);
        makeOrderBtn.setLayoutY(586);
        makeOrderBtn.setStyle("-fx-background-color: #ffba03; -fx-border-color: #fba700; -fx-border-width: 2;");
        makeOrderBtn.setFont(new Font(17));
        makeOrderBtn.setOnAction(e ->{
            String dateStr = date.getText();
            if(OrderGenerator.validateDate(dateStr)){
                List<Menu> orderedMenus = new ArrayList<>();
                for(Object menuCB: menuVBox.getChildren().toArray()){
                    if(((CheckBox) menuCB).selectedProperty().get()){
                        orderedMenus.add((Menu) ((CheckBox)menuCB).getUserData());
                    }
                }
                Order order = new Order(
                        OrderGenerator.generateOrderID(restaurantCB.getValue().getNama(), dateStr, user.getNomorTelepon()),
                        dateStr,
                        OrderGenerator.calculateDeliveryCost(user.getLokasi()),
                        restaurantCB.getValue(),
                        orderedMenus);


                Button orderBtn = new Button(order.getOrderId());
                orderBtn.setPrefSize(373,25);
                orderBtn.setUserData(order);
                orderBtn.setStyle("-fx-background-color: #fcba03; -fx-background-radius: 20; -fx-border-color: #ffd573; -fx-border-width: 3; -fx-border-radius: 17;");
                orderBtn.setFont(new Font("Century Gothic Bold",20));
                orderBtn.setOnAction(event ->{
                    new BillPrinter(stage,mainApp,user,order,this.printBillScene);
                });
                allOrderButtons.put(orderBtn,"Not Finished");

                user.addOrderHistory(order);
                orderBtnVBox.getChildren().add(orderBtn);
                orderBtn.setOnAction(event->{
                    new BillPrinter(stage,mainApp,user,order,this.printBillScene);

                    Stage popUpStage = new Stage();

                    AnchorPane popUp = new AnchorPane();
                    popUp.setPrefSize(450,300);

                    String paymentMethod = user.getPaymentSystem() instanceof DebitPayment ? "Debit":"Credit card";
                    long totalTransaction = 0;
                    try {
                        totalTransaction = user.getPaymentSystem().processPayment(user.getSaldo(), ((long)order.getTotalHarga()));
                        if(user.getSaldo() < totalTransaction){
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        Alert orderSuccessMsg = new Alert(Alert.AlertType.WARNING);
                        orderSuccessMsg.setTitle("Insufficient");
                        orderSuccessMsg.setHeaderText(null);
                        orderSuccessMsg.setContentText("Insufficient Balance!");
                        orderSuccessMsg.showAndWait();
                    }
                    if (totalTransaction != 0) {
                        Text prompt = new Text("Pay with " + paymentMethod + " for Rp." + totalTransaction);
                        prompt.setLayoutX(35);
                        prompt.setLayoutY(87);
                        prompt.setFont(new Font("Century Gothic Bold", 16));
                        popUp.getChildren().add(prompt);

                        Button yesBtn = new Button("YES");
                        long finalTotalTransaction = totalTransaction;
                        yesBtn.setOnAction(event1 -> {
                            order.setOrderFinished(true);
                            user.setSaldo(user.getSaldo()- finalTotalTransaction);
                            unfinishedorderBtnVBox.getChildren().remove(user.getOrderHistory().indexOf(order));
                            popUpStage.close();
                        });
                        yesBtn.setLayoutX(87);
                        yesBtn.setLayoutY(140);
                        yesBtn.setPrefSize(89,45);
                        yesBtn.setTextFill(Paint.valueOf("white"));
                        yesBtn.setStyle("-fx-background-color: #df0020; -fx-border-color: #ca0020; -fx-border-width: 2;");
                        yesBtn.setFont(new Font("System Bold", 24));
                        popUp.getChildren().add(yesBtn);

                        Button noBtn = new Button("NO");
                        noBtn.setOnAction(event2 -> popUpStage.close());
                        noBtn.setLayoutX(274);
                        noBtn.setLayoutY(140);
                        noBtn.setPrefSize(89,45);
                        noBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #f0b003; -fx-border-width: 2;");
                        noBtn.setFont(new Font("System Bold", 24));
                        popUp.getChildren().add(noBtn);

                        popUpStage.setScene(new Scene(popUp));
                        popUpStage.show();
                    }
                });
                unfinishedorderBtnVBox.getChildren().add(orderBtn);
                System.out.println(allOrderButtons.toString());
                System.out.println(ButtonsList);

                Alert orderSuccessMsg = new Alert(Alert.AlertType.INFORMATION);
                orderSuccessMsg.setTitle("Process Status");
                orderSuccessMsg.setHeaderText(null);
                orderSuccessMsg.setContentText("Order is succesfully made!");
                orderSuccessMsg.showAndWait();
            }else{
                Alert orderSuccessMsg = new Alert(Alert.AlertType.ERROR);
                orderSuccessMsg.setTitle("Error");
                orderSuccessMsg.setHeaderText(null);
                orderSuccessMsg.setContentText("Incorrect date format!");
                orderSuccessMsg.showAndWait();
            }
        });
        layout.getChildren().add(makeOrderBtn);
        return new Scene(layout);
    }

    private Scene createBillPrinter(){
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

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(21);
        backBtn.setLayoutY(23);
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        Text mainText = new Text("Orders");
        mainText.setLayoutX(164);
        mainText.setLayoutY(121);
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

        orderBtnVBox.setPrefSize(387,485);
        orderBtnVBox.setStyle("-fx-background-color: #ffffff;");
        sp.setContent(orderBtnVBox);
        
        return new Scene(layout);
    }

    private Scene createBayarBillForm() {
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

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(21);
        backBtn.setLayoutY(23);
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        Text mainText = new Text("Orders");
        mainText.setLayoutX(164);
        mainText.setLayoutY(121);
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

        unfinishedorderBtnVBox.setPrefSize(387,485);
        unfinishedorderBtnVBox.setPadding(new Insets(0,0,0,5));
        unfinishedorderBtnVBox.setStyle("-fx-background-color: #ffffff;");
        for(Map.Entry<Button,String> entry : allOrderButtons.entrySet()){
            if(entry.getValue().equals("Not finished")){
                Button unfinishedOrder = new Button(entry.getKey().getText(),entry.getKey());
                Order order = (Order)unfinishedOrder.getUserData();
                unfinishedOrder.setOnAction(e->{
                    new BillPrinter(stage,mainApp,user,order,this.printBillScene);

                    Stage popUpStage = new Stage();

                    AnchorPane popUp = new AnchorPane();
                    popUp.setPrefSize(450,300);

                    String paymentMethod = user.getPaymentSystem() instanceof DebitPayment ? "Debit":"Credit card";
                    long totalTransaction = 0;
                    try {
                        totalTransaction = user.getPaymentSystem().processPayment(user.getSaldo(), ((long)order.getTotalHarga()));
                        if(user.getSaldo()< totalTransaction){
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        Alert orderSuccessMsg = new Alert(Alert.AlertType.WARNING);
                        orderSuccessMsg.setTitle("Insufficient");
                        orderSuccessMsg.setHeaderText(null);
                        orderSuccessMsg.setContentText("Insufficient Balance!");
                        orderSuccessMsg.showAndWait();
                    }
                    Text prompt = new Text("Pay with " + paymentMethod + " for Rp." + totalTransaction);
                    prompt.setLayoutX(35);
                    prompt.setLayoutY(87);
                    prompt.setFont(new Font("Century Gothic Bold", 16));
                    popUp.getChildren().add(prompt);

                    Button yesBtn = new Button("YES");
                    long finalTotalTransaction = totalTransaction;
                    yesBtn.setOnAction(event -> {
                        entry.setValue("Finished");
                        order.setOrderFinished(true);
                        user.setSaldo(user.getSaldo()- finalTotalTransaction);
                        unfinishedorderBtnVBox.getChildren().remove(user.getOrderHistory().indexOf(order));
                        popUpStage.close();
                    });
                    yesBtn.setLayoutX(87);
                    yesBtn.setLayoutY(140);
                    yesBtn.setPrefSize(89,45);
                    yesBtn.setTextFill(Paint.valueOf("white"));
                    yesBtn.setStyle("-fx-background-color: #df0020; -fx-border-color: #ca0020; -fx-border-width: 2;");
                    yesBtn.setFont(new Font("System Bold", 24));
                    popUp.getChildren().add(yesBtn);

                    Button noBtn = new Button("NO");
                    noBtn.setOnAction(event -> popUpStage.close());
                    noBtn.setLayoutX(274);
                    noBtn.setLayoutY(140);
                    noBtn.setPrefSize(89,45);
                    noBtn.setStyle("-fx-background-color: #fcba03; -fx-border-color: #f0b003; -fx-border-width: 2;");
                    noBtn.setFont(new Font("System Bold", 24));
                    popUp.getChildren().add(noBtn);

                    popUpStage.setScene(new Scene(popUp));
                    popUpStage.show();
                });
            }
        }
        sp.setContent(unfinishedorderBtnVBox);

        return new Scene(layout);
    }


    private Scene createCekSaldoScene() {
        AnchorPane layout =  new AnchorPane();
        layout.setPrefSize(500,700);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName = new Label();
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(150,14);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        Text mainText = new Text("Saldo");
        mainText.setLayoutX(184);
        mainText.setLayoutY(217);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic",48));
        layout.getChildren().add(mainText);

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(21);
        backBtn.setLayoutY(23);
        backBtn.setOnAction(e -> mainApp.setScene(this.scene));
        layout.getChildren().add(backBtn);

        Text paymentMethod = new Text(user.getPaymentSystem() instanceof CreditCardPayment?"Credit Card:":"Debit:");
        paymentMethod.setLayoutX(190);
        paymentMethod.setLayoutY(281);
        paymentMethod.setFont(new Font(29));
        layout.getChildren().add(paymentMethod);

        Text saldo = new Text("Rp." + Double.toString(user.getSaldo()));
        saldo.setLayoutX(129);
        saldo.setLayoutY(340);
        saldo.setFont(new Font(27));
        layout.getChildren().add(saldo);
        return new Scene(layout);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        //TODO: Implementasi validasi isian pesanan
        try {

        } catch (Exception e) {

        }
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran,Button orderBtn) {
        //TODO: Implementasi validasi pembayaran
        try {

        } catch (Exception e) {

        }
    }

    private List<CheckBox> createMenuCheckBox(Restaurant newValue) {
        List<CheckBox> checkBoxes = new ArrayList<>();
        for(Menu menu: newValue.getMenu()){
            CheckBox menuCB = new CheckBox(menu.getNamaMakanan() + " " + menu.getHarga());
            menuCB.setPrefSize(268,17);
            menuCB.setStyle("-fx-background-color: #ffda73;");
            menuCB.setUserData(menu);
            checkBoxes.add(menuCB);
        }
        return checkBoxes;
    }

}