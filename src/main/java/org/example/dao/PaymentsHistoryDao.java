package org.example.dao;

import org.example.model.entity.CreditCard;
import org.example.model.entity.PaymentsHistory;

import java.util.List;

public interface PaymentsHistoryDao {
    void addNewPayment(PaymentsHistory paymentsHistory);

    PaymentsHistory getPaymentById(int id);

    List<PaymentsHistory> getSenderPayments(CreditCard creditCard);

    List<PaymentsHistory> getReceiverPayments(String receiverCardNumber);

    void updatePayment(PaymentsHistory paymentsHistory);

    void deletePayment(PaymentsHistory paymentsHistory);
}
