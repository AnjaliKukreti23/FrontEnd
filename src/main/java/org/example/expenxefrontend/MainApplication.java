package org.example.expenxefrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URL;

import org.json.JSONObject;

public class MainApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApplication.class.getResource("ExpenseSummary.fxml"));
       // Scene scene1 = new Scene(fxmlLoader1.load());
        // stage.setTitle("Hello!");
        try {
            Parent root = fxmlLoader1.load();

            ExpenseSummaryController controller = fxmlLoader1.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            controller.populateFieldsFromURL("http://localhost:8090/expenditure/summary");

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        launch();
    }
}