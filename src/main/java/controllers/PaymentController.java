package controllers;

import dao.CustomerDao;
import dao.PaymentDao;
import entities.Customer;
import entities.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;


public class PaymentController {
    private ObservableList<Payment> listItems;

    @FXML
    private TableView PaymentListTableView;

    @FXML
    private TableColumn PaymentListTableViewIdColumn;

    @FXML
    private TableColumn PaymentListTableViewDateColumn;

    @FXML
    private TableColumn PaymentListTableViewPaymentColumn;

    @FXML
    private TableColumn PaymentListTableViewCommentColumn;

    @FXML
    private TableColumn PaymentListTableViewQuantityColumn;

    @FXML
    private TableColumn PaymentListTableViewCustomerColumn;

    @FXML
    private Label PaymentIdLabel;

    @FXML
    private DatePicker PaymentDatePicker;

    @FXML
    private TextField PaymentPaymentTextField;

    @FXML
    private TextField PaymentQuantityTextField;

    @FXML
    private TextField PaymentCommentTextField;

    @FXML
    private ComboBox<Customer> CustomerComboBox;

    @FXML
    private void initialize() {
        loadPayments();
        PaymentListTableView.setEditable(true);
        setValuesToCustomerComboBox();
    }

    public void loadPayments() {
        listItems = FXCollections.observableArrayList(PaymentDao.getPayments());
        PaymentListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Payment, Long>("id"));
        PaymentListTableViewDateColumn.setCellValueFactory(new PropertyValueFactory<Payment, LocalDate>("date"));
        PaymentListTableViewQuantityColumn.setCellValueFactory(new PropertyValueFactory<Payment, Double>("quantity"));
        PaymentListTableViewPaymentColumn.setCellValueFactory(new PropertyValueFactory<Payment, Double>("payment"));
        PaymentListTableViewCommentColumn.setCellValueFactory(new PropertyValueFactory<Payment,String>("paymentComment"));
        PaymentListTableViewCustomerColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("Customer"));
        PaymentListTableView.setItems(listItems);
    }

    @FXML
    public void savePaymentAction(ActionEvent actionEvent) {
        Payment Payment = new Payment();
        if (PaymentIdLabel.getText().length() > 0) {
            Payment.setId(Long.parseLong(PaymentIdLabel.getText()));
        }

            Payment.setDate(PaymentDatePicker.getValue());
            Payment.setPayment(Double.valueOf(PaymentPaymentTextField.getText()));
            Payment.setPaymentComment(PaymentCommentTextField.getText());
            Payment.setQuantity(Double.valueOf(PaymentQuantityTextField.getText()));
            Payment.setCustomer(CustomerComboBox.getValue());


            PaymentDao.saveOrUpdatePayment(Payment);

            loadPayments();

            clearPaymentForm();

    }

    @FXML
    public void editPaymentAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Payment> selectionModel = PaymentListTableView.getSelectionModel();
        Payment Payment = selectionModel.getSelectedItem();
        PaymentIdLabel.setText(Long.toString(Payment.getId()));
        PaymentDatePicker.setValue(Payment.getDate());
        PaymentQuantityTextField.setText(String.valueOf(Payment.getQuantity()));
        PaymentPaymentTextField.setText(String.valueOf(Payment.getPayment()));
        PaymentCommentTextField.setText(Payment.getPaymentComment());
        CustomerComboBox.setValue(Payment.getCustomer());
    }


    @FXML
    public void deletePaymentAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Payment> selectionModel = PaymentListTableView.getSelectionModel();
        Payment Payment = selectionModel.getSelectedItem();
        PaymentDao.deletePayment(Payment);
        PaymentListTableView.getItems().remove(Payment);
    }

    @FXML
    public void clearPaymentForm() {
        PaymentIdLabel.setText("");
        PaymentQuantityTextField.clear();
        PaymentPaymentTextField.clear();
        PaymentCommentTextField.clear();
        PaymentDatePicker.setValue(null);
        CustomerComboBox.setValue(null);
    }

    private void setValuesToCustomerComboBox() {
        CustomerComboBox.setItems(FXCollections.observableArrayList(CustomerDao.getCustomers()));
    }

}
