package assignments.assignment2;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items;
    private String orderStatus = "Not Finished";

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){
        this.orderId = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = items;
    }
    
    //SETTER method
    public void setOrderStatus(String newStatus){
        this.orderStatus = newStatus;
    }

    //GETTER method
    public String getOrderId(){
        return this.orderId;
    }

    public String getTanggalPemesanan(){
        return this.tanggalPemesanan;
    }

    public int getBiayaOngkosKirim(){
        return this.biayaOngkosKirim;
    }

    public Restaurant getRestaurant(){
        return this.restaurant;
    }

    public ArrayList<Menu> getItems(){
        return this.items;
    }

    public String getOrderStatus(){
        return this.orderStatus;
    }
}
