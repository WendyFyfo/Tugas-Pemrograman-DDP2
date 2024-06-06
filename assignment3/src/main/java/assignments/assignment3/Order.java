package assignments.assignment3;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant restaurant;
    private boolean orderFinished;
    private List<Menu> items;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, List<Menu> items) {
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.restaurant = resto;
        this.orderFinished = false;
        this.items = items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean getOrderFinished() {
        return this.orderFinished;
    }

    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getOngkir() {
        return ongkir;
    }

    public List<Menu> getItems() {
        return items;
    }

    public List<Menu> getSortedMenu() {
        List<Menu> menuArr = new ArrayList<>(getItems().size());
        for (int i = 0; i < getItems().size(); i++) {
            menuArr.add(i,getItems().get(i));
        }
        int n = menuArr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (menuArr.get(j).getHarga() > menuArr.get(j+1).getHarga()) {
                    Menu temp = menuArr.get(j);
                    menuArr.add(j,menuArr.get(j+1));
                    menuArr.add(j+1,temp);
                }
            }
        }
        return menuArr;
    }

    public double getTotalHarga() {
        double sum = 0;
        for (Menu menu : getItems()) {
            sum += menu.getHarga();
        }
        return sum += getOngkir();
    }
}
