package controllers;

import dao.CompanyDao;
import entities.Company;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.time.LocalDate;


public class CompaniesListController {
    private ObservableList<Company> listItems;

    @FXML
    private TableView companiesListTableView;

    @FXML
    private TableColumn companiesListTableViewIdColumn;

    @FXML
    private TableColumn companiesListTableViewNameColumn;

    @FXML
    private TableColumn companiesListTableViewFoundationDateColumn;

    @FXML
    private TableColumn companiesListTableViewInitialCapitalColumn;

    @FXML
    private Label companyIdLabel;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField companyInitialCapitalTextField;

    @FXML
    private DatePicker companyFoundationDateDatePicker;

    @FXML
    private void initialize() {
        loadCompanies();
        companiesListTableView.setEditable(true);
        companiesListTableViewNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void loadCompanies() {
        listItems = FXCollections.observableArrayList(CompanyDao.getCompanies());
        companiesListTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Company, Long>("id"));
        companiesListTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));
        companiesListTableViewFoundationDateColumn.setCellValueFactory(new PropertyValueFactory<Company, LocalDate>("foundationDate"));
        companiesListTableViewInitialCapitalColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("initialCapital"));
        companiesListTableView.setItems(listItems);
    }

    @FXML
    public void saveCompanyAction(ActionEvent actionEvent) {
        Company company = new Company();
        if (companyIdLabel.getText().length() > 0) {
            company.setId(Long.parseLong(companyIdLabel.getText()));
        }
        company.setName(companyNameTextField.getText());
        company.setFoundationDate(companyFoundationDateDatePicker.getValue());
        company.setInitialCapital(Double.valueOf(companyInitialCapitalTextField.getText()));

        CompanyDao.saveOrUpdateCompany(company);

        loadCompanies();

        clearCompanyForm();
    }

    @FXML
    public void editCompanyAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Company> selectionModel = companiesListTableView.getSelectionModel();
        Company company = selectionModel.getSelectedItem();
        companyIdLabel.setText(Long.toString(company.getId()));
        companyNameTextField.setText(company.getName());
        companyFoundationDateDatePicker.setValue(company.getFoundationDate());
        companyInitialCapitalTextField.setText(String.valueOf(company.getInitialCapital()));
    }


    @FXML
    public void deleteCompanyAction(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Company> selectionModel = companiesListTableView.getSelectionModel();
        Company company = selectionModel.getSelectedItem();
        CompanyDao.deleteCompany(company);
        companiesListTableView.getItems().remove(company);
    }

    @FXML
    public void clearCompanyForm() {
        companyIdLabel.setText("");
        companyNameTextField.clear();
        companyFoundationDateDatePicker.setValue(null);
        companyInitialCapitalTextField.clear();
    }

    public void changeCompanyNameCellEvent(TableColumn.CellEditEvent cellEditEvent) {
        TableView.TableViewSelectionModel<Company> selectionModel = companiesListTableView.getSelectionModel();
        Company company = selectionModel.getSelectedItem();

        company.setId(company.getId());

        company.setName(cellEditEvent.getNewValue().toString());

         company.setFoundationDate(company.getFoundationDate());
         company.setInitialCapital(company.getInitialCapital());
         CompanyDao.saveOrUpdateCompany(company);
    }


}
