package assignments.assignmentmodel;

import java.util.ArrayList;
import assignments.assignmentmodel.payment.CreditCardPayment;
import assignments.assignmentmodel.payment.DebitPayment;
import assignments.assignmentmodel.payment.DepeFoodPaymentSystem;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private String deliveryStatus = "not finished";
    private DepeFoodPaymentSystem payment;
    private long saldo;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = payment;
        this.saldo = saldo;
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
