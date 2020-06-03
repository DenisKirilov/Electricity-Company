package controllers;

import Project.CustomerType;
import dao.CompanyDao;
import dao.CustomerDao;
import entities.Company;
import entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.time.LocalDate;


public class CustomerController {
    private ObservableList<Customer> listItems;

    @FXML
    private TableView CustomerListTableView;

    @FXML
    private TableColumn CustomerListTableViewIdColumn;

    @FXML
    private TableColumn CustomerListTableViewFirstNameColumn;

    @FXML
    private TableColumn CustomerListTableViewLastNameColumn;

    @FXML
    private TableColumn CustomerListTableViewCustomerTypeColumn;

    @FXML
    private TableColumn CustomerListTableViewCompanyColumn;

    @FXML
    private ComboBox<Company> CompanyComboBox;

    @FXML
    private ComboBox<CustomerType> CustomerTypeComboBox;

    @FXML
    private Label CustomerIdLabel;

    @FXML
    private TextField CustomerFirstNameTextField;

    @FXML
    private TextField CustomerLastNameTextField;

    @FXML
    private Label CustomerFirstNameErrorMessage;

    @FXML
    private Label CustomerLastNameErrorMessage;


    @FXML
    private void initialize() {
        loadCustomers();
        CustomerListTableView.setEditable(true);
        setValuesToCustomerTypeComboBox();
        setValuesToCompanyComboBox();
    }

    public void loadCustomers() {
        listItems = FXCollections.observableArrayList(CustomerDao.getCustomers());
        CustomerListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Long>("id"));
        CustomerListTableViewFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        CustomerListTableViewLastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        CustomerListTableViewCustomerTypeColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustomerType"));
        CustomerListTableViewCompanyColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Company"));
        CustomerListTableView.setItems(listItems);
    }

    @FXML
    public void saveCustomerAction(ActionEvent actionEvent) {
        Customer Customer = new Customer();
        if (CustomerIdLabel.getText().length() > 0) {
            Customer.setId(Long.parseLong(CustomerIdLabel.getText()));
        }
        if (CustomerFirstNameFormValidation() && CustomerLastNameFormValidation()) {
            Customer.setFirstName(CustomerFirstNameTextField.getText());
            Customer.setLastName(CustomerLastNameTextField.getText());

            Customer.setCustomerType(CustomerTypeComboBox.getValue());
            Customer.setCompany(CompanyComboBox.getValue());


            CustomerDao.saveOrUpdateCustomer(Customer);

            loadCustomers();

            clearCustomerForm();
        }
    }

    @FXML
    public void editCustomerAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Customer> selectionModel = CustomerListTableView.getSelectionModel();
        Customer Customer = selectionModel.getSelectedItem();
        CustomerIdLabel.setText(Long.toString(Customer.getId()));
        CustomerFirstNameTextField.setText(Customer.getFirstName());
        CustomerLastNameTextField.setText(Customer.getLastName());
        CustomerTypeComboBox.setValue(Customer.getCustomerType());
        CompanyComboBox.setValue(Customer.getCompany());
    }


    @FXML
    public void deleteCustomerAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Customer> selectionModel = CustomerListTableView.getSelectionModel();
        Customer Customer = selectionModel.getSelectedItem();
        CustomerDao.deleteCustomer(Customer);
        CustomerListTableView.getItems().remove(Customer);
    }

    @FXML
    public void clearCustomerForm() {
        CustomerIdLabel.setText("");
        CustomerFirstNameTextField.clear();
        CustomerLastNameTextField.clear();
        CustomerTypeComboBox.setValue(null);
        CompanyComboBox.setValue(null);
    }

    public void changeCustomerFirstNameCellEvent(TableColumn.CellEditEvent cellEditEvent) {
        TableView.TableViewSelectionModel<Customer> selectionModel = CustomerListTableView.getSelectionModel();
        Customer Customer = selectionModel.getSelectedItem();
        Customer.setId(Customer.getId());
        Customer.setFirstName(cellEditEvent.getNewValue().toString());
        Customer.setLastName(Customer.getLastName());
        Customer.setCustomerType(Customer.getCustomerType());
        CustomerDao.saveOrUpdateCustomer(Customer);
    }

    public void changeCustomerLastNameCellEvent(TableColumn.CellEditEvent cellEditEvent) {
        TableView.TableViewSelectionModel<Customer> selectionModel = CustomerListTableView.getSelectionModel();
        Customer Customer = selectionModel.getSelectedItem();
        Customer.setId(Customer.getId());
        Customer.setFirstName(cellEditEvent.getNewValue().toString());
        Customer.setLastName(Customer.getLastName());
        Customer.setCustomerType(Customer.getCustomerType());
        CustomerDao.saveOrUpdateCustomer(Customer);
    }


    public boolean CustomerFirstNameFormValidation() {
        return CustomerFirstNameValidation();

    }

    public boolean CustomerLastNameFormValidation() {
        return CustomerLastNameValidation();

    }

    public boolean CustomerFirstNameValidation() {
        if (CustomerFirstNameTextField.getText().isEmpty()) {
            CustomerFirstNameErrorMessage.setText("First Name cannot be blank!");
            return false;
        }
        return true;
    }

    public boolean CustomerLastNameValidation() {
        if (CustomerLastNameTextField.getText().isEmpty()) {
            CustomerLastNameErrorMessage.setText("Last Name cannot be blank!");
            return false;
        }
        return true;
    }

    private void setValuesToCustomerTypeComboBox() {
        CustomerTypeComboBox.setItems(FXCollections.observableArrayList(CustomerType.values()));
    }

    private void setValuesToCompanyComboBox() {
        CompanyComboBox.setItems(FXCollections.observableArrayList(CompanyDao.getCompanies()));
    }

}
