package controllers;

import dao.*;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Set;


public class BalanceController {
    private ObservableList<Finances> listItems;

    @FXML
    private TableView financesListTableView;

    @FXML
    private TableColumn financesListTableViewIdColumn;

    @FXML
    private TableColumn financesListTableViewIncomeColumn;

    @FXML
    private TableColumn financesListTableViewExpensesColumn;

    @FXML
    private TableColumn financesListTableViewBalanceColumn;

    @FXML
    private TableColumn financesListTableViewProfitColumn;

    @FXML
    private TableColumn ListTableViewCompanyColumn;

    @FXML
    private ComboBox<Company> CompanyComboBox;

    @FXML
    private Label FinancesIdLabel;

    @FXML
    private TextField financesListIncomeTextField;

    @FXML
    private TextField financesListExpensesTextField;

    @FXML
    private ComboBox<Integer> financesListBalanceComboBox;

    @FXML
    private Label financesListProfitLabel;

    @FXML
    private void initialize() {
        loadFinances();
        financesListTableView.setEditable(true);
        setValuesToCompanyComboBox();
    }

    public void loadFinances() {
        listItems = FXCollections.observableArrayList(FinancesDao.getFinances());
        financesListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Finances, Long>("id"));
        financesListTableViewIncomeColumn.setCellValueFactory(new PropertyValueFactory<Finances, Double>("Income"));
        financesListTableViewExpensesColumn.setCellValueFactory(new PropertyValueFactory<Finances, Double>("Expenses"));
        financesListTableViewProfitColumn.setCellValueFactory(new PropertyValueFactory<Finances, Double>("Profit"));
        financesListTableViewBalanceColumn.setCellValueFactory(new PropertyValueFactory<Finances, Double>("Balance"));
        ListTableViewCompanyColumn.setCellValueFactory(new PropertyValueFactory<Finances, String>("Company"));
        financesListTableView.setItems(listItems);
    }

    @FXML
    public void saveFinancesAction(ActionEvent actionEvent) {
        Finances finances = new Finances();
        if (FinancesIdLabel.getText().length() > 0) {
           finances.setId(Long.parseLong(FinancesIdLabel.getText()));
        }
        finances.setIncome(Double.valueOf(financesListIncomeTextField.getText()));
        finances.setExpenses(Double.valueOf(financesListExpensesTextField.getText()));
        finances.CalculateProfit();
        finances.ProfitAfterTaxes(Double.valueOf(financesListBalanceComboBox.getValue()));
        finances.setCompany(CompanyComboBox.getValue());


        FinancesDao.saveOrUpdateFinance(finances);

        loadFinances();

        clearFinancesForm();
    }

    @FXML
    public void PaySalariesAction(ActionEvent actionEvent) {
        Finances finances = new Finances();
        if (FinancesIdLabel.getText().length() > 0) {
            finances.setCompany(CompanyComboBox.getValue());
            Company company = CompanyDao.getCompany(finances.getCompany().getId());
            Set<Employee> employeesInCompany = EmployeeDao.employeesInCompany(company);
            employeesInCompany.stream().forEach(e -> finances.paySalary(e));
            financesListExpensesTextField.setText(String.valueOf(finances.getExpenses()));
        }
    }

    @FXML
    public void editFinancesAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Finances> selectionModel = financesListTableView.getSelectionModel();
        Finances Finances = selectionModel.getSelectedItem();
        FinancesIdLabel.setText(Long.toString(Finances.getId()));
        financesListIncomeTextField.setText(String.valueOf(Finances.getIncome()));
        financesListExpensesTextField.setText(String.valueOf(Finances.getExpenses()));
        financesListProfitLabel.setText(String.valueOf(Finances.getProfit()));
        CompanyComboBox.setValue(Finances.getCompany());
    }



    @FXML
    public void deleteFinancesAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Finances> selectionModel = financesListTableView.getSelectionModel();
        Finances Finances = selectionModel.getSelectedItem();
        FinancesDao.deleteFinance(Finances);
        financesListTableView.getItems().remove(Finances);
    }

    @FXML
    public void clearFinancesForm() {
        FinancesIdLabel.setText("");
        financesListIncomeTextField.clear();
        financesListExpensesTextField.setText("");
        financesListBalanceComboBox.setValue(null);
        financesListProfitLabel.setText("");
        CompanyComboBox.setValue(null);
    }

    @FXML
    public void CollectPaymentsAction(ActionEvent actionEvent) {
        Finances finances = new Finances();
        if (FinancesIdLabel.getText().length() > 0) {
            finances.setCompany(CompanyComboBox.getValue());
            Company company = CompanyDao.getCompany(finances.getCompany().getId());
            Set<Customer> customersInCompany = CustomerDao.customersInCompany(company);
            for(Customer c:customersInCompany){
                for(Payment p:PaymentDao.customerPayments(c)) {
                    finances.getPayment(p);
                }
            }
            financesListIncomeTextField.setText(String.valueOf(finances.getIncome()));
        }
    }
    @FXML
    public void CalculateProfitAction(ActionEvent actionEvent) {
        Finances finances = new Finances();
        if (FinancesIdLabel.getText().length() > 0) {
            financesListProfitLabel.setText(String.valueOf((Double.valueOf(financesListIncomeTextField.getText()))-(Double.valueOf(financesListExpensesTextField.getText()))));
        }
    }
    private void setValuesToCompanyComboBox() {
        CompanyComboBox.setItems(FXCollections.observableArrayList(CompanyDao.getCompanies()));
        financesListBalanceComboBox.getItems().addAll(10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
    }

}
