package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class CartController implements Initializable {
    public StackPane cartStackpane;
    @FXML
    private JFXListView<OrderList> listview;
    private ObservableList<OrderList> listOfOrder = FXCollections.observableArrayList();
    private OrderList orderList;
    private AnchorPane notifPane;
    private Text notifText;
    Connection connection;
    DBHelper dbHelper =  new DBHelper();
    Popup popup = new Popup();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = dbHelper.getConnection();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {

        }
        LoadData();
    }

    public void loadNotif(AnchorPane notifPane, Text qtyNotif){
        this.notifPane = notifPane;
        this.notifText = notifText;
        dbHelper.LoadNotif(notifPane,qtyNotif);
    }


    @FXML
    void showData(MouseEvent event) {
        orderList = listview.getSelectionModel().getSelectedItem();
        System.out.println(orderList.getQtyOrder());
    }

    class Cell extends ListCell<OrderList> {
        HBox hbox = new HBox();
        JFXButton btn1 = new JFXButton(" ");
        JFXButton btn2 = new JFXButton(" ");
        JFXButton btn3 = new JFXButton(" ");
        Label order = new Label(" ");
        Pane pane =  new Pane();
        VBox vbox = new VBox();
        HBox qtyHbox =  new HBox();
        Label name = new Label(" ");
        Label harga = new Label(" ");
        Image cart = new Image("/sample/image/ic_paw.png");
        ImageView img =  new ImageView(cart);
        public Cell(){
            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
            MaterialDesignIconView icon2 = new MaterialDesignIconView(MaterialDesignIcon.MINUS);
            MaterialDesignIconView icon3 = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
            icon.setGlyphSize(16);
            icon2.setGlyphSize(16);
            icon3.setGlyphSize(18);

            icon.getStyleClass().addAll("icon_list_min");
            icon2.getStyleClass().addAll("icon_list_min");
            icon3.getStyleClass().addAll("icon_list_add");

            btn1.setGraphic(icon);
            btn2.setGraphic(icon2);
            btn3.setGraphic(icon3);

            btn1.setAlignment(Pos.CENTER);
            btn2.setAlignment(Pos.CENTER);
            btn3.setAlignment(Pos.CENTER);

            btn1.setContentDisplay(ContentDisplay.CENTER);
            btn2.setContentDisplay(ContentDisplay.CENTER);
            btn3.setContentDisplay(ContentDisplay.CENTER);

            btn1.getStyleClass().addAll("bnt_list_add2");
            btn2.getStyleClass().addAll("bnt_list_min2");
            btn3.getStyleClass().addAll("btn_list_delete");

            btn1.setOnAction(event -> {
                try {
                    orderList = listview.getSelectionModel().getSelectedItem();
                    int newQty = orderList.getQtyOrder()+1;
                    dbHelper.updateQtyOrder(connection,orderList.getIdOrder(),newQty);
                    orderList.setQtyOrder(newQty);
                    boolean empty =false;
                    updateItem(orderList,empty);
                }catch (Exception e){
                    popup.toast(cartStackpane, "Please select item first");
                }

            });
            btn2.setOnAction(event -> {
                try {
                    orderList = listview.getSelectionModel().getSelectedItem();
                    if (orderList.getQtyOrder()>1){
                        int newQty = orderList.getQtyOrder()-1;
                        dbHelper.updateQtyOrder(connection,orderList.getIdOrder(),newQty);
                        orderList.setQtyOrder(newQty);
                        boolean empty =false;
                        updateItem(orderList,empty);
                    }else{
                        dialofDelete(orderList);
                    }
                }catch (Exception e){
                    popup.toast(cartStackpane, "Please select item first");
                }
            });
            btn3.setOnAction(event -> {
                try {
                    orderList = listview.getSelectionModel().getSelectedItem();
                    if (orderList==null){
                        popup.toast(cartStackpane, "Please select item first");
                    }else{
                        dialofDelete(orderList);
                    }
                }catch (Exception e){
                    popup.toast(cartStackpane, "Please select item first");
                }

            });
            name.setFont(Font.font("Product Sans", FontWeight.BOLD, 16));
            order.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));
            harga.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));


            qtyHbox.getChildren().addAll(btn2,order,btn1);
            vbox.getChildren().addAll(name,harga);
            hbox.getChildren().addAll(img,vbox, pane,qtyHbox, btn3);
            vbox.setAlignment(Pos.CENTER_LEFT);
            qtyHbox.setAlignment(Pos.CENTER);
            hbox.setAlignment(Pos.CENTER);
            qtyHbox.setHgrow(pane, Priority.ALWAYS);
            hbox.setHgrow(pane, Priority.ALWAYS);
            hbox.setSpacing(4.0);
            qtyHbox.setSpacing(8.0);
            name.setPadding(new Insets(0,0,0,16));
            order.setPadding(new Insets(0,8,0,8));
            harga.setPadding(new Insets(0,0,0,16));
        }

        public void updateItem(OrderList orderList, boolean empty){
            super.updateItem(orderList, empty);
            setText(null);
            setGraphic(null);
            if(name!=null && !empty){
                this.name.setText(orderList.getNamaOrder());
                this.order.setText(String.valueOf(orderList.getQtyOrder()));
                this.harga.setText(orderList.getHargaOrder()+"K");
                setGraphic(hbox);
            }
        }
    }
    public void dialofDelete(OrderList list){
        try {

            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Hapus Data"));
            content.setBody(new Text("Yakin ingin mengapus data dari keranjang?"));
            JFXDialog dialog =  new JFXDialog(cartStackpane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton cancelButton = new JFXButton("URUNGKAN");
            JFXButton OkButton = new JFXButton("HAPUS");
            content.getStyleClass().addAll("dialog-style");
            OkButton.getStyleClass().addAll("button-dialog");
            content.setActions(OkButton,cancelButton);
            cancelButton.setOnAction(event1 -> {
                dialog.close();
            });
            OkButton.setOnAction(event1 -> {
                try {
                    dbHelper.deleteOnCart(connection,list.getIdOrder());
                    LoadData();
                }catch (Exception e){
                    popup.toast(cartStackpane, "Please select item first");
                }
                dialog.close();
            });
            dialog.show();
        }catch (Exception e){
            popup.toast(cartStackpane, "Please select item first");
        }
    }
    private void LoadData() {
        try {
            listOfOrder = dbHelper.readOrderListDB(connection);
            listview.setCellFactory(param -> new Cell());
            listview.setItems(listOfOrder);
            if (listOfOrder.size() == 0){
                Parent root = FXMLLoader.load(getClass().getResource("/sample/Layout/emptyOrderState.fxml"));
                cartStackpane.getChildren().add(root);
            }
        }catch (Exception e){

        }

    }
}
