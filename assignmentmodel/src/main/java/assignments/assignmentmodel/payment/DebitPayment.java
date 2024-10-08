package assignments.assignmentmodel.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    private final double MINIMUM_TOTAL_PRICE = 50000;

    @Override
    public long processPayment(long amount) {
        if(amount < MINIMUM_TOTAL_PRICE){
            System.out.println("Jumlah Pesanan < 50000 mohon menggunakan metode pembayaran yang lain.");
            return 0;
        }else{
            return amount;
        }
    }
}
