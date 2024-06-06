package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.ScrollPane;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

import java.util.List;

public class BillPrinter {
    private final Order order;
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private Scene viewOrder;

    public BillPrinter(Stage stage, MainApp mainApp, User user, Order order, Scene viewOrder) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
        this.order = order;
        this.viewOrder = viewOrder;
    }

    private Scene createBillPrinterForm(){
        AnchorPane layout = new AnchorPane();
        layout.setPrefSize(700,500);
        layout.setStyle("-fx-background-image: url('/Image/loginFormBG.png')");

        Label userName = new Label(user.getNama());
        userName.setLayoutX(300);
        userName.setLayoutY(24);
        userName.setPrefSize(14,150);
        userName.setAlignment(Pos.CENTER_RIGHT);
        userName.setFont(new Font("System Bold", 13));
        layout.getChildren().add(userName);

        Text mainText = new Text("Bill");
        mainText.setLayoutX(219);
        mainText.setLayoutY(146);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.setFont(new Font("Century Gothic Bold Italic", 48));
        layout.getChildren().add(mainText);

        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(23);
        backBtn.setLayoutY(21);
        backBtn.setOnAction(e -> mainApp.setScene(this.viewOrder));
        layout.getChildren().add(backBtn);

        GridPane billGrid = new GridPane();
        billGrid.setPrefSize(104,169);
        billGrid.getColumnConstraints().add(new ColumnConstraints(100));
        billGrid.getRowConstraints().add(new RowConstraints(30));
        billGrid.setStyle("-fx-background-color: #ffffff ");
        billGrid.setPadding(new Insets(5));
        layout.getChildren().add(billGrid);

        ScrollPane menuScroll = new ScrollPane();
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuScroll.setPrefSize(200,200);
        billGrid.add(menuScroll,0,6,2,1);

        VBox menuVBox = new VBox();
        menuVBox.setPrefSize(128,279);
        menuVBox.getChildren().add(getOrderedMenus());
        menuScroll.setContent(menuVBox);

        Text orderIDText = new Text("Order ID:");
        orderIDText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(orderIDText,0,0);

        Text orderID = new Text(order.getOrderId());
        orderID.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(orderID,1,0);

        Text dateText = new Text("Date:");
        dateText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(dateText,0,1);

        Text date = new Text(order.getTanggal());
        date.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(dateText,1,1);

        Text restaurantText = new Text("Restaurant:");
        restaurantText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(restaurantText,0,2);

        Text restaurant = new Text(order.getRestaurant().getNama());
        restaurant.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(restaurant,1,2);

        Text locationText = new Text("Location:");
        locationText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(locationText,0,3);

        Text location = new Text(user.getLokasi());
        location.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(location,1,3);

        Text statusText = new Text("Status:");
        statusText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(statusText,0,4);

        Text status = new Text(order.getOrderFinished()?"Finished":"Not Finished");
        status.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(status,1,4);

        Text pesananText = new Text("Pesanan:");
        pesananText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(pesananText,0,5,2,1);

        Text biayaOngkosText = new Text("Biaya Ongkos Kirim:");
        biayaOngkosText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(biayaOngkosText,0,7);

        Text biayaOngkos = new Text(Integer.toString(order.getOngkir()));
        biayaOngkos.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(biayaOngkos,1,7);

        Text totalText = new Text("Total Biaya:");
        totalText.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(totalText,0,8);

        Text total = new Text(Double.toString(order.getTotalHarga()));
        total.setTextAlignment(TextAlignment.CENTER);
        billGrid.add(total,1,8);


        return new Scene(layout, 400, 200);
    }

    private Group getOrderedMenus(){
        Group orderedMenuTexts = new Group();
        for(Menu menu: order.getSortedMenu()){
            Text menuText = new Text("-" + menu.getNamaMakanan() + "Rp." + menu.getHarga());
            menuText.setTextAlignment(TextAlignment.CENTER);
        }
        return orderedMenuTexts;
    }


    public Scene getScene() {
        return this.createBillPrinterForm();
    }
}
