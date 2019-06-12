package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import sample.Entity.OrderList;
import sample.Helper.DBHelper;
import sample.Helper.Popup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuCategoryController implements Initializable {


    @FXML
    private StackPane menuCategoryPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton btnCart;
    @FXML
    public AnchorPane notifPane;
    @FXML
    public Text qtyNotif;
    @FXML
    private JFXButton btnMenu;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnCheckOut;
    @FXML
    private Text txtTittle;
    @FXML
    private JFXButton btnMenuIndo;
    @FXML
    private JFXButton btnMenuJpn;
    @FXML
    private JFXButton btnMenuJuice;
    @FXML
    private JFXButton btnMenuCoffee;
    @FXML
    private AnchorPane content;
    @FXML
    private JFXButton btnMin;
    @FXML
    private JFXButton btnPlus;
    @FXML
    private MaterialDesignIconView iconAdd;
    @FXML
    private JFXCheckBox cbTest;
    private int btnPressed;
    private int order =0;
    private boolean onChart;
    private Popup popup = new Popup();
    Connection connection;
    DBHelper dbHelper = new DBHelper();
    private ObservableList<OrderList> listOfOrder = FXCollections.observableArrayList();
    CartController cartController =  new CartController();

    public void getNotificationPane() throws IOException, SQLException, ClassNotFoundException {
     cartController.loadNotif(notifPane,qtyNotif);
    }

    public Text getQtyNotifText(){
        return qtyNotif;
    }

    @FXML
    void add(ActionEvent event) {
            order+=1;
            btnPlus.setText(String.valueOf(order));
            iconAdd.setVisible(false);
            btnMin.setVisible(true);
    }
    @FXML
    void min(ActionEvent event) {
            if (order > 1) {
                order -= 1;
                btnPlus.setText(String.valueOf(order));
            } else {
                order -= 1;
                btnMin.setVisible(false);
                iconAdd.setVisible(true);
                btnPlus.setText("");
            }
    }

    @FXML
    public void gotoCart() throws IOException, SQLException, ClassNotFoundException {
        onChart = true;
        btnCheckOut.setVisible(true);
        btnCheckOut.setManaged(true);
        btnMenuIndo.setVisible(false);
        btnMenuIndo.setManaged(false);
        btnMenuJpn.setVisible(false);
        btnMenuJpn.setManaged(false);
        btnMenuJuice.setVisible(false);
        btnMenuJuice.setManaged(false);
        btnMenuCoffee.setVisible(false);
        btnMenuCoffee.setManaged(false);
        listOfOrder = dbHelper.readOrderListDB(connection);
        System.out.println("List of Order: "+listOfOrder.size());
        if (listOfOrder.size()==0){
            btnCheckOut.setText("ADD ITEM");
        }
        dbHelper.LoadNotif(notifPane,qtyNotif);
        txtTittle.setText("Your Order");
        LoadUIMenu("/sample/Layout/CartActivity");

    }


    
    public void setLayout(int i) throws IOException {
        switch (i){
            case 0:
                txtTittle.setText("Indonesian Food");
                LoadUIMenu("/sample/Layout/IndonesiaMenu");
                break;
            case 1:
                txtTittle.setText("Japan Food");
                LoadUIMenu("/sample/Layout/JapanMenu");
                break;
            case 2:
                txtTittle.setText("Juice Menu");
                LoadUIMenu("/sample/Layout/JuiceMenu");
                break;
            case 3:
                txtTittle.setText("Coffee Menu");
                LoadUIMenu("/sample/Layout/CoffeeMenu");
                break;
        }
    }

    public void LoadUIMenu(String layout) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
            borderPane.setCenter(root);
        }catch (IOException e){

        }
    }


    public void goBack() throws IOException, SQLException, ClassNotFoundException {
        if (onChart){
            setLayout(btnPressed);
            btnSet();
            onChart=false;
            dbHelper.LoadNotif(notifPane,qtyNotif);
        }else {
            Parent root = FXMLLoader.load(getClass().getResource( "/sample/Layout/MenuActivity.fxml"));
            menuCategoryPane.getChildren().addAll(root);
        }
    }

    public void gotoMenuIndo(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        btnPressed =0;
        updateNav();
        txtTittle.setText("Indonesian Food");
        LoadUIMenu("/sample/Layout/IndonesiaMenu");
    }

    public void gotoMenuJapan(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        btnPressed =1;
        updateNav();
        txtTittle.setText("Japan Food");
        LoadUIMenu("/sample/Layout/JapanMenu");
    }

    public void gotoMenuJuice(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        btnPressed =2;
        updateNav();
        txtTittle.setText("Juice Menu");
        LoadUIMenu("/sample/Layout/JuiceMenu");
    }

    public void gotoMenuCoffee(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        btnPressed =3;
        updateNav();
        txtTittle.setText("Coffee Menu");
        LoadUIMenu("/sample/Layout/CoffeeMenu");
    }

    @FXML
    void gotoCheckout() throws IOException {
        try {

            System.out.println("List of Order: "+listOfOrder.size());
            if (listOfOrder.size()==0){
                goBack();
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("/sample/Layout/OrderResumeActivity.fxml"));
                menuCategoryPane.getChildren().addAll(root);
            }
        }catch (Exception e){

        }

    }

    void btnSet(){
        btnCheckOut.setVisible(false);
        btnCheckOut.setManaged(false);
        btnMenuIndo.setVisible(true);
        btnMenuIndo.setManaged(true);
        btnMenuJpn.setVisible(true);
        btnMenuJpn.setManaged(true);
        btnMenuJuice.setVisible(true);
        btnMenuJuice.setManaged(true);
        btnMenuCoffee.setVisible(true);
        btnMenuCoffee.setManaged(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnSet();
        try {
            listOfOrder = dbHelper.readOrderListDB(connection);
            getData();
            dbHelper.updateNavPage(connection,2);
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {

        }
    }
    @FXML
    void showMenu(ActionEvent event) {
        if (onChart){
            popup.poup3Menu(connection,menuCategoryPane,btnMenu,"Help","Delete All ","Info ",borderPane, notifPane,qtyNotif);
        }else{
            popup.poup2Menu(menuCategoryPane,btnMenu,"Help","Info ");
        }

    }
    @FXML
    void LoadNotification(MouseEvent event){
        try {
            dbHelper.LoadNotif(notifPane,qtyNotif);
        }catch (Exception e){

        }

    }

    public void getData() throws SQLException, ClassNotFoundException {
        DBHelper dbHelper = new DBHelper();
        connection = dbHelper.getConnection();
        String query1 = "SELECT * FROM navigator;";
        ResultSet resultSet;
        int navMenu = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                navMenu = resultSet.getInt("nav_manu");
            }
            dbHelper.LoadNotif(notifPane,qtyNotif);
        }catch (Exception e){
            System.out.println("Exception : "+e);
        }
        btnMin.setVisible(false);
        System.out.println("NavMenu : "+navMenu);
        try {
            setLayout(navMenu);
        } catch (IOException e) {

        }
    }

    public void updateNav() throws SQLException{
        dbHelper.LoadNotif(notifPane,qtyNotif);
        dbHelper.updateNavMenu(connection,btnPressed);
    }
}
