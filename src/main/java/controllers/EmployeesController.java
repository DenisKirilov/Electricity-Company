package controllers;

import dao.CompanyDao;
import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;


public class EmployeesController {
    private ObservableList<Employee> listItems;

    @FXML
    private TableView EmployeeListTableView;

    @FXML
    private TableColumn EmployeeListTableViewIdColumn;

    @FXML
    private TableColumn EmployeeListTableViewFirstNameColumn;

    @FXML
    private TableColumn EmployeeListTableViewLastNameColumn;

    @FXML
    private TableColumn EmployeeListTableViewBirthDateColumn;

    @FXML
    private TableColumn EmployeeListTableViewHireDateColumn;

    @FXML
    private TableColumn EmployeeListTableViewSalaryColumn;

    @FXML
    private TableColumn EmployeeListTableViewCompanyColumn;

    @FXML
    private ComboBox<Company> CompanyComboBox;

    @FXML
    private Label EmployeeIdLabel;

    @FXML
    private TextField EmployeeFirstNameTextField;

    @FXML
    private TextField EmployeeLastNameTextField;

    @FXML
    private DatePicker EmployeeBirthDatePicker;

    @FXML
    private DatePicker EmployeeHireDatePicker;

    @FXML
    private TextField EmployeeSalaryTextField;

    @FXML
    private Label EmployeeFirstNameErrorMessage;

    @FXML
    private Label EmployeeLastNameErrorMessage;


    @FXML
    private void initialize() {
        loadEmployees();
        EmployeeListTableView.setEditable(true);
        birthDatePickerDisableFutureDates();
        hireDatePickerDisableFutureDates();
        setValuesToCompanyComboBox();
    }

    public void loadEmployees() {
        listItems = FXCollections.observableArrayList(EmployeeDao.getEmployees());
        EmployeeListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        EmployeeListTableViewFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        EmployeeListTableViewLastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        EmployeeListTableViewBirthDateColumn.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("birthDate"));
        EmployeeListTableViewHireDateColumn.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("hireDate"));
        EmployeeListTableViewSalaryColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        EmployeeListTableViewCompanyColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Company"));
        EmployeeListTableView.setItems(listItems);
    }

    @FXML
    public void saveEmployeeAction(ActionEvent actionEvent) {
        Employee Employee = new Employee();
        if (EmployeeIdLabel.getText().length() > 0) {
            Employee.setId(Long.parseLong(EmployeeIdLabel.getText()));
        }
        if (employeeFirstNameFormValidation() && employeeLastNameFormValidation()) {
            Employee.setFirstName(EmployeeFirstNameTextField.getText());
            Employee.setLastName(EmployeeLastNameTextField.getText());

            Employee.setBirthDate(EmployeeBirthDatePicker.getValue());
            Employee.setHireDate(EmployeeHireDatePicker.getValue());
            Employee.setSalary(Double.valueOf(EmployeeSalaryTextField.getText()));

            Employee.setCompany(CompanyComboBox.getValue());


            EmployeeDao.saveOrUpdateEmployee(Employee);

            loadEmployees();

            clearEmployeeForm();
        }
    }

    @FXML
    public void editEmployeeAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = EmployeeListTableView.getSelectionModel();
        Employee Employee = selectionModel.getSelectedItem();
        EmployeeIdLabel.setText(Long.toString(Employee.getId()));
        EmployeeFirstNameTextField.setText(Employee.getFirstName());
        EmployeeLastNameTextField.setText(Employee.getLastName());
        EmployeeBirthDatePicker.setValue(Employee.getBirthDate());
        EmployeeHireDatePicker.setValue(Employee.getHireDate());
        EmployeeSalaryTextField.setText(String.valueOf(Employee.getSalary()));
        CompanyComboBox.setValue(Employee.getCompany());
    }


    @FXML
    public void deleteEmployeeAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = EmployeeListTableView.getSelectionModel();
        Employee Employee = selectionModel.getSelectedItem();
        EmployeeDao.deleteEmployee(Employee);
        EmployeeListTableView.getItems().remove(Employee);
    }

    @FXML
    public void clearEmployeeForm() {
        EmployeeIdLabel.setText("");
        EmployeeFirstNameTextField.clear();
        EmployeeLastNameTextField.clear();
        EmployeeBirthDatePicker.setValue(null);
        EmployeeHireDatePicker.setValue(null);
        EmployeeSalaryTextField.clear();
        CompanyComboBox.setValue(null);
    }

    public void changeEmployeeFirstNameCellEvent(TableColumn.CellEditEvent cellEditEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = EmployeeListTableView.getSelectionModel();
        Employee employee = selectionModel.getSelectedItem();
        employee.setId(employee.getId());
        employee.setFirstName(cellEditEvent.getNewValue().toString());
        employee.setLastName(employee.getLastName());
        employee.setBirthDate(employee.getBirthDate());
        employee.setHireDate(employee.getHireDate());
        EmployeeDao.saveOrUpdateEmployee(employee);
    }

    public void changeEmployeeLastNameCellEvent(TableColumn.CellEditEvent cellEditEvent) {
        TableView.TableViewSelectionModel<Employee> selectionModel = EmployeeListTableView.getSelectionModel();
        Employee employee = selectionModel.getSelectedItem();
        employee.setId(employee.getId());
        employee.setFirstName(cellEditEvent.getNewValue().toString());
        employee.setLastName(employee.getLastName());
        employee.setBirthDate(employee.getBirthDate());
        employee.setHireDate(employee.getHireDate());
        EmployeeDao.saveOrUpdateEmployee(employee);
    }


    public boolean employeeFirstNameFormValidation() {
        return employeeFirstNameValidation();

    }

    public boolean employeeLastNameFormValidation() {
        return employeeLastNameValidation();

    }

    public boolean employeeFirstNameValidation() {
        if (EmployeeFirstNameTextField.getText().isEmpty()) {
            EmployeeFirstNameErrorMessage.setText("First Name cannot be blank!");
            return false;
        }
        return true;
    }

    public boolean employeeLastNameValidation() {
        if (EmployeeLastNameTextField.getText().isEmpty()) {
            EmployeeLastNameErrorMessage.setText("Last Name cannot be blank!");
            return false;
        }
        return true;
    }

    public void birthDatePickerDisableFutureDates() {
        EmployeeBirthDatePicker.setDayCellFactory(getDayCellFactory());
    }


    public void hireDatePickerDisableFutureDates() {
        EmployeeHireDatePicker.setDayCellFactory(getDayCellFactory());
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: gray");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }


    private void setValuesToCompanyComboBox() {
        CompanyComboBox.setItems(FXCollections.observableArrayList(CompanyDao.getCompanies()));
    }

}
