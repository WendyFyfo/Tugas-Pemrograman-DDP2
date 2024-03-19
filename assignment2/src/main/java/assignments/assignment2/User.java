package assignments.assignment2;

import java.util.ArrayList;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private String deliveryStatus = "not finished";

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    //SETTER method
    public void setOrder(Order order){
        this.orderHistory.add(order);
    }

    public void setDeliveryStatus(String newStatus){
        this.deliveryStatus = newStatus;
    }

    //GETTER method
    public String getNama(){
        return this.nama;
    }
    public String getNomorTelepon() {
        return this.nomorTelepon;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public String getRole(){
        return this.role;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

}
