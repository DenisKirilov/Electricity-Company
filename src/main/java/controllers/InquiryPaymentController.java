package controllers;

import dao.CompanyDao;
import dao.CustomerDao;
import dao.FinancesDao;
import dao.PaymentDao;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class InquiryPaymentController {

    private ObservableList<Payment> listItems;

    @FXML
    private TableView PaymentListTableView;

    @FXML
    private TableColumn PaymentListTableViewIdColumn;

    @FXML
    private TableColumn PaymentListTableViewPaymentColumn;

    @FXML
    private TableColumn PaymentListTableViewQuantityColumn;

    @FXML
    private TableColumn PaymentListTableViewDateColumn;

    @FXML
    private TableColumn PaymentListTableViewCommentColumn;

    @FXML
    private TableColumn PaymentListTableViewCustomerColumn;

    @FXML
    private TextField paymentLowerThanTextField;

    @FXML
    private Label SummaryPaymentLabel;

    @FXML
    private Label HighestPaymentLabel;

    @FXML
    private ComboBox<Customer> CustomerComboBox;


    @FXML
    private void initialize() {
        CustomerComboBox.setPromptText("Select customer");
        setValuesToCustomerComboBox();
    }

    public void PaymentLowerAction() {
        listItems = FXCollections.observableArrayList(PaymentDao.paymentLowerThan(Double.valueOf(paymentLowerThanTextField.getText())));
        PaymentListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Payment, Long>("id"));
        PaymentListTableViewPaymentColumn.setCellValueFactory(new PropertyValueFactory<Payment, Double>("payment"));
        PaymentListTableViewQuantityColumn.setCellValueFactory(new PropertyValueFactory<Payment, Double>("quantity"));
        PaymentListTableViewDateColumn.setCellValueFactory(new PropertyValueFactory<Payment, LocalDate>("date"));
        PaymentListTableViewCommentColumn.setCellValueFactory(new PropertyValueFactory<Payment,String>("paymentComment"));
        PaymentListTableViewCustomerColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("Customer"));
        PaymentListTableView.setItems(listItems);
    }

    @FXML
    private void loadPaymentsAction(ActionEvent event) {
        SummaryPaymentLabel.setText(String.valueOf(PaymentDao.biggestPaymentByCustomer(CustomerComboBox.getValue())));
        HighestPaymentLabel.setText(String.valueOf(PaymentDao.clientPaymentSum(CustomerComboBox.getValue())));
    }

    @FXML
    public void clearAction(){
        SummaryPaymentLabel.setText("");
        HighestPaymentLabel.setText("");
    }


    private void setValuesToCustomerComboBox() {
        CustomerComboBox.setItems(FXCollections.observableArrayList(CustomerDao.getCustomers()));
    }
}