package org.example.expenxefrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExpenseSummaryController {



    @FXML
    private TextField month;

    @FXML
    private TextField amount;

    @FXML
    private Button home;

    @FXML
    private Button spend;



    public void populateFieldsFromURL(String url) {
        try {
            // Create URL object
            URL apiUrl = new URL(url);

            // Create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();

            // Check if the response code is OK (200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON string to JSONObject
                JSONObject jsonObject = new JSONObject(response.toString());

                // Get the expenditure list
                JSONArray expenditureList = jsonObject.getJSONArray("expenditureList");

                // Check if there are any expenditures
                if (expenditureList.length() > 0) {
                    // Get the first expenditure (assuming there's only one in your example)
                    JSONObject expenditure = ((JSONArray) expenditureList).getJSONObject(0);

                    // Extract month and amount from the expenditure
                    String monthAndYear = expenditure.getString("monthAndYear");
                    BigDecimal expenditureAmount = expenditure.getBigDecimal("amount");

                    // Populate the TextFields
                    month.setText(monthAndYear);
                    amount.setText(expenditureAmount.toString());
                } else {
                    // No expenditures found
                    System.out.println("No expenditures found in the JSON data.");
                }
            } else {
                // Error handling for HTTP request
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        // Set up the action for the spend button
        spend.setOnAction(event -> {
            try {
                // Load the expenditure list page (ExpenseList.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpensesList.fxml"));
                Parent expenseListPage = loader.load();
                Scene expenseListScene = new Scene(expenseListPage);

                // Get the stage from the button and set the scene to the expenditure list page
                Stage stage = (Stage) spend.getScene().getWindow();
                stage.setScene(expenseListScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}




