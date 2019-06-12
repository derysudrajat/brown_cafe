package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import sample.Entity.Customer;
import sample.Entity.IndonesaianFood;
import sample.Helper.DBHelper;
import sample.Helper.Popup;
import sun.security.pkcs11.Secmod;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private StackPane mainPane;

    @FXML
    private JFXButton btnMenuIndo;

    @FXML
    private JFXButton btnMenuJapan;

    @FXML
    private JFXButton btnMenuJuice;

    @FXML
    private JFXButton btnMenuCoffee;

    @FXML
    private JFXButton btnMenu;
    Connection connection;
    DBHelper dbHelper = new DBHelper();
    Popup popup = new Popup();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    int btnPressed;

    @FXML
    void goToMenu1(){
        btnPressed =0;
        goCategory();
    }

    @FXML
    void goToMenu2(){
        btnPressed =1;
        goCategory();
    }

    @FXML
    void goToMenu3(){
        btnPressed =2;
        goCategory();

    }

    @FXML
    void goToMenu4(){
        btnPressed =3;
        goCategory();

    }


    @FXML
    void goCategory(){
        try {
            dbHelper.updateNavMenu(connection,btnPressed);
            loadUI("/sample/Layout/MenuCategory");
        }catch (Exception e){

        }
    }
    public void loadUI(String layout){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
            mainPane.getChildren().add(root);
        }catch (IOException e){

        }
    }
    @FXML
    void openMenu(ActionEvent event) {
        popup.poup2Menu(mainPane,btnMenu,"Help","Info ");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = dbHelper.getConnection();
            dbHelper.updateNavPage(connection,1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
