package org.example;

import org.example.dao.CreditCardDao;
import org.example.dao.PaymentsHistoryDao;
import org.example.dao.UserDao;
import org.example.dao.impl.CreditCardDaoImpl;
import org.example.dao.impl.PaymentsHistoryDaoImpl;
import org.example.dao.impl.UserDaoImpl;
import org.example.db.SessionProvider;
import org.example.model.AccountStatus;
import org.example.model.PaymentStatus;
import org.example.model.Role;
import org.example.model.UserStatus;
import org.example.model.entity.CreditCard;
import org.example.model.entity.PaymentsHistory;
import org.example.model.entity.User;
import org.hibernate.Session;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Session session = SessionProvider.getSessionFactory().openSession();

        // TODO: before each run of main method you should recreate database

        // User dao testing
        addNewUsers(session);
        getUsersByPhoneNumbers(session);
        getUserByIds(session);
        getAllUsers(session);
        updateUser(session);
        deleteUser(session);

        // CreditCard dao testing
        addNewCreditCards(session);
        getCreditCardsByCardNumbers(session);
        getUserCreditCards(session);
        updateCreditCard(session);
        deleteCreditCard(session);

        // PaymentsHistory dao testing
        addNewPH(session);
        getPHById(session);
        getAllPaymentsByCard(session);
        deletePH(session);

        session.close();
    }

    private static void addNewUsers(Session session) {
        System.out.println("Adding users..." + System.lineSeparator());

        UserDao userDao = new UserDaoImpl(session);
        session.beginTransaction();

        User u1 = User.builder()
                .phoneNumber("123456789")
                .firstName("first1")
                .lastName("last1")
                .password("pass1")
                .role(Role.USER)
                .userStatus(UserStatus.ACTIVE)
                .build();

        User u2 = User.builder()
                .phoneNumber("987654321")
                .firstName("first2")
                .lastName("last2")
                .password("pass2")
                .role(Role.ADMIN)
                .userStatus(UserStatus.ACTIVE)
                .email("email2@mail.com")
                .age((byte) 18)
                .build();

        userDao.addNewUser(u1);
        userDao.addNewUser(u2);
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "Users added successfully");
    }

    private static void getUsersByPhoneNumbers(Session session) {
        System.out.println("Getting users by phone numbers..." + System.lineSeparator());

        UserDao userDao = new UserDaoImpl(session);
        System.out.println(userDao.getUserByPhoneNumber("123456789"));
        System.out.println(userDao.getUserByPhoneNumber("987654321"));

        System.out.println(System.lineSeparator() + "Users by phone numbers found successfully");
    }

    private static void getUserByIds(Session session) {
        System.out.println("Getting users by ids..." + System.lineSeparator());

        UserDao userDao = new UserDaoImpl(session);
        System.out.println(userDao.getUserById(1));
        System.out.println(userDao.getUserById(2));

        System.out.println(System.lineSeparator() + "Users by ids found successfully");
    }

    private static void getAllUsers(Session session) {
        System.out.println("Getting all users..." + System.lineSeparator());
        UserDao userDao = new UserDaoImpl(session);
        userDao.getAllUsers().forEach(System.out::println);
        System.out.println(System.lineSeparator() + "All users found successfully");
    }

    private static void updateUser(Session session) {
        System.out.println("Updating users..." + System.lineSeparator());

        session.beginTransaction();
        UserDao userDao = new UserDaoImpl(session);
        User u = userDao.getUserById(1);
        u.setUserStatus(UserStatus.BLOCKED);
        userDao.updateUser(u);
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "User updated successfully");
    }

    private static void deleteUser(Session session) {
        System.out.println("Deleting user..." + System.lineSeparator());

        session.beginTransaction();
        UserDao userDao = new UserDaoImpl(session);
        userDao.deleteUser(userDao.getUserById(2));
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "User deleted successfully");
    }

    private static void addNewCreditCards(Session session) {
        System.out.println("Adding new credit cards..." + System.lineSeparator());

        UserDao userDao = new UserDaoImpl(session);
        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        session.beginTransaction();

        User u = userDao.getUserById(1);
        CreditCard cc1 = CreditCard.builder()
                .cardNumber("1111 2222 3333 4444")
                .user(u)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal(0))
                .build();

        CreditCard cc2 = CreditCard.builder()
                .cardNumber("5555 6666 7777 8888")
                .user(u)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal(0))
                .build();

        creditCardDao.addNewCreditCard(cc1);
        creditCardDao.addNewCreditCard(cc2);
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "New credit cards added successfully");
    }

    private static void getCreditCardsByCardNumbers(Session session) {
        System.out.println("Getting credit cards by card numbers..." + System.lineSeparator());

        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        System.out.println(creditCardDao.getCreditCardByNumber("1111 2222 3333 4444"));
        System.out.println(creditCardDao.getCreditCardByNumber("5555 6666 7777 8888"));

        System.out.println(System.lineSeparator() + "Credit cards by credit numbers found successfully");
    }

    private static void getUserCreditCards(Session session) {
        System.out.println("Getting all credit cards..." + System.lineSeparator());

        UserDao userDao = new UserDaoImpl(session);
        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        creditCardDao.getUserCreditCards(userDao.getUserById(1)).forEach(System.out::println);

        System.out.println(System.lineSeparator() + "User credit cards found successfully");
    }

    private static void updateCreditCard(Session session) {
        System.out.println("Updating credit card..." + System.lineSeparator());

        session.beginTransaction();
        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        CreditCard c = creditCardDao.getCreditCardByNumber("1111 2222 3333 4444");
        c.setAccountStatus(AccountStatus.BLOCKED);
        creditCardDao.updateCreditCard(c);
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "Credit card updated successfully");
    }

    private static void deleteCreditCard(Session session) {
        System.out.println("Deleting credit card..." + System.lineSeparator());

        session.beginTransaction();
        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        creditCardDao.deleteCreditCard(creditCardDao.getCreditCardByNumber("5555 6666 7777 8888"));
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "Credit card deleted successfully");
    }

    private static void addNewPH(Session session) {
        System.out.println("Adding new payment history..." + System.lineSeparator());

        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        PaymentsHistoryDao paymentsHistoryDao = new PaymentsHistoryDaoImpl(session);
        session.beginTransaction();

        CreditCard c = creditCardDao.getCreditCardByNumber("1111 2222 3333 4444");
        PaymentsHistory p = PaymentsHistory.builder()
                .sum(new BigDecimal("1000.50"))
                .senderCardNumber(c)
                .receiverCardNumber("1234 5678 9012 3456")
                .paymentStatus(PaymentStatus.PREPARED)
                .build();
        paymentsHistoryDao.addNewPayment(p);

        session.getTransaction().commit();
        System.out.println(System.lineSeparator() + "New payment history added successfully");
    }

    private static void getPHById(Session session) {
        System.out.println("Getting payment history by id..." + System.lineSeparator());

        PaymentsHistoryDao paymentsHistoryDao = new PaymentsHistoryDaoImpl(session);
        System.out.println(paymentsHistoryDao.getPaymentById(1));

        System.out.println(System.lineSeparator() + "Payment history by id found successfully");
    }

    private static void getAllPaymentsByCard(Session session) {
        System.out.println("Getting all payments by card..." + System.lineSeparator());

        CreditCardDao creditCardDao = new CreditCardDaoImpl(session);
        PaymentsHistoryDao paymentsHistoryDao = new PaymentsHistoryDaoImpl(session);
        paymentsHistoryDao.getSenderPayments(creditCardDao.getCreditCardByNumber("1111 2222 3333 4444"))
                .forEach(System.out::println);

        System.out.println(System.lineSeparator() + "Payments by card found successfully");
    }

    private static void deletePH(Session session) {
        System.out.println("Deleting payments history..." + System.lineSeparator());

        session.beginTransaction();
        PaymentsHistoryDao paymentsHistoryDao = new PaymentsHistoryDaoImpl(session);
        paymentsHistoryDao.deletePayment(paymentsHistoryDao.getPaymentById(1));
        session.getTransaction().commit();

        System.out.println(System.lineSeparator() + "Payments history deleted successfully");
    }
}