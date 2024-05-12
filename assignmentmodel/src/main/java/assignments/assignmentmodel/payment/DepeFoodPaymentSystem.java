package assignments.assignmentmodel.payment;

public interface DepeFoodPaymentSystem {

    public long processPayment(long saldo, long amount) throws Exception;
}