package sample.Helper;

import com.jfoenix.controls.*;
import com.mysql.cj.x.protobuf.Mysqlx;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.Entity.IndonesaianFood;
import sample.Entity.OrderList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Popup {
    DBHelper dbHelper = new DBHelper();
    IndonesaianFood food;
    public void poup1Dialog(StackPane stackPane,String title, String body, String textConfirm){
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text(title));
            content.setBody(new Text(body));
            JFXDialog dialog =  new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            content.getStyleClass().addAll("dialog-style");
            JFXButton OkButton = new JFXButton(textConfirm);
            OkButton.getStyleClass().addAll("button-dialog");
            content.setActions(OkButton);
            OkButton.setOnAction(event1 -> {
                dialog.close();
            });
            dialog.show();
        }catch (Exception e){
            System.out.println("No data Selected");

        }
    }
    public void poup1DialogandLoadUI(StackPane stackPane,String title, String body, String textConfirm, String layout){
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text(title));
            content.setBody(new Text(body));
            JFXDialog dialog =  new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            content.getStyleClass().addAll("dialog-style");
            JFXButton OkButton = new JFXButton(textConfirm);
            OkButton.getStyleClass().addAll("button-dialog");
            content.setActions(OkButton);
            OkButton.setOnAction(event1 -> {
                dialog.close();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
                } catch (IOException e) {

                }
                stackPane.getChildren().addAll(root);
            });
            dialog.show();
        }catch (Exception e){
            System.out.println("No data Selected");

        }
    }
    public void poup2Dialog(StackPane stackPane,Connection connection, String tittle1,String body1, String textOk, String txtCancel, String tittle2,String body2, String txtConfirm, BorderPane borderPane, AnchorPane notifPane, Text qtyNotif){
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text(tittle1));
            content.setBody(new Text(body1));
            JFXDialog dialog =  new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton cancelButton = new JFXButton(txtCancel);
            JFXButton OkButton = new JFXButton(textOk);
            content.getStyleClass().addAll("dialog-style");
            OkButton.getStyleClass().addAll("button-dialog");
            content.setActions(OkButton,cancelButton);
            cancelButton.setOnAction(event1 -> {
                dialog.close();
            });
            OkButton.setOnAction(event -> {
                try {
                    dbHelper.deleteAllOnCart(connection);
                    poup1Dialog(stackPane,tittle2,body2,txtConfirm);
                    dialog.close();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/Layout/CartActivity.fxml"));
                        borderPane.setCenter(root);
                        dbHelper.LoadNotif(notifPane,qtyNotif);
                    } catch (IOException e) {

                    }
                }catch (Exception e){

                }
            });
            dialog.show();
        }catch (Exception e){

        }
    }
    public void poup2Menu(StackPane stackPane, JFXButton manuButton, String menu1, String menu2){
        JFXPopup popup = new JFXPopup(manuButton);
        JFXButton btnMenu1 = new JFXButton(menu1);
        JFXButton btnMenu2 = new JFXButton(menu2);
        btnMenu1.setPadding(new Insets(10,50,10,10));
        btnMenu2.setPadding(new Insets(10,50,10,10));
        VBox vBox = new VBox(btnMenu1,btnMenu2);
        btnMenu1.setOnAction(event -> {
            System.out.println("Menu 1 Pressed");
            poup1Dialog(stackPane,"Help","Dalam tahap ini anda dapat memilih\n" +
                    "menu makanan yang tersedia yaitu makanan\n" +
                    "Indonesia dan Jepang, serta dapat memilih\n" +
                    "Minuman yaitu Juice dan Kopi","OK");
        });
        btnMenu2.setOnAction(event -> {
            System.out.println("Menu 2 Pressed");
            poup1Dialog(stackPane,"Info","Aplikasi Browncafe ini dibuat oleh\n" +
                    "Mahasiswa S1 Software Engineering\n" +
                    "Fakultas Teknologi Industri dan Informatika\n" +
                    "Institut Teknologi Telkom","OK");
        });
        popup.setPopupContent(vBox);
        popup.setAutoHide(true);
        popup.show(manuButton,JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }
    public void poup3Menu(Connection connection, StackPane stackPane, JFXButton manuButton, String menu1, String menu2, String menu3, BorderPane borderPane, AnchorPane notifPane, Text qtyNotif){
        JFXPopup popup = new JFXPopup(manuButton);
        JFXButton btnMenuA = new JFXButton(menu1);
        JFXButton btnMenuB = new JFXButton(menu2);
        JFXButton btnMenuC = new JFXButton(menu3);

        btnMenuA.setPadding(new Insets(10,60,10,10));
        btnMenuB.setPadding(new Insets(10,30,10,10));
        btnMenuC.setPadding(new Insets(10,60,10,10));
        btnMenuA.setOnAction(event -> {
            poup1Dialog(stackPane,"Help","Dalam tahap ini anda dapat menambahkan\n" +
                    "atau mengurangi serta menghapus data\n" +
                    "dari keranjang belanjaan anda, sebelumnya\n" +
                    "anda harus memilih dahulu data yang ingin \n" +
                    "anda lakukan tindakan","OK");
        });
        btnMenuB.setOnAction(event -> {
            poup2Dialog(stackPane,connection,"Peringatan",
                    "Apakah anda yakin akan menghapus semua data?",
                    "HAPUS","URUNGKAN",
                    "Hapus Semua Data",
                    "Semua data berhasil di hapus,\n" +
                            "ayo tambahkan untuk mengisi list lagi",
                    "OK",borderPane, notifPane, qtyNotif);
        });
        btnMenuC.setOnAction(event -> {
            poup1Dialog(stackPane,"Info","Aplikasi Browncafe ini dibuat oleh\n" +
                    "Mahasiswa S1 Software Engineering\n" +
                    "Fakultas Teknologi Industri dan Informatika\n" +
                    "Institut Teknologi Telkom","OK");

        });
        VBox vBox = new VBox(btnMenuA,btnMenuB,btnMenuC);
        popup.setPopupContent(vBox);
        popup.setAutoHide(true);
        popup.show(manuButton,JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }
    public void toast(StackPane stackPane, String message){
        Text error = new Text(message);
        error.setFill(Color.WHITE);
        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.getStyleClass().addAll("jfx-snackbar-content");
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(error));
    }
}
