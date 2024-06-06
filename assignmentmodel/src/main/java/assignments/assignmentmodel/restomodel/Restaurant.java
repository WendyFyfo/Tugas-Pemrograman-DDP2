package assignments.assignmentmodel.restomodel;

import assignments.assignmentmodel.restomodel.Menu;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<>();
    private long saldo = 0;

    public Restaurant(String nama){
        this.nama = nama;
    }
    
    //GETTER method
    public String getNama(){
        return this.nama;
    }

    public ArrayList<Menu> getMenu(){
        return this.menu;
    }

    public long getSaldo(){ return this.saldo;}

    //method menambahkan saldo
    public void addSaldo(long amount){
        this.saldo += amount;
    }
    //add menu ke arraylist menu
    public void addMenu(Menu menu) {
        this.menu.add(menu);
    }
}
