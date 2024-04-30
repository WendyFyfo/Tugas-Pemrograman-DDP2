package assignments.assignment2;

import assignments.assignment1.OrderGenerator;
import assignments.assignmentmodel.User;
import assignments.assignmentmodel.Order;
import assignments.assignmentmodel.Restaurant;
import assignments.assignmentmodel.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList;

    private static final int[] biayaOngkir = {10000, 20000, 35000, 40000, 60000};

    public static void main(String[] args) {
        boolean programRunning = true;
        User userLoggedIn;
        while(programRunning){
            printHeader(); //Cetak Header program
            startMenu(); //Cetak menu Start
            initUser(); //init class User
            int command = input.nextInt(); //input command program
            input.nextLine();

            //handle command
            if(command == 1){
                //Login menu
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                userLoggedIn = getUser(nama, noTelp); //get user info berdasarkan input login
                if (userLoggedIn == null){
                    //hentikan login menu bila user tidak ditemukan
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan !");
                    continue;
                }
                System.out.println("Selamat datang " + userLoggedIn.getNama() + "!");


                boolean isLoggedIn = true;
                //Masuk ke menu berdasarkan role user
                if(userLoggedIn.getRole() == "Customer"){
                    //start menu customer
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        //handle commandCust
                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false; //exit dari program
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali"); //handle invalid input
                        }
                    }
                }else{
                    //Start menu Admin
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        //handle commandAdmin
                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                //exit program
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    //mengembalikan object User dari userList
    public static User getUser(String nama, String nomorTelepon){
        for(User user: userList){
            if (nama.equals(user.getNama()) && nomorTelepon.equals(user.getNomorTelepon())){
                return user;
            }
        }
        return null; //kembalikan null bila tidak ada data user yang cocok
    }

    //method untuk start menu membuat pesanan
    public static void handleBuatPesanan(User user){
        System.out.print("----------Buat Pesanan----------");
        boolean generatingOrder = true;
        while(generatingOrder) {
            //validasi input user
            String namaRestoran = "";
            String tanggalOrder = "";
            Restaurant restoObject = null;
            do{
                namaRestoran = OrderGenerator.inputRestaurantName("");
                if(namaRestoran.isEmpty()){continue;}

                //cek keberadaan restoran di sistem
                for(Restaurant resto: restoList){
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
            String orderID = OrderGenerator.generateOrderID(namaRestoran, tanggalOrder, user.getNomorTelepon());
            
            //input jumlah pesanan dan menu yang dipesan
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = input.nextInt();
            input.nextLine();

            System.out.println("Order: ");
            int i = 0;
            ArrayList<String> orderList = new ArrayList<String>(jumlahPesanan);
            while (i < jumlahPesanan) {
                orderList.add(input.nextLine());
                i++;
            }

            //cek apakah menu ada pada restoran
            ArrayList<Menu> orderedMenu = new ArrayList<>();
            for(Menu menu: restoObject.getMenu()){
                String namaMakanan = menu.getNamaMakanan();
                if (orderList.size() > 0 ){ //stop if orderlist is empty/ completely moved to orderedMenu
                        if (namaMakanan.equals(orderList.get(0))){
                            orderList.remove(0);
                            orderedMenu.add(menu);
                            continue;
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
            user.setOrder(new Order(orderID, tanggalOrder, biayaOngkir["PUTSB".indexOf(user.getLokasi())], restoObject, orderedMenu ));
            //mencetak pesan pesanan diterima
            System.out.println("pesanan dengan ID " + orderID + " diterima!");
            generatingOrder = false;
        }
    }

    //method untuk start menu cetakBill
    public static void handleCetakBill(User user){
        //cek apakah user sudah pernah memesan
        if(user.getOrderHistory().size() == 0){
            System.out.println("\nOrder Kosong:\nCustomer "+ user.getNama() + " belum pernah membuat pesanan.\n");
            return;
        }

        //start menu cetakBill
        System.out.println("----------Cetak Bill----------");
        boolean cetakBill = true;
        while (cetakBill){
            //input Order ID
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();

            //cek apakah orderID ada pada Sistem dan mencetak bill bila ada
            for (Order order: user.getOrderHistory()){
                if (order.getOrderId().equals(orderID)){
                    System.out.println("Bill:");
                    System.out.println("Order ID: " + orderID);
                    System.out.println("Tanggal Pemesanan: " + order.getTanggalPemesanan());
                    System.out.println("Restaurant: " + order.getRestaurant().getNama());
                    System.out.println("Lokasi: " + user.getLokasi());
                    System.out.println("Status Pengiriman: " + order.getOrderStatus() );
                    System.out.println("Pesanan:");
                    for(Menu menu: order.getItems()){
                        System.out.println("- " + menu.toString());
                    }
                    System.out.println("Biaya Ongkos Kirim: Rp " + order.getBiayaOngkosKirim() );

                    int totalBiaya = 0;
                    for(Menu menu: order.getItems()){ //fix iterated array
                        totalBiaya += (int) menu.getHarga();
                    }
                    totalBiaya += order.getBiayaOngkosKirim();
                    System.out.println("Total Biaya: Rp " + totalBiaya);
                    cetakBill = false;
                    break;
                }
            }
            if (cetakBill == false){
                System.out.println();
                break;
            }
            System.out.println("Order ID tidak dapat ditemukan.\n");
        }
    }

    //method untuk mencetak menu pada restaurant
    public static void handleLihatMenu(){
        System.out.println("----------Lihat Menu----------");
        //Start menu lihat menu
        boolean lihatMenu = true;
        while (lihatMenu){
            //input nama Restoran
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            //cek apakah restoran ada pada sistem
            Restaurant restoObject = null;
            for(Restaurant resto: restoList){
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
            for (Restaurant resto: restoList){
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    ArrayList<Menu> menu= resto.getMenu();
                    for(int i = 0;  i< menu.size(); i++){
                        System.out.println((i+1) +". " + menu.get(i).toString());
                    }
                    lihatMenu = false;
                    break;
                }
            }
            if (lihatMenu == false){
                System.out.println();
                break;
            }
            System.out.println("Restoran tidak terdaftar pada sistem.\n");
        }
    }

    //method untuk mengupdate data field status pesanan pada User
    public static void handleUpdateStatusPesanan(User user){
        System.out.println("----------Update Status Pesanan----------");
        boolean updateOrderStatus = true;
        while(updateOrderStatus){
            //input order ID
            System.out.print("Order ID: ");
            String orderID = input.nextLine();
            //cek apakah order ID ada pada Sistem
            for (Order order: user.getOrderHistory()) {
                //mengganti status pesanan bila order ID ada
                if (order.getOrderId().equals(orderID)) {
                    System.out.print("Status: ");
                    String newStatus = input.nextLine();
                    if (order.getOrderStatus().equalsIgnoreCase(newStatus) == false){
                        order.setOrderStatus(newStatus);
                        System.out.println("Status pesanan dengan ID " + order.getOrderId() + " berhasil diupdate!\n");
                        updateOrderStatus = false;
                    }else{
                        System.out.println("Status pesanan dengan ID " + order.getOrderId() + "tidak berhasil diupdate!\n");
                    }
                }
            }
            if(updateOrderStatus){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
            }else{
                break;
            }
        }
    }

    //method admin untuk menambahkan objek restoran ke sistem
    public static void handleTambahRestoran(){
        System.out.println("----------Tambah Restoran---------");
        boolean tambahRestoran  = true;
        while(tambahRestoran){
            //input nama restoran
            System.out.print("Nama: ");
            String namaRestoran = input.nextLine().trim();
            //validasi panjang input nama restoran
            if (namaRestoran.length() < 4) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            //cek apakah nama restoran sudah pernah terdaftar
            if(restoList != null){
                boolean namaTerdaftar = false;
                for(Restaurant resto: restoList){
                    if (namaRestoran.equals(resto.getNama())){
                        System.out.println("Restoran dengan nama " + namaRestoran + " sudah pernah terdaftar. mohon masukkan nama yang berbeda!\n");
                        namaTerdaftar = true;
                        break;
                    }
                }
                if (namaTerdaftar){
                    continue;
                }
            }

            //input menu yang ada pada restoran
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine();
            ArrayList<String[]> tempMenuList = new ArrayList<>();
            String menu = "";
            int splitPosition = 0;
            int i = 0;
            while (i < jumlahMakanan){
                menu = input.nextLine().trim();
                splitPosition = menu.lastIndexOf(" ");
                if(splitPosition == -1){
                    System.out.println("Harus menulis harga setelah nama menu!\n");
                    tempMenuList.clear(); //clear arraylist bila input harga invalid
                    break;
                }
                tempMenuList.add(new String[]{menu.substring(0,splitPosition), menu.substring(splitPosition+1)});
                i++;
            }
            if (tempMenuList.isEmpty()){
                continue;
            }

            for(String[] item: tempMenuList){
                if(item[1].replaceAll("[0-9]+", "").length() > 0) {
                    System.out.println("Harga menu harus bilangan bulat\n");
                    tempMenuList.clear(); //clear arraylist bila input harga invalid
                    break;
                }
            }
            if (tempMenuList.isEmpty()){
                continue;
            }

            //Sort menu pada restoran berdasarkan harga dan nama
            ArrayList<String[]> menuList = new ArrayList<>();
            menuList.add(tempMenuList.get(0));
            for(int j=1 ; j < jumlahMakanan; j++){
                double hargaMenu2 = Double.parseDouble(tempMenuList.get(j)[1]);
                for(int k=1; k < menuList.size()+ 1;k++){
                    double hargaMenu1 = Double.parseDouble(menuList.get(j-k)[1]);
                    if(hargaMenu1 < hargaMenu2){
                        menuList.add(j+1-k, tempMenuList.get(j));
                        break;
                    }else if (hargaMenu1 == hargaMenu2){
                        int compareNamaMenu = menuList.get(j-k)[0].compareTo(tempMenuList.get(j)[0]) ;
                        if(compareNamaMenu < 0){
                            if(j == jumlahMakanan-1){
                                menuList.add(j+1-k, tempMenuList.get(j));
                                break;
                            }
                            continue;
                        }else if (compareNamaMenu == 0){
                            menuList.add(j-k, tempMenuList.get(j));
                            break;
                        }else{
                            menuList.add(j,tempMenuList.get(j));
                            break;
                        }
                    }else{
                        if(j-k == 0){ //check apakah menu1 dicompare dengan menuList[0]
                            menuList.add(0, tempMenuList.get(j));
                            break;
                        }else { 
                            continue;
                        }
                        
                    }
                }
            }

            //memasukkann Resto dan daftar menunya ke sistem
            restoList.add(new Restaurant(namaRestoran));
            for(String[] item: menuList) {
                restoList.get(restoList.size()-1).addMenu(new Menu(item[0],Double.parseDouble(item[1])));
            }
            System.out.println("Restaurant " + namaRestoran + " berhasil terdaftar.");
            tambahRestoran = false;
        }
    }

    //method admin untuk menghapus objek restoran dari sistem
    public static void handleHapusRestoran(){
        System.out.println("----------Hapus Restoran----------");
        boolean hapusRestoran = true;
        boolean restoFound =  false;
        while(hapusRestoran){
            //input nama restoran
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            //cek apakah nama restoran ada pada sistem
            for(Restaurant resto: restoList){
                //menghapus restoran dari sistem bila namanya ada pada sistem
                if (namaRestoran.equalsIgnoreCase(resto.getNama())){
                    restoFound = true;
                    restoList.remove(resto);
                    System.out.println("Restoran berhasil dihapus.");
                    break;
                }
            }
            if(restoFound == false){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            hapusRestoran = false;
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
