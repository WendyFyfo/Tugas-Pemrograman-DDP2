package assignments.assignmentmodel.restomodel;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items;
    private String orderStatus;
    private int totalBiaya;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){
        this.orderId = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = items;
        this.orderStatus = "Not Finished";
        countTotalBiaya();
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

    public int getTotalBiaya(){
        return this.totalBiaya;
    }

    //method untuk menghitung total biaya
    public void countTotalBiaya(){
        for(Menu menu: this.getItems()){ //fix iterated array
            this.totalBiaya += (int) menu.getHarga();
        }
        this.totalBiaya += this.getBiayaOngkosKirim();
    }
}
