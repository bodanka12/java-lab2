package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.CreditCardDao;
import org.example.model.entity.CreditCard;
import org.example.model.entity.User;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class CreditCardDaoImpl implements CreditCardDao {

    private final Session session;

    private static final String GET_CARD_BY_NUMBER = "SELECT cc FROM CreditCard cc WHERE cc.cardNumber = :cardNumber";
    private static final String GET_USER_CARDS = "SELECT cc FROM CreditCard cc WHERE cc.user.id = :userId";
    private static final String DELETE_CREDIT_CARD = "DELETE CreditCard cc WHERE cc.id = :id";

    @Override
    public void addNewCreditCard(CreditCard creditCard) {
        session.persist(creditCard);
    }

    @Override
    public CreditCard getCreditCardByNumber(String number) {
        return session.createQuery(GET_CARD_BY_NUMBER, CreditCard.class)
                .setParameter("cardNumber", number)
                .uniqueResult();
    }

    @Override
    public List<CreditCard> getUserCreditCards(User user) {
        return session.createQuery(GET_USER_CARDS, CreditCard.class)
                .setParameter("userId", user.getId())
                .list();
    }

    @Override
    public void updateCreditCard(CreditCard creditCard) {
        session.merge(creditCard);
    }

    @Override
    public void deleteCreditCard(CreditCard creditCard) {
        session.createQuery(DELETE_CREDIT_CARD)
                .setParameter("id", creditCard.getId())
                .executeUpdate();
    }
}
