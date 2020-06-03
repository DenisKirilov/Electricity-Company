package entities;

import Project.CustomerType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="date")
    private LocalDate date;
    @Column(name="quantity")
    private double quantity;

    @Column(name="payment")
    private double payment;
    @Column(name="comment")
    private String paymentComment;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Payment() {

    }

    public Payment(Customer customer,LocalDate date, String paymentComment) {
        this.customer = customer;
        this.date = date;
        this.paymentComment = paymentComment;
    }

    public Payment(Customer customer,LocalDate date) {
        this.customer = customer;
        this.date = date;
        this.paymentComment = paymentComment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getPaymentComment() {
        return paymentComment;
    }

    public void setPaymentComment(String paymentComment) {
        this.paymentComment = paymentComment;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void payBill(Finances bank){
        this.payment = quantity * getCustomer().getCustomerTypeDouble();
        bank.addIncome(payment);
    }

    @Override
    public String toString() {
        return  "{payment=" +
                payment +
                " " +
                customer +
                '}';
    }
}
