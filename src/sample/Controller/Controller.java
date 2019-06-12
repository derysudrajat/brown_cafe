package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Helper.DBHelper;
import sun.security.pkcs11.Secmod;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane ControllerPane;
    @FXML
    public JFXButton navRegister;
    @FXML
    public JFXButton navMenu;
    @FXML
    public JFXButton navRevChe;
    @FXML
    public JFXButton navGetRec;

    private int navTarget=0;
    DBHelper dbHelper = new DBHelper();
    Connection connection;


    @FXML
    private void gotoMenu() throws SQLException {
        navTarget=1;
        dbHelper.updateNavPage(connection,navTarget);
        getUI(navTarget);

    }

    @FXML
    private void gotoRecip() throws SQLException {
        navTarget=3;
        dbHelper.updateNavPage(connection,navTarget);
        getUI(navTarget);
    }

    @FXML
    private void gotoRegister() throws SQLException {
    }

    @FXML
    private void gotoReview() throws SQLException {
        navTarget=2;
        dbHelper.updateNavPage(connection,navTarget);
        getUI(navTarget);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = dbHelper.getConnection();
            setLayout(0);
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {

        }

    }

    private void getUI(int i) {
        setLayout(i);
    }
    private void setLayout(int i){
        switch (i){
            case 0:
                loadUI("/sample/Layout/RegisterActivity",ControllerPane);
                break;
            case 1:
                loadUI("/sample/Layout/MenuActivity",ControllerPane);

                break;
            case 2:
                loadUI("/sample/Layout/MenuCategory",ControllerPane);

                break;
            case 3:
                loadUI("/sample/Layout/OrderResumeActivity",ControllerPane);

                break;
        }
    }
    private void loadUI(String layout,AnchorPane pane ) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
            pane.getChildren().add(root);
        }catch (IOException ignored){

        }
    }

}
