package Employee;

import DBConnection.DatabaseConnection;
import Customer.LoginSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeLogin {

    @FXML
    private Button ExitButton;
    @FXML
    private TextField IdField;
    @FXML
    private PasswordField PassField;
    @FXML
    private Label WarningLabel;
    @FXML
    Button goBackBtn;
    @FXML
     Button loginButton;

    public void ExitButtonOnAction(ActionEvent event){

        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {

        if (!IdField.getText().isBlank() && !PassField.getText().isBlank()) {
            login();
        } else{
            WarningLabel.setText("Don't forget to enter your credentials!");
        }
    }
@FXML
    public void login () {


        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();


            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement("SELECT * FROM employeedetails WHERE  EmployeeID= ? and Password = ?");
                preparedStatement.setString(1, IdField.getText());
                preparedStatement.setString(2, PassField.getText());

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    loginBtn();

                } else {
                    WarningLabel.setText("invalid login. Please try again!");
                }


            } catch (Exception event) {
                event.printStackTrace();

            }
        }


    @FXML
public void loginBtn() throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(LoginSceneController.class.getResource("mainPage.fxml"));
    Stage stage1 = (Stage) loginButton.getScene().getWindow();
    stage1.setScene(new Scene(fxmlLoader.load(), 800, 609));
}
      @FXML
    public void goBackBtn() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginSceneController.class.getResource("Login.fxml"));
        Stage stage1 = (Stage) goBackBtn.getScene().getWindow();
        stage1.setScene(new Scene(fxmlLoader.load(), 849, 609));


    }



}




