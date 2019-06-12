package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sample.Entity.IndonesaianFood;
import sample.Entity.OrderList;
import sample.Helper.DBHelper;
import sample.Helper.Popup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderResumeController implements Initializable {
    @FXML
    private JFXListView<OrderList> listview;
    private ObservableList<OrderList> listofOrder = FXCollections.observableArrayList();
    private OrderList orderList;
    @FXML
    private StackPane orderResumePane;
    @FXML
    private Text txtCustomerName;
    @FXML
    private Text txtSumHarga;
    @FXML
    private MaterialDesignIconView icon_edit;
    @FXML
    private JFXButton btnFinish;
    @FXML
    private JFXTextField etEditName;
    @FXML
    private JFXButton btnEdit;
    boolean onEdit = true;
    Connection connection;
    Popup popup = new Popup();
    RequiredFieldValidator validator = new RequiredFieldValidator();
    DBHelper dbHelper = new DBHelper();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        etEditName.setVisible(false);
        etEditName.setManaged(false);
        try {
            connection = dbHelper.getConnection();
            LoadData();
            dbHelper.updateNavPage(connection,3);
            int sumData = dbHelper.getSumofOder(connection);
            txtSumHarga.setText(sumData+"K");
            String namaCustomer = dbHelper.getNamaCustomer(connection);
            txtCustomerName.setText(namaCustomer);
        } catch (SQLException e) {
            System.out.println("Database not Connected");
        } catch (ClassNotFoundException e) {

        }
    }

    @FXML
    void validateText(KeyEvent event) {
        if (etEditName.getText().equals("")){
            validator.setMessage("Name Can't be Empty");
            etEditName.getValidators().add(validator);
            etEditName.validate();
        }else{
            etEditName.resetValidation();
        }
    }
    @FXML
    void editName(ActionEvent event) {
        if (onEdit){
            String name = dbHelper.getNamaCustomer(connection);
            icon_edit.setGlyphName("CHECK");
            etEditName.setText(name);
            txtCustomerName.setVisible(false);
            txtCustomerName.setManaged(false);
            etEditName.setVisible(true);
            etEditName.setManaged(true);
            onEdit = false;
        }else{
            if (etEditName.getText().equals("")){
                validator.setMessage("Name Can't be Empty");
                etEditName.getValidators().add(validator);
                etEditName.validate();
            }else{
                String name = etEditName.getText();
                int id = dbHelper.getIdCustomer(connection);
                dbHelper.updateCustomerName(connection,name,id);
                etEditName.setText("");
                icon_edit.setGlyphName("BORDER_COLOR");
                txtCustomerName.setVisible(true);
                txtCustomerName.setManaged(true);
                etEditName.setVisible(false);
                etEditName.setManaged(false);
                onEdit = true;
                String namaCustomer = dbHelper.getNamaCustomer(connection);
                txtCustomerName.setText(namaCustomer);
                popup.toast(orderResumePane,"Name was Updated Successfully");
            }
        }
    }

    public void goBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/Layout/MenuCategory.fxml"));
            orderResumePane.getChildren().add(root);
        }catch (IOException e){

        }
    }

    class Cell extends ListCell<OrderList> {
        HBox hbox = new HBox();
        Label qty = new Label(" ");
        Pane pane =  new Pane();
        Pane pane2 =  new Pane();

        Label name = new Label(" ");

        Label harga = new Label(" ");


        public Cell(){

            name.setFont(Font.font("Product Sans", FontWeight.BOLD, 16));
            harga.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));
            qty.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));

            hbox.getChildren().addAll(name, pane,qty,pane2,harga);

            hbox.setHgrow(pane, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER);

            hbox.setSpacing(4.0);
            pane2.setPadding(new Insets(0,30,0,0));
            name.setPadding(new Insets(0,0,0,16));
            harga.setPadding(new Insets(0,16,0,16));
        }
        public void updateItem(OrderList orderList, boolean empty){
            super.updateItem(orderList, empty);
            setText(null);
            setGraphic(null);
            if(name!=null && !empty){
                this.name.setText(orderList.getNamaOrder());
                this.harga.setText((orderList.getHargaOrder()*orderList.getQtyOrder())+"K");
                this.qty.setText("@"+orderList.getQtyOrder());
                setGraphic(hbox);
            }
        }
    }

    @FXML
    void finish(ActionEvent event) throws IOException {
        try {String namaCustomer = dbHelper.getNamaCustomer(connection);
            dbHelper.deleteOrderList(connection);
            popup.poup1DialogandLoadUI(orderResumePane,"INFO",
                    "Terimakasih "+namaCustomer+" telah memesan, silahkan tunggu dimeja\n" +
                            "hingga pesanan selesai dibuat, salam hangat.","CONFIRM",
                    "/sample/Layout/RegisterActivity");
        }catch (Exception e){

        }
    }
    private void LoadData() throws SQLException {
        listofOrder = dbHelper.readOrderListDB(connection);
        int totalOder = dbHelper.getTotalOrder(connection);
        int totalPay = dbHelper.getSumofOder(connection);
        int idCustomer = dbHelper.getIdCustomer(connection);
        dbHelper.InsertCustomer(connection,totalOder,totalPay,idCustomer);
        listview.setCellFactory(param -> new Cell());
        listview.setItems(listofOrder);
    }

    public void loadUI(String layout) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
            orderResumePane.getChildren().addAll(root);
        }catch (IOException e){

        }
    }
}
