package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sample.Helper.DBHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable{


    @FXML
    private StackPane mainPane;

    @FXML
    private JFXTextField etName;

    @FXML
    private JFXButton btnGO;

    private Connection connection;
    DBHelper dbHelper = new DBHelper();
    RequiredFieldValidator validator = new RequiredFieldValidator();

    @FXML
    void textValidate(KeyEvent event) {
        if (etName.getText().equals("")){
            validator.setMessage("Name Can't be Empty");
            etName.getValidators().add(validator);
            etName.validate();
        }else{
            etName.resetValidation();
        }

    }
    @FXML
    void NextPage(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (etName.getText().equals("")){
            validator.setMessage("Name Can't be Empty");
            etName.getValidators().add(validator);
            etName.validate();
        }else{
            String name = etName.getText();
            writeToDB(name);
            etName.setText("");
            loadUI("/sample/Layout/MenuActivity");
        }
    }

    public void writeToDB(String name) throws SQLException, ClassNotFoundException {

        String query = "INSERT INTO customer (nama_customer,total_order,total_pay) VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);
        preparedStatement.executeUpdate();
    }

    public void loadUI(String layout) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
            mainPane.getChildren().add(root);
        }catch (IOException e){

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = dbHelper.getConnection();
            dbHelper.updateNavMenu(connection,0);
            dbHelper.updateNavPage(connection,0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
