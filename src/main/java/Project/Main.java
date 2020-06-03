package Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.*;
import entities.*;

import java.time.LocalDate;
import java.util.List;

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLMain.fxml"));
        primaryStage.setTitle("Electricity distribution company");
        primaryStage.setScene(new Scene(root, 800, 750));
        primaryStage.show();
    }

    public static void main(String[] args){

        launch(args);

    }
}
