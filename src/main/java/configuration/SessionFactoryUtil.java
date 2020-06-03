package configuration;

import entities.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Payment.class);
            configuration.addAnnotatedClass(Finances.class);

            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;


    }
}