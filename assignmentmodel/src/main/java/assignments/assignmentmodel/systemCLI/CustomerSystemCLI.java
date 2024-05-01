package assignments.assignmentmodel.systemCLI;

import assignments.assignmentmodel.payment.*;
import assignments.assignmentmodel.restomodel.*;
import assignments.assignment1.OrderGenerator;

import java.util.ArrayList;
import java.util.Scanner;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI {
    private User customerLoggedIn;
    private static final Scanner input = new Scanner(System.in);
    private static final int[] biayaOngkir = {10000, 20000, 35000, 40000, 60000};

    //with-arg constructor dan menjalankan menu customer
    public CustomerSystemCLI(User customerLoggedIn){
        this.customerLoggedIn = customerLoggedIn;
        run();
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    public void run(){
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    @Override
    public boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> System.out.printf("\nSisa saldo sebesar Rp %d", this.customerLoggedIn.getSaldo());
            case 6 -> {System.out.println();return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    void handleBuatPesanan(){
        //Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("----------Buat Pesanan----------");
        boolean generatingOrder = true;
        while(generatingOrder) {
            //validasi input user
            String namaRestoran;
            String tanggalOrder = "";
            Restaurant restoObject = null;
            do{
                namaRestoran = OrderGenerator.inputRestaurantName("");
                if(namaRestoran.isEmpty()){continue;}

                //cek keberadaan restoran di sistem
                for(Restaurant resto: AdminSystemCLI.restoList){
                    if(namaRestoran.equalsIgnoreCase(resto.getNama())){
                        restoObject = resto;
                        break;
                    }
                }
                if (restoObject == null){
                    System.out.println("Restoran tidak terdaftar pada sistem.\n");
                    namaRestoran = "";
                    continue;
                }

                tanggalOrder = OrderGenerator.inputDate("");
                if(tanggalOrder.isEmpty()){namaRestoran = "";}
            }while(namaRestoran.isEmpty());

            //membuat orderID dan mencetak pesan proses pembuatan pesanan sukses
            String orderID = OrderGenerator.generateOrderID(namaRestoran, tanggalOrder, this.customerLoggedIn.getNomorTelepon());

            //input jumlah pesanan dan menu yang dipesan
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = input.nextInt();
            input.nextLine();

            System.out.println("Order: ");
            int i = 0;
            ArrayList<String> orderList = new ArrayList<>(jumlahPesanan);
            while (i < jumlahPesanan) {
                orderList.add(input.nextLine());
                i++;
            }

            //cek apakah menu ada pada restoran
            ArrayList<Menu> orderedMenu = new ArrayList<>();
            for(Menu menu: restoObject.getMenu()){
                String namaMakanan = menu.getNamaMakanan();
                if (orderList.size() > 0 ){ //stop if orderlist is empty orcompletely moved to orderedMenu
                    if (namaMakanan.equals(orderList.getFirst())){
                        orderList.removeFirst();
                        orderedMenu.add(menu);
                    }
                }else{
                    break;
                }
            }

            if (orderedMenu.size() != jumlahPesanan){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }

            //Make new order for user instance;
            this.customerLoggedIn.setOrder(new Order(orderID, tanggalOrder, biayaOngkir["PUTSB".indexOf(this.customerLoggedIn.getLokasi())], restoObject, orderedMenu ));
            //mencetak pesan pesanan diterima
            System.out.println("pesanan dengan ID " + orderID + " diterima!");
            generatingOrder = false;
        }

    }

    Order handleCetakBill(){
        //method untuk handle ketika customer ingin cetak bill
            if(this.customerLoggedIn.getOrderHistory().isEmpty()){
                System.out.println("\nOrder Kosong:\nCustomer "+ this.customerLoggedIn.getNama() + " belum pernah membuat pesanan.\n");
                return null;
            }

            //start menu cetakBill
            System.out.println("----------Cetak Bill----------");
            while (true){
                //input Order ID
                System.out.print("Masukkan Order ID: ");
                String orderID = input.nextLine();

                //cek apakah orderID ada pada Sistem dan mencetak bill bila ada
                for (Order order: this.customerLoggedIn.getOrderHistory()){
                    if (order.getOrderId().equals(orderID)){
                        System.out.println("\nBill:");
                        System.out.println("Order ID: " + orderID);
                        System.out.println("Tanggal Pemesanan: " + order.getTanggalPemesanan());
                        System.out.println("Restaurant: " + order.getRestaurant().getNama());
                        System.out.println("Lokasi: " + this.customerLoggedIn.getLokasi());
                        System.out.println("Status Pengiriman: " + order.getOrderStatus() );
                        System.out.println("Pesanan:");
                        for(Menu menu: order.getItems()){
                            System.out.println("- " + menu.toString());
                        }
                        System.out.println("Biaya Ongkos Kirim: Rp " + order.getBiayaOngkosKirim() );
                        System.out.println("Total Biaya: Rp " + order.getTotalBiaya());
                        return order;
                    }
                }

                System.out.println("Order ID tidak dapat ditemukan.\n");
            }
    }

    void handleLihatMenu(){
        // method untuk handle ketika customer ingin melihat menu
        System.out.println("----------Lihat Menu----------");
        //Start menu lihat menu
        boolean lihatMenu = true;
        while (lihatMenu){
            //input nama Restoran
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            //cek apakah restoran ada pada sistem
            Restaurant restoObject = null;
            for(Restaurant resto: AdminSystemCLI.restoList){
                if(namaRestoran.equalsIgnoreCase(resto.getNama())){
                    restoObject = resto;
                    break;
                }
            }
            if (restoObject == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            //mencetak seluruh menu yang ada pada resto dan harganya
            System.out.println("Menu: ");
            for (Restaurant resto: AdminSystemCLI.restoList){
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    ArrayList<Menu> menu= resto.getMenu();
                    for(int i = 0;  i< menu.size(); i++){
                        System.out.println((i+1) +". " + menu.get(i).toString());
                    }
                    lihatMenu = false;
                    break;
                }
            }
            if (lihatMenu){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
            }
        }
    }

    void handleBayarBill(){
        //method untuk handle ketika customer ingin melihat menu
        Order order = handleCetakBill();
        if (order == null){return;} //check apakah customer sudah pernah membuat order

        //check apakah customer sudah membayar bill
        if (order.getOrderStatus().equals("Finished")){
            System.out.println("Pesanan dengan ID ini sudah lunas!");
            return;
        }

        System.out.println("\nOpsi Pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit");
        System.out.print("Pilihan Metode Pembayaran: ");
        String metodePembayaran = input.nextLine();

        DepeFoodPaymentSystem payment = this.customerLoggedIn.getPayment(); //mengambil data paymnet customer
        int tagihan = order.getTotalBiaya();
        long totalbiaya = payment.processPayment(tagihan);
        if(metodePembayaran.equals("1")){
            if(payment instanceof CreditCardPayment){
                if(totalbiaya > this.customerLoggedIn.getSaldo()){
                    System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain!");
                    return;
                }
                customerLoggedIn.payOrder(totalbiaya);
                System.out.printf("Berhasil Membayar Bill sebesar Rp %d dengan biaya transaksi sebesar Rp %d", tagihan, totalbiaya*2/102);
            }else{
                System.out.println("User belum memiliki metode pembayaran ini!");
                return;
            }
        }else if (metodePembayaran.equals("2")){
            if(payment instanceof DebitPayment){
                if(totalbiaya == 0){return;}
                if(totalbiaya > this.customerLoggedIn.getSaldo()){
                    System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain!");
                    return;
                }
                customerLoggedIn.payOrder(totalbiaya);
                System.out.printf("Berhasil Membayar Bill sebesar Rp %d", tagihan);
            }else{
                System.out.println("User belum memiliki metode pembayaran ini!");
                return;
            }
        }
        order.getRestaurant().addSaldo(tagihan); //memasukkan saldo hasil penjualan ke saldo restaurant
        order.setOrderStatus("Finished"); //mengubah status order setelah membayar
    }
}
