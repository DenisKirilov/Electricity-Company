package controllers;

import dao.CompanyDao;
import dao.FinancesDao;
import entities.Company;
import entities.Finances;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.util.Collections;

public class InquiryController {

    private ObservableList<Finances> listItems;

    @FXML
    private Label totalIncomeLabel;

    @FXML
    private Label totalExpensesLabel;

    @FXML
    private Label totalProfitLabel;

    @FXML
    private Label totalProfitAfterTaxLabel;

    @FXML
    private ComboBox<Company> CompanyComboBox;

    @FXML
    BarChart<String,Number> bc;

    @FXML
    private void initialize() {
        CompanyComboBox.setPromptText("Select company");
        setValuesToCompanyComboBox();
    }

    @FXML
    private void loadTotalAction(ActionEvent event) {
        totalIncomeLabel.setText(String.valueOf(FinancesDao.sumIncome(CompanyComboBox.getValue())));
        totalExpensesLabel.setText(String.valueOf(FinancesDao.sumExpenses(CompanyComboBox.getValue())));
        totalProfitLabel.setText(String.valueOf(FinancesDao.sumProfit(CompanyComboBox.getValue())));
        totalProfitAfterTaxLabel.setText(String.valueOf(FinancesDao.sumProfitAfterTaxes(CompanyComboBox.getValue())));
        XYChart.Series chart = new XYChart.Series<>();
        chart.setName(CompanyComboBox.getValue().getName());
        chart.getData().add(new XYChart.Data( "Income", Double.valueOf(totalIncomeLabel.getText())));
        chart.getData().add(new XYChart.Data( "Expenses",Double.valueOf(totalExpensesLabel.getText())));
        bc.getData().add(chart);
    }

    @FXML
    public void clearAction(){
        totalIncomeLabel.setText("");
        totalExpensesLabel.setText("");
        totalProfitLabel.setText("");
        totalProfitAfterTaxLabel.setText("");
        bc.getData().removeAll(Collections.singleton(bc.getData().setAll()));
    }


    private void setValuesToCompanyComboBox() {
        CompanyComboBox.setItems(FXCollections.observableArrayList(CompanyDao.getCompanies()));
    }
}