package org.example.expenxefrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveController {
   /* @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");*/

    @FXML
    private TextField description;



    @FXML
    private TextField date;

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<Currency> currency;

    @FXML
    private Button home;

   @FXML
    private Button spend;

   @FXML
   private Button save;

    @FXML
    private void initialize() {
        // Populate the currency ComboBox with enum values
        currency.getItems().addAll(Currency.values());

        // Set up the action for the save button
        save.setOnAction(event -> {
            // Extract data from the text fields
            String expenditureDescription = description.getText();
            String expenditureDate = null;

                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
                    try {
                        Date expeditureDate = sdf.parse(date.getText());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    //String expenditureDate = date.getText();

                BigDecimal expenditureAmount = new BigDecimal(amount.getText());

                Currency selectedCurrency = currency.getValue();

            // Create a JSON object representing the expenditure details
            JSONObject expenditureJson = new JSONObject();
            expenditureJson.put("description", expenditureDescription);
            expenditureJson.put("date", expenditureDate);
            expenditureJson.put("amount", expenditureAmount);


            }
          //  expenditureJson.put("currency", selectedCurrency.toString());

            // Send a POST request to the "expenditure/add" endpoint with the JSON data
            try {
                URL url = new URL("http://localhost:8090/expenditure/add");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                os.write(expenditureJson.toString().getBytes());
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Expenditure added successfully.");

                  /*  description.clear();
                    amount.clear();
                    date.clear();*/

                } else {
                    System.out.println("Failed to add expenditure. Response code: " + responseCode);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });


        spend.setOnAction(event -> {

            navigateToExpenditureList();
        });
    }

    private void navigateToExpenditureList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpensesList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) spend.getScene().getWindow();
            stage.setScene(scene);
            ExpenseListController expenseListController = loader.getController();
            expenseListController.refreshData();
            stage.show();

            // Optionally, you can also refresh the data in the expenditure list

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

