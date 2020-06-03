package dao;

import configuration.SessionFactoryUtil;
import entities.Company;
import entities.Employee;
import entities.Payment;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.query.Query;

import javax.persistence.EntityGraph;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CompanyDao {

    public static void saveCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    public static void saveOrUpdateCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }

    public static void saveCompanies(List<Company> companyList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companyList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Company> getCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company", Company.class).getResultList();
        }
    }

    public static Company getCompany(long id) {
        Transaction transaction = null;
        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Company entity using get() method
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }

    public static Company loadCompany(long id) {
        Transaction transaction = null;
        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Company entity using load() method
            company = session.load(Company.class, id);
            transaction.commit();
        }
        return company;
    }

    public static Company getCompanyById(long id) {
        Transaction transaction = null;
        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Company entity using byId() method
            company = session.byId(Company.class).getReference(id);
            transaction.commit();
        }
        return company;
    }

    public static void deleteCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }


    public static List<Company> companiesWithInitialCapitalGreaterThan(double initialCapital) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.greaterThan(root.get("initialCapital"), initialCapital));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithInitialCapitalLessThan(double initialCapital) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);

            cr.select(root).where(cb.lessThan(root.get("initialCapital"), initialCapital));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithInitialCapitalBetween(double top, double bottom) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.between(root.get("initialCapital"), top, bottom));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static BigDecimal maxInitialCapital() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cr = cb.createQuery(BigDecimal.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(cb.max(root.get("initialCapital")));

            Query<BigDecimal> query = session.createQuery(cr);
            BigDecimal maxInitialCapital = query.getSingleResult();
            return maxInitialCapital;
        }
    }

    public static BigDecimal sumInitialCapital() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cr = cb.createQuery(BigDecimal.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(cb.sum(root.get("initialCapital")));

            Query<BigDecimal> query = session.createQuery(cr);
            BigDecimal maxInitialCapital = query.getSingleResult();
            return maxInitialCapital;
        }
    }

    public static List<Company> companiesWithNameEqualTo(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.equal(root.get("name"), name));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithNameNotEqualTo(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.notEqual(root.get("name"), name));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithNameLike(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.like(root.get("name"), "%" + name + "%"));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithNameNotLike(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.notLike(root.get("name"), "%" + name + "%"));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithNameStartingWithAndInitialCapitalGreaterThan(String name, double initialCapital) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);

            Predicate greaterThanInitialCapital = cb.greaterThan(root.get("initialCapital"), initialCapital);
            Predicate nameStartingWith = cb.like(root.get("name"), name + "%");

            cr.select(root).where(cb.and(greaterThanInitialCapital, nameStartingWith));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesByNameAscAndInitialCapitalDesc() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.orderBy(cb.asc(root.get("initialCapital")), cb.desc(root.get("name")));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }

    public static List<Company> companiesWithFoundationDateGreaterThan(LocalDate localDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.greaterThan(root.get("foundationDate"), localDate));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }


    // Select all employees in a given company Lazy Initialization Demo
    public static List<Employee> employeesInCompany(Company company) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Company company1 = session.find(Company.class, company.getId());
            employees = company1.getEmployeeList();
            Hibernate.initialize(employees);
        }
        return employees;
    }


    // Select all companies founded after a given date Native SQL
    public static List<Company> companiesFoundedAfter(LocalDate localDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createNativeQuery("SELECT * FROM company as c WHERE c.foundation_date > ?", Company.class);
            query.setParameter(1, localDate);
            return query.getResultList();
        }
    }

    // Select all companies NamedQuery
    public static List<Company> companiesByNamedQuery() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createNamedQuery("selectCompanyEntities", Company.class);
            return query.getResultList();
        }
    }

    // Select all companies StoredProcedures
    public static List<Company> companiesByStoredProcedures() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createSQLQuery("CALL GetAllCompanies()").addEntity(Company.class);
            return query.getResultList();
        }
    }

    // Select all employees who work in a given company Native SQL
    public static List<Employee> employeesInCompanyNSQL(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createNativeQuery("SELECT * FROM employees JOIN company " +
                            "ON company.id = employees.company_id WHERE company.id = :company_id", Employee.class);
            query.setParameter("company_id", company.getId());
            return query.getResultList();
        }
    }

    // Select all employees in any company Native SQL
    public static List<Employee> employeesInCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createNativeQuery("SELECT * FROM employees JOIN company " +
                            "ON company.id = employees.company_id", Employee.class);
            return query.getResultList();
        }
    }

    // Select all companies with employees Native SQL
    public static List<Company> companiesWithEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createNativeQuery("SELECT * FROM company JOIN employees " +
                            "ON company.id = employees.company_id", Company.class);
            return query.getResultList();
        }
    }


    // Select all companies with employees group by company JOIN
    public static List<Company> companiesWithEmployeesGroupByCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createNativeQuery("SELECT * FROM company JOIN employees " +
                            "ON company.id = employees.company_id GROUP BY company.id", Company.class);
            return query.getResultList();
        }
    }

    // Select all companies with employees LEFT JOIN
    public static List<Company> companiesWithEmployeesLeftJoin() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createNativeQuery("SELECT * FROM company LEFT JOIN employees " +
                            "ON company.id = employees.company_id", Company.class);
            return query.getResultList();
        }
    }

    // Select all companies with employees JOIN FETCH
    public static List<Company> companiesWithEmployeesJoinFetch() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session
                    .createQuery("FROM Company c JOIN FETCH c.employeeList", Company.class);
            return query.getResultList();
        }
    }

    // Select all employees in a given company Lazy Initialization Dynamic Graph
    public static List<Employee> employeesInCompanyDynamicGraph(Company company) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            EntityGraph entityGraph = session.createEntityGraph(Company.class);
            entityGraph.addAttributeNodes("employeeList");
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.fetchgraph", entityGraph);
            company = session.find(Company.class, company.getId(), properties);
        }
        return company.getEmployeeList();
    }

}
