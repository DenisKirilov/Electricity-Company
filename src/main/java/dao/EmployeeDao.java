package dao;

import configuration.SessionFactoryUtil;
import entities.Company;
import entities.Customer;
import entities.Employee;
import entities.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDao {

    public static void saveEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
    }

    public static void saveOrUpdateEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    public static void saveEmployees(List<Employee> employeeList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employeeList.stream().forEach(employee -> employee.getCompany().addEmployee(employee));
            employeeList.stream().forEach(employee -> session.save(employee));
            transaction.commit();
        }
    }

    public static List<Employee> getEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM entities.Employee", Employee.class).list();
        }
    }

    public static Employee getEmployee(long id) {
        Transaction transaction = null;
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Employee entity using get() method
            employee = session.get(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }

    public static Employee loadEmployee(long id) {
        Transaction transaction = null;
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Employee entity using load() method
            employee = session.load(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }

    public static Employee getEmployeeById(long id) {
        Transaction transaction = null;
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get entity.entity.Employee entity using byId() method
            employee = session.byId(Employee.class).getReference(id);
            transaction.commit();
        }
        return employee;
    }

    public static void deleteEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //employee.getCompany().removeEmployee(employee);
            session.delete(employee);
            transaction.commit();
        }
    }


    // Select all employees who work in a given company
    public static Set<Employee> employeesInCompany(Company company) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            employees = session.createQuery("FROM Employee WHERE company.id = "
                    + company.getId()).getResultList();
        }
        return employees.stream().collect(Collectors.toSet());
    }

    // Select all employees who work in a company with a given name
    public static Set<Employee> employeesInCompanyNameEqualTo(String companyName) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Employees WHERE company.name = :company_name");
            query.setParameter("company_name", companyName);
            employees = query.getResultList();
        }
        return employees.stream().collect(Collectors.toSet());
    }

    // Select all employees who are older than given number of years
    public static Set<Employee> employeesOlderThan(int years) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Employees WHERE birthDate < :birth_date");
            int year = LocalDate.now().getYear() - years;
            query.setParameter("birth_date", LocalDate.of(year, 01, 01));
            employees = query.getResultList();
        }
        return employees.stream().collect(Collectors.toSet());
    }

    // Select all employees order by birthDate
    public static List<Employee> employeesOrderByBirthDate() {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Employees ORDER BY birthDate ASC");
            employees = query.getResultList();
        }
        return employees;
    }

    // Count the number of employees who work in a company employees group by company
    public static List<Long> countEmployeesGroupByCompany() {
        List<Long> numberOfEmployeesByCompany;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Employees GROUP BY company");
            numberOfEmployeesByCompany = query.getResultList();
        }
        return numberOfEmployeesByCompany;
    }

    // Select the names of all employees group by name
    public static List<String> employeeNames() {
        List<String> numberOfEmployeesByCompany;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT name FROM Employees GROUP BY name");
            numberOfEmployeesByCompany = query.getResultList();
        }
        return numberOfEmployeesByCompany;
    }

    // Select the birthDates of all employees (using alias in the query)
    public static List<LocalDate> employeesBirthDates() {
        List<LocalDate> employeesBirthDates;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT E.birthDate as b FROM Employee as E");
            employeesBirthDates = query.getResultList();
        }
        return employeesBirthDates;
    }

    // Select only employees with different names using DISTINCT
    public static List<String> employeesWithDistinctNames() {
        List<String> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT name FROM Employees");
            employees = query.getResultList();
        }
        return employees;
    }

    // Select Employees Order By name limit 3
    public static List<Employee> employeesLimitBy(int limit) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Employees ORDER BY name");
            employees = query.setMaxResults(limit).getResultList();
        }
        return employees;
    }

    // Select Employees Order by name starting with the fourth one
    public static List<Employee> employeesOrderByNameAfter(int number) {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Employees ORDER BY name");
            employees = query.setFirstResult(number).getResultList();
        }
        return employees;
    }


    public static Map<Long, String> employeesIdsAndNames() {
        List<Tuple> employeesIdsAndNamesTuples;
        Map<Long, String> employeesIdsAndNames = new HashMap<>();
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT id as id, name as n FROM Employees", Tuple.class);
            employeesIdsAndNamesTuples = query.getResultList();
            employeesIdsAndNamesTuples.stream()
                    .forEach(tuple -> employeesIdsAndNames.put((Long) tuple.get("id"), (String) tuple.get("n")));
//             employeesIdsAndNamesTuples.stream().forEach(tuple -> employeesIdsAndNames.put( (Long) tuple.get(0), (String) tuple.get(1)));
        }
        return employeesIdsAndNames;
    }

}
