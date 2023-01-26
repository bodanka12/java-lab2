package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.PaymentsHistoryDao;
import org.example.model.entity.CreditCard;
import org.example.model.entity.PaymentsHistory;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class PaymentsHistoryDaoImpl implements PaymentsHistoryDao {

    private final Session session;

    private static final String GET_PH_BY_ID = "SELECT ph FROM PaymentsHistory ph WHERE ph.id = :id";
    private static final String GET_SENDER_PH = "SELECT ph FROM PaymentsHistory ph WHERE ph.senderCardNumber = :senderCard";
    private static final String GET_RECEIVER_PH = "SELECT ph FROM PaymentsHistory ph WHERE ph.receiverCardNumber = :receiverCard";
    private static final String DELETE_PH = "DELETE PaymentsHistory ph WHERE ph.id = :id";

    @Override
    public void addNewPayment(PaymentsHistory paymentsHistory) {
        session.persist(paymentsHistory);
    }

    @Override
    public PaymentsHistory getPaymentById(int id) {
        return session.createQuery(GET_PH_BY_ID, PaymentsHistory.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public List<PaymentsHistory> getSenderPayments(CreditCard creditCard) {
        return session.createQuery(GET_SENDER_PH, PaymentsHistory.class)
                .setParameter("senderCard", creditCard)
                .list();
    }

    @Override
    public List<PaymentsHistory> getReceiverPayments(String receiverCardNumber) {
        return session.createQuery(GET_RECEIVER_PH, PaymentsHistory.class)
                .setParameter("receiverCard", receiverCardNumber)
                .list();
    }

    @Override
    public void updatePayment(PaymentsHistory paymentsHistory) {
        session.merge(paymentsHistory);
    }

    @Override
    public void deletePayment(PaymentsHistory paymentsHistory) {
        session.createQuery(DELETE_PH)
                .setParameter("id", paymentsHistory.getId())
                .executeUpdate();
    }
}
