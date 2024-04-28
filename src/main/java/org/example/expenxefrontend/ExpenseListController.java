package org.example.expenxefrontend;

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
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExpenseListController {

    @FXML

    private Button newButton;

    @FXML
    private Button home;

    @FXML
    private Button spend;



    @FXML
    private TextField description;

    @FXML
    private TextField date;

    @FXML
    private TextField amount;

    @FXML
    private void initialize(){
        populateFieldsFromURL("http://localhost:8090/expenditure/");


        newButton.setOnAction(event -> {
            try {
                // Load the save page (Save.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Save.fxml"));
                Parent savePage = loader.load();
                Scene saveScene = new Scene(savePage);

                // Get the stage from the button and set the scene to the save page
                Stage stage = (Stage) newButton.getScene().getWindow();
                stage.setScene(saveScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void populateFieldsFromURL(String url){
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

                // Get the expenditures array
                JSONArray expenditures = jsonObject.getJSONArray("expenditures");

                // Check if there are any expenditures
                if (expenditures.length() > 0) {
                    // Get the first expenditure (assuming there's only one in your example)
                    JSONObject expenditure = ((JSONArray) expenditures).getJSONObject(0);

                    // Extract data from the expenditure
                    String expenditureDescription = expenditure.getString("description");
                    String expenditureDate = expenditure.getString("date");
                    BigDecimal expenditureAmount = expenditure.getBigDecimal("amount");

                    // Populate the text fields
                    description.setText(expenditureDescription);
                    date.setText(expenditureDate);
                    amount.setText(expenditureAmount.toString());

                }
            }
        }catch(IOException e){
                    System.out.println(e);
                }
    }

    public void refreshData() {
        populateFieldsFromURL("http://localhost:8090/expenditure/");
    }




}
