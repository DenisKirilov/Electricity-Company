package entities;

import Project.CustomerType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "customer")
    private List<Payment> paymentList;

    public Customer(String firstName, String lastName, CustomerType customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerType = customerType;
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return firstName+lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public double getCustomerTypeDouble() {
        if(customerType == customerType.BUSINESS) return 1.6;
        else return 1.4;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }


    @Override
    public String toString() {
        return "Customer{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customerType='" + customerType + '\'';
    }
}