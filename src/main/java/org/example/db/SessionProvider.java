package org.example.db;

import org.example.model.AccountStatus;
import org.example.model.PaymentStatus;
import org.example.model.Role;
import org.example.model.UserStatus;
import org.example.model.entity.CreditCard;
import org.example.model.entity.PaymentsHistory;
import org.example.model.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionProvider {

    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) return sessionFactory;

        synchronized (SessionFactory.class) {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                configuration.addAnnotatedClass(AccountStatus.class);
                configuration.addAnnotatedClass(CreditCard.class);
                configuration.addAnnotatedClass(PaymentsHistory.class);
                configuration.addAnnotatedClass(PaymentStatus.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(UserStatus.class);

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            return sessionFactory;
        }
    }
}
