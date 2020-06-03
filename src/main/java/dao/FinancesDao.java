package dao;

import configuration.SessionFactoryUtil;
import entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FinancesDao {

    public static void saveFinance(Finances finances) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(finances);
            transaction.commit();
        }
    }

    public static void saveOrUpdateFinance(Finances finances) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(finances);
            transaction.commit();
        }
    }

    public static void saveProperties(List<Finances> propertiesList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            propertiesList.stream().forEach((prop) -> session.save(prop));
            transaction.commit();
        }
    }

    public static List<Finances> getProperties() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM properties", Finances.class).list();
        }
    }

    public static Finances getFinance(long id) {
        Transaction transaction = null;
        Finances finances;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Finance entity using get() method
            finances = session.get(Finances.class, id);
            transaction.commit();
        }
        return finances;
    }

    public static Finances getFinance1(long company_id) {
        Transaction transaction = null;
        Finances finances;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Finance entity using get() method
            finances = session.get(Finances.class, company_id);
            transaction.commit();
        }
        return finances;
    }


    public static List<Finances> getFinances() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Finances", Finances.class).getResultList();
        }
    }

    public static Finances loadFinance(long id) {
        Transaction transaction = null;
        Finances finances;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Finance entity using load() method
            finances = session.load(Finances.class, id);
            transaction.commit();
        }
        return finances;
    }

    public static Finances getFinanceById(long id) {
        Transaction transaction = null;
        Finances finances;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Finance entity using byId() method
            finances = session.byId(Finances.class).getReference(id);
            transaction.commit();
        }
        return finances;
    }

    public static void deleteFinance(Finances finances) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(finances);
            transaction.commit();
        }

    }

    //1
    public static double sumIncome(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT SUM(income) FROM Finances WHERE company_id = " + company.getId());
            return (double) sumQuery.getSingleResult();
        }
    }
    //2
    public static double sumExpenses(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT SUM(expenses) FROM Finances WHERE company_id = " + company.getId());
            return (double) sumQuery.getSingleResult();
        }
    }
    //3
    public static double sumProfit(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT SUM(profit) FROM Finances WHERE company_id = " + company.getId());
            return (double) sumQuery.getSingleResult();
        }
    }
    //4
    public static double sumProfitAfterTaxes(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query sumQuery = session.createQuery("SELECT SUM(balance) FROM Finances WHERE company_id = " + company.getId());
            return (double) sumQuery.getSingleResult();
        }
    }

    public static List<Finances> paymentCompanyId(long companyid) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Finances> cr = cb.createQuery(Finances.class);
            Root<Finances> root = cr.from(Finances.class);
            cr.select(root).where(cb.equal(root.get("company_id"), companyid));

            Query<Finances> query = session.createQuery(cr);
            List<Finances> company = query.getResultList();
            return company;
        }
    }

    public static List<Finances> tryy() {
            try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
                Query query = session.createQuery("FROM Finances");
                List<Finances> employees = query.getResultList();
                return employees;
            }
        }

}
