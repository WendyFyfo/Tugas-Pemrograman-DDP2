package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<>();

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

    //add menu ke arraylist menu
    public void addMenu(Menu menu) {
        this.menu.add(menu);
    }
}
