package assignments.assignmentmodel.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    private final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public long processPayment(long amount) {

        return amount + countTransactionFee(amount);
    }

    public long countTransactionFee(long amount){
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }

}
