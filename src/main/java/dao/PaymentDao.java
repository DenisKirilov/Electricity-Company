package dao;

import configuration.SessionFactoryUtil;
import entities.Company;
import entities.Customer;
import entities.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentDao {

    public static void savePayment(Payment payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        }
    }

    public static void saveOrUpdatePayment(Payment payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(payment);
            transaction.commit();
        }
    }

    public static void savePayments(List<Payment> paymentsList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            paymentsList.stream().forEach((pay) -> session.save(pay));
            transaction.commit();
        }
    }

    public static List<Payment> getPayments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Payment", Payment.class).list();
        }
    }

    public static Payment getPayment(long id) {
        Transaction transaction = null;
        Payment payment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Payment entity using get() method
            payment = session.get(Payment.class, id);
            transaction.commit();
        }
        return payment;
    }

    public static Payment loadPayment(long id) {
        Transaction transaction = null;
        Payment payment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Payment entity using load() method
            payment = session.load(Payment.class, id);
            transaction.commit();
        }
        return payment;
    }

    public static Payment getPaymentById(long id) {
        Transaction transaction = null;
        Payment payment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Payment entity using byId() method
            payment = session.byId(Payment.class).getReference(id);
            transaction.commit();
        }
        return payment;
    }

    public static void deletePayment(Payment payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(payment);
            transaction.commit();
        }

    }

    public static Set<Payment> customerPayments(Customer customer) {
        List<Payment> payments;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            payments = session.createQuery("FROM Payment WHERE customer.id = "
                    + customer.getId()).getResultList();
        }
        return payments.stream().collect(Collectors.toSet());
    }


    //5.
    public static double biggestPaymentByCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT MAX(payment) FROM Payment WHERE customer_id = " + customer.getId());
            return (double) sumQuery.getSingleResult();
        }
    }


    //6.
    public static List<Payment> paymentLowerThan(double payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Payment> cr = cb.createQuery(Payment.class);
            Root<Payment> root = cr.from(Payment.class);
            cr.select(root).where(cb.lessThan(root.get("payment"), payment));

            Query<Payment> query = session.createQuery(cr);
            List<Payment> customers = query.getResultList();
            return customers;
        }
    }
    //7
    public static double clientPaymentSum(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT SUM(payment) FROM Payment WHERE customer_id = " + customer.getId());
            return (double) sumQuery.getSingleResult();
        }
    }

}
