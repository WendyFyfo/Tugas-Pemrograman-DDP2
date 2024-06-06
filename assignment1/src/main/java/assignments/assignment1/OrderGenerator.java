package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
    private static final String[] biayaOngkir = {"10.000", "20.000", "35.000", "40.000", "60.000"}; //Array menyimpan biaya ongkir tiap lokasi
    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     *
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        do{
            namaRestoran = inputRestaurantName(namaRestoran);
            if(namaRestoran.isEmpty()){continue;}

            tanggalOrder = inputDate(tanggalOrder);
            if(tanggalOrder.isEmpty()){
                namaRestoran = "";
                continue;
            }

            noTelepon = inputNoTelepon(noTelepon);
            if(noTelepon.isEmpty()){
                namaRestoran = "";
                tanggalOrder = "";
            }
        }while(namaRestoran.isEmpty() || tanggalOrder.isEmpty() || noTelepon.isEmpty());
        
        StringBuilder orderID = new StringBuilder(); //Stringbuilder untuk menyimpan OrderID
        orderID.append(namaRestoran.substring(0,4).toUpperCase()); //mengambil 4 karakter alphanumeric nama restoran untuk OrderID
        String[] tanggalOrderArr = tanggalOrder.split("/");
        orderID.append(String.join("",tanggalOrderArr)); //mengisi karakter 4-11 Order ID dengan tanggal order

        //Menghitung karakter 12-13 Order ID dari no telepon
        int noTeleponSum = 0;
        for (int i = 0; i < noTelepon.length(); i++){
            noTeleponSum += Character.getNumericValue(noTelepon.charAt(i));
        }
        String noTeleponChars = Integer.toString(noTeleponSum % 100);
        if (noTeleponChars.length() == 2){
            orderID.append(noTeleponChars);
        }else {
            orderID.append("0" + noTeleponChars);
        }

        orderID.append(generateCheckSum(orderID.toString())); //generate checksum untuk 2 karakter terakhir OrderID
        return orderID.toString();
    }


    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String orderID, String lokasi){
        do{
            orderID = inputOrderID(orderID); //menerima dan validasi input orderID
            if(orderID.isEmpty()){continue;}
            
            lokasi = inputLokasi(lokasi); //menerima validasi input lokasi   
        }while(orderID.isEmpty() || lokasi.isEmpty());

        System.out.println();
        //return string Bill
        return String.format("Bill:\nOrder ID: %s\nTanggal Pemesanan: %s\nLokasi Pengiriman: %s\nBiaya Ongkos Kirim: Rp %s\n",
                orderID, orderID.substring(4,6) + "/" + orderID.substring(6,8) + "/" + orderID.substring(8,12),
                lokasi, biayaOngkir["PUTSB".indexOf(lokasi)]);
    }
    
    //method untuk meminta dan validasi input nama restoran
    public static String inputRestaurantName(String name){
        String namaRestoran;
        if (name.isEmpty()){
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine();
            String namaRestoranFormatted = namaRestoran.trim().toUpperCase().replaceAll("[^A-Z0-9]+","");
            if (namaRestoranFormatted.length() < 4){
                System.out.println("Nama Restoran tidak valid!\n");
                return "";
            }
        }else{
            namaRestoran = name;
        }
        return namaRestoran;
    }
    
    //method untuk meminta dan validasi input tanggal order
    public static String inputDate(String date){
        String tanggalOrderInput;
        String[] tanggalOrder;
        if (date.isEmpty()){
            System.out.print("Tanggal pemesanan: ");
            tanggalOrderInput = input.nextLine();
            if (tanggalOrderInput.replaceAll("[^/]+", "").length() != 2){
                System.out.println("Masukkan tanggal sesuai format DD/MM/YYYY!\n");
                return "";
            }else if (!tanggalOrderInput.replaceAll("[0-9/]+", "").isEmpty()){
                System.out.println("Masukkan tanggal sesuai format DD/MM/YYYY!\n");
                return "";
            }

            tanggalOrder = tanggalOrderInput.replaceAll("[^0-9/]+","").split("/");
            if (tanggalOrder[0].length() != 2 || tanggalOrder[1].length() != 2 || tanggalOrder[2].length() != 4){
                System.out.println("Masukkan tanggal sesuai format DD/MM/YYYY!\n");
                return "";
            }
        }else{
            tanggalOrderInput = date;
        }
        return tanggalOrderInput;
    }

    //method untuk meminta dan validasi input nomor telepon
    public static String inputNoTelepon(String nomor){
        String noTelepon;
        if (nomor.isEmpty()){
            System.out.print("No. Telepon: ");
            noTelepon = input.nextLine();
            if (!noTelepon.replaceAll("[0-9]+", "").isEmpty()){
                System.out.println("Harap masukkan no. telepon dalam bilangan bulat positif.");
                return "";
            }
        }else{
            noTelepon = nomor;
        }
        return noTelepon;
    }

    //method untuk meminta dan validasi input orderID
    public static String inputOrderID(String argOrderID){
        String orderID;
        if(argOrderID.isEmpty()){
            System.out.print("\nOrder ID: ");
            orderID = input.nextLine().toUpperCase();
            if(orderID.length() != 16){ //validasi length input
                System.out.println("Order ID minimal 16 karakter!");
                return "";
            }

            if (!orderID.substring(14).equals(generateCheckSum(orderID.substring(0,14)))){
                System.out.println("Silakan masukkan Order ID yang Valid!");
                return "";
            } 
        }else{
            orderID = argOrderID;
        }

        return orderID;
    }

    //method untuk meminta dan validasi input lokasi
    public static String inputLokasi(String argLokasi){
        String lokasi;
        if(argLokasi.isEmpty()){
            System.out.print("Lokasi Pengiriman: ");
            lokasi = input.nextLine().toUpperCase();
            if (lokasi.length() != 1 || !"PUTSB".contains(lokasi)){//validasi length dan lokasi input
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                return "";
            }
            
        }else{
            lokasi = argLokasi.toUpperCase();
        }
        return lokasi;
    }

    public static String generateCheckSum(String orderID){
        int checksum1 = 0;
        int checksum2 = 0;
        for (int i = 0; i < orderID.length(); i++){
            if (i % 2 == 0 ){ //cehckSum1 dari index genap
                checksum1 += chartoCode(orderID.charAt(i));
            }else{ //checkSum2 dari indec ganjil
                checksum2 += chartoCode(orderID.charAt(i));
            }
        }
        //mengubah checkSum menjadi char berdasarkan code 39 Character set
        return String.format("%s%s", codeToChar(checksum1%36), codeToChar(checksum2%36));
    }

    public static int chartoCode(char n){
        //mengembalikan nilai code 39 character set dari char n
        if("0123456789".contains(Character.toString(n))){
            return (int) n - 48; //perhitungan char to code digit decimal
        }else{
            return (int) n  - 55; //perhitungan char to code karakter alphabet
        }
    }

    public static char codeToChar(int n){
        //mengembalikan nilai code 39 Character set menjadi char
        if(n < 10){
            return (char) (n + 48);
        }else{
            return (char) (n + 55);
        }
    }

    public static void main(String[] args) {
        showMenu();
        int aksi = 0;
        while (aksi != 3 ){
            System.out.println("--------------------------------");
            System.out.print("Pilihan menu: ");
            aksi = input.nextInt(); //meminta aksi user
            input.nextLine();
            switch (aksi){
                case 1:
                    //menjalankan menu generatingOrder
                    System.out.println("Order ID " + generateOrderID("","","") + " diterima!");
                    break;

                case 2:
                    //Mencetak hasil generateBill
                    System.out.print(generateBill("","")); 
                    break;

                case 3:
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    break;

                default://menangkap input pilihan menu yang tidak valid
                    System.out.println("Pilihan menu tidak valid!");
            }
            System.out.println("--------------------------------");
            System.out.println("Pilih menu:");
            System.out.println("1. Generate Order ID");
            System.out.println("2. Generate Bill");
            System.out.println("3. Keluar");
        }
    }

    
}
