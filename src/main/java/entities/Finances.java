package entities;

import dao.FinancesDao;

import javax.persistence.*;

@Entity
@Table(name="finances")
public class Finances {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="income")
    private double income;
    @Column(name = "expenses")
    private double expenses;
    @Column(name="balance")
    private double balance;
    @Column(name = "profit")
    private double profit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Finances() {
        this.income = 0.0;
        this.expenses = 144.0;
        this.balance= 0.0;
        this.profit = 0.0;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void addIncome(double income) {
        this.income += income;
    }

    public void paySalary(Employee employee){
        addExpenses(employee.getSalary());
    }

    public void getPayment(Payment payment){
        addIncome(payment.getPayment());
    }

    public void addExpenses(double expenses) {
        this.expenses += expenses;
    }

    public void CalculateProfit(){
        this.profit = income-expenses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double ProfitAfterTaxes(double percentTax){
        if (this.profit>0)this.balance = this.profit-(this.profit*(percentTax/100));
        else this.balance=this.profit+(this.profit*(percentTax/100));
        return this.balance;
    }

    @Override
    public String toString() {
        return "Finances{" +
                "id=" + id +
                ", income=" + income +
                ", expenses=" + expenses +
                ", balance=" + balance +
                ", profit=" + profit +
                ", company=" + company +
                '}';
    }

}
