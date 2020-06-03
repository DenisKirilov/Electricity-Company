package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "foundation_date")
    private LocalDate foundationDate;

    @Column(name = "initial_capital", nullable = false)
    private double initialCapital;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Customer> customerList;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Finances> financesList;


    public Company() {
        employeeList = new ArrayList<>();
        customerList = new ArrayList<>();
        financesList = new ArrayList<>();
    }

    public Company(String name) {
        this.name = name;
        this.initialCapital = 0;
        this.foundationDate = LocalDate.of(1900, 1, 1);
        employeeList = new ArrayList<>();
        customerList = new ArrayList<>();
        financesList = new ArrayList<>();
    }

    public Company(String name, double initialCapital, LocalDate foundationDate) {
        this.name = name;
        this.initialCapital = initialCapital;
        this.foundationDate = foundationDate;
        employeeList = new ArrayList<>();
        customerList = new ArrayList<>();
        financesList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(double initialCapital) {
        this.initialCapital = initialCapital;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employeeList.remove(employee);
    }


    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomer(Customer customer) {
        this.customerList.add(customer);
    }

    public void removeCustomer(Customer customer) {
        this.customerList.remove(customer);
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Finances> getFinancesList() {
        return financesList;
    }

    public void setFinancesList(List<Finances> financesList) {
        this.financesList = financesList;
    }

    public void addFinances(Finances finances) {
        this.financesList.add(finances);
    }

    public void removeFinances(Finances finances) {
        this.financesList.remove(finances);
    }

    @Override
    public String toString() {
        return "Id = " + id + ", Name='" + name +
                ", foundationDate=" + foundationDate +
                ", initialCapital=" + initialCapital;
    }
}
