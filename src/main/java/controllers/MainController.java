package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane rootBorderPane;

    public MainController() {
    }

    @FXML
    private void initialize() {
    }

    @FXML
    public void printCompaniesAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLCompaniesList.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void printFinancesAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLPrintBalance.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void printEmployeeAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLPrintEmployees.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void printCustomersAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLPrintCustomers.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void printPaymentsAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLPrintPayment.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void InquiryAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLInquiry.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }

    public void InquiryCustomersAnchor(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/FXMLInquiryPayment.fxml"));
        rootBorderPane.setCenter(anchorPane);
    }



}
