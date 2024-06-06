package assignments.assignmentmodel.restomodel;

public class Menu {
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    //GETTER method
    public String getNamaMakanan(){
        return this.namaMakanan;
    }

    public double getHarga(){
        return this.harga;
    }

    //override Object toString() method
    public String toString(){
        return this.namaMakanan + " " + (int) this.harga;
    }
}
