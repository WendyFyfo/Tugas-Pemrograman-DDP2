package assignments.assignmentmodel.systemCLI;

import assignments.assignment1.OrderGenerator;
import assignments.assignmentmodel.restomodel.Menu;
import assignments.assignmentmodel.restomodel.Restaurant;

import java.util.ArrayList;

//TODO: Extends Abstract yang diberikan
public class AdminSystemCLI extends UserSystemCLI{
    protected static ArrayList<Restaurant> restoList = new ArrayList<>();

    //no-arg constructor untuk menjalankan menu Admin
    public AdminSystemCLI(){
        run();
    }


    @Override
    public boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("----------Tambah Restoran---------");
        boolean tambahRestoran = true;
        while(tambahRestoran){
            //input nama restoran
            String namaRestoran = OrderGenerator.inputRestaurantName("");
            if(namaRestoran.isEmpty()){continue;} //jika nama tidak valid, kembali meminta input

            //cek apakah nama restoran sudah pernah terdaftar
            boolean namaTerdaftar = false;
            if(restoList != null){
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
            String menu;
            int splitPosition;
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
                    System.out.println("Harga menu harus bilangan bulat!\n");
                    tempMenuList.clear(); //clear arraylist bila input harga invalid
                    break;
                }
            }
            if (tempMenuList.isEmpty()){
                continue;
            }

            //Sort menu pada restoran berdasarkan harga dan nama
            ArrayList<String[]> menuList = new ArrayList<>();
            menuList.add(tempMenuList.getFirst());
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
                        }else if (compareNamaMenu == 0){
                            menuList.add(j-k, tempMenuList.get(j));
                            break;
                        }else{
                            menuList.add(j,tempMenuList.get(j));
                            break;
                        }
                    }else{
                        if(j-k == 0){ //check apakah menu1 dicompare dengan menuList[0]
                            menuList.addFirst( tempMenuList.get(j));
                            break;
                        }
                    }
                }
            }

            //memasukkann Resto dan daftar menunya ke sistem
            restoList.add(new Restaurant(namaRestoran));
            for(String[] item: menuList) {
                restoList.getLast().addMenu(new Menu(item[0],Double.parseDouble(item[1])));
            }
            System.out.println("Restaurant " + namaRestoran + " berhasil terdaftar.");
            tambahRestoran = false;
        }
    }

    protected void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
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
}
