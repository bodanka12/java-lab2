package org.example.dao;

import org.example.model.entity.CreditCard;
import org.example.model.entity.User;

import java.util.List;

public interface CreditCardDao {
    void addNewCreditCard(CreditCard creditCard);

    CreditCard getCreditCardByNumber(String number);

    List<CreditCard> getUserCreditCards(User user);

    void updateCreditCard(CreditCard creditCard);

    void deleteCreditCard(CreditCard creditCard);
}
