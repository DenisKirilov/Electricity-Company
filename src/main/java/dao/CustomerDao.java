package dao;

import configuration.SessionFactoryUtil;
import entities.Company;
import entities.Customer;
import entities.Employee;
import entities.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDao {

    public static void saveCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        }
    }

    public static void saveOrUpdateCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
        }
    }

    public static void saveCustomers(List<Customer> customersList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            customersList.stream().forEach((cus) -> session.save(cus));
            transaction.commit();
        }
    }

    public static List<Customer> getCustomers() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    public static Customer getCustomer(long id) {
        Transaction transaction = null;
        Customer customer;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Customer entity using get() method
            customer = session.get(Customer.class, id);
            transaction.commit();
        }
        return customer;
    }

    public static Customer loadCustomer(long id) {
        Transaction transaction = null;
        Customer customer;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Customer entity using load() method
            customer = session.load(Customer.class, id);
            transaction.commit();
        }
        return customer;
    }

    public static Customer getCustomerById(long id) {
        Transaction transaction = null;
        Customer customer;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Customer entity using byId() method
            customer = session.byId(Customer.class).getReference(id);
            transaction.commit();
        }
        return customer;
    }

    public static void deleteCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //customer.getCompany().removeCustomer(customer);
            session.delete(customer);
            transaction.commit();
        }

    }

    public static Set<Customer> customersInCompany(Company company) {
        List<Customer> customers;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            customers = session.createQuery("FROM Customer WHERE company.id = "
                    + company.getId()).getResultList();
        }
        return customers.stream().collect(Collectors.toSet());
    }

}
