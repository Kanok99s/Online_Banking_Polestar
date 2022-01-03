package Customer;


import DBConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class UpdatePassword implements Initializable {

    @FXML
    TextField idField;

    @FXML
     ComboBox<String> questionField;

    @FXML
    TextField answerField;

    @FXML
    TextField newPassField;

    @FXML
    Button newPassBtn;
    @FXML
     Label error;
    @FXML
    Button goBackBtn;
@FXML
Button exitBtn;


    @FXML
    protected void passSet() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB  = connectNow.getConnection();


        try {
            if (idField.getText().isEmpty() || questionField.getSelectionModel().getSelectedItem().isEmpty() || answerField.getText().isEmpty() || newPassField.getText().isEmpty()) {
                error.setText("Please fill in all boxes");
            } else if
            (newPassField.getText().length() < 8) {
                error.setText("Please choose at least 8 characters, numbers, and symbols");
            } else {

                PreparedStatement preparedStatement = connectDB.prepareStatement("SELECT * FROM CustomerInformation.customer_details WHERE  IDnumber= ? and Security_question = ? and Answer = ?");
                preparedStatement.setString(1, idField.getText());
                preparedStatement.setString(2, questionField.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, answerField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    error.setText("invalid. Please try again!");

                } else {

                    preparedStatement = connectDB.prepareStatement("Update customer_details SET Password = ? WHERE  IDnumber= ? and Security_question = ? and Answer = ?");
                    preparedStatement.setString(1, newPassField.getText());
                    preparedStatement.setString(2, idField.getText());
                    preparedStatement.setString(3, String.valueOf(questionField.getSelectionModel().getSelectedItem().toString())); //Change method
                    preparedStatement.setString(4, answerField.getText());


                    System.out.println(idField.getText());
                    System.out.println(String.valueOf(questionField.getSelectionModel().getSelectedItem()));
                    System.out.println(String.valueOf(answerField.getText()));

                    preparedStatement.executeUpdate();
                    if (preparedStatement.execute())
                        System.out.println("Updating Password");
                    newPassBtn();
                }
                System.out.println(newPassField.getText());
            }
            }catch(SQLException | IOException e){
            e.printStackTrace();
        }
        }




    @FXML
    public void newPassBtn() throws Exception{
    FXMLLoader fxmlLoader = new FXMLLoader(SuccessWindow.class.getResource("SuccessfulPassUpdate.fxml"));
    Stage stage1 = (Stage) newPassBtn.getScene().getWindow();
        stage1.setScene(new Scene(fxmlLoader.load(), 580, 245));

}
    @FXML
    public void goBackBtn() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginSceneController.class.getResource("login.fxml"));
        Stage stage1 = (Stage) goBackBtn.getScene().getWindow();
        stage1.setScene(new Scene(fxmlLoader.load(), 849, 609));
    }


    public void exit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        questionField.getItems().removeAll(questionField.getItems());
        questionField.getItems().addAll("Select a security question", "What high school did you attend?", "In what city were you born?", "What was the first concert you attended?", "What is your pets name?",
                "What is your mothers middle name?", "What is your grandmothers first name?","What was your dream job as a child?","What was your childhood nickname?","Who was your childhood hero?");
        questionField.getSelectionModel().select("Select a security question");


      //  Image img = new Image("src/main/img/com/example/employee/src/main/img/com/example/employee/src/main/img/Bank_symbol 13.40.55 13.40.55 13.40.55.png");
    }


}
