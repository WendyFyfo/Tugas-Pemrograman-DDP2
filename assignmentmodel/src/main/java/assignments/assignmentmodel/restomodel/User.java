package assignments.assignmentmodel.restomodel;

import java.util.ArrayList;

import assignments.assignmentmodel.payment.DebitPayment;
import assignments.assignmentmodel.payment.DepeFoodPaymentSystem;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private DepeFoodPaymentSystem payment;
    private long saldo;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = new DebitPayment();
        this.saldo = 0;
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

    public DepeFoodPaymentSystem getPayment(){
        return this.payment;
    }

    public long getSaldo(){
        return this.saldo;
    }

    public void payOrder(long amount) {
        this.saldo -= amount;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

}
