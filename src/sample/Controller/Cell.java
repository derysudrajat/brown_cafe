package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sample.Entity.IndonesaianFood;
import sample.Helper.DBHelper;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;


class Cell extends ListCell<IndonesaianFood> {
    Connection connection;
    IndonesaianFood food;
    DBHelper dbHelper =  new DBHelper();
    HBox hbox = new HBox();
    JFXButton btn1 = new JFXButton(" ");
    JFXButton btn2 = new JFXButton(" ");
    int order;
    Pane pane =  new Pane();
    VBox vbox = new VBox();
    Label name = new Label(" ");
    Label desc = new Label(" ");
    Label harga = new Label(" ");
    Image cart;
    ImageView img;
    public Cell(JFXListView<IndonesaianFood> listview, StackPane stackPane, int id) {
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        MaterialDesignIconView icon2 = new MaterialDesignIconView(MaterialDesignIcon.MINUS);
        icon.setGlyphSize(24);
        icon2.setGlyphSize(24);
        switch (id){
            case 1:
                cart = new Image("/sample/image/ic_indo.png");
                break;
            case 2:
                cart = new Image("/sample/image/ic_japan.png");
                break;
            case 3:
                cart = new Image("/sample/image/ic_juice.png");
                break;
            case 4:
                cart = new Image("/sample/image/ic_coffee.png");
                break;
        }

        img =  new ImageView(cart);
        img.setFitHeight(50);
        img.setFitWidth(50);

        icon.getStyleClass().addAll("icon_list_add");
        icon2.getStyleClass().addAll("icon_list_min");

        btn1.setGraphic(icon);
        btn2.setGraphic(icon2);
        btn1.setAlignment(Pos.CENTER);
        btn2.setAlignment(Pos.CENTER);
        btn2.setVisible(false);
        btn1.setContentDisplay(ContentDisplay.CENTER);
        btn2.setContentDisplay(ContentDisplay.CENTER);
        btn1.getStyleClass().addAll("btn_list_add");
        btn2.getStyleClass().addAll("bnt_list_min");

        btn1.setOnAction(event -> {

            try {
                food = listview.getSelectionModel().getSelectedItem();
                System.out.println("food id: "+food.getId());
                int id_now = dbHelper.getIdOrderList(connection, food.getId());
                System.out.println("id_now: "+id_now);
                if (id_now==0){
                    try {
                        connection = dbHelper.getConnection();
                        dbHelper.addOrderList(connection, food.getId(),food.getNama(),1,food.getHarga(),food.getOrder());
                        Text message = new Text("Item add to cart");
                        message.setFill(Color.WHITE);
                        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                        snackbar.getStyleClass().addAll("jfx-snackbar-content");
                        snackbar.enqueue(new SnackbarEvent(message));
                    } catch (SQLException e) {
                        Text error = new Text("Item was already added");
                        error.setFill(Color.WHITE);
                        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                        snackbar.getStyleClass().addAll("jfx-snackbar-content");
                        snackbar.enqueue(new SnackbarEvent(error));
                    }
                }else{
                    Text error = new Text("Item was already added");
                    error.setFill(Color.WHITE);
                    JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                    snackbar.getStyleClass().addAll("jfx-snackbar-content");
                    snackbar.enqueue(new SnackbarEvent(error));
                }
            }catch (Exception e){
                Text error = new Text("Please Select Item First");
                error.setFill(Color.WHITE);
                JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                snackbar.enqueue(new SnackbarEvent(error));

            }

        });

        name.setFont(Font.font("Product Sans", FontWeight.BOLD, 16));
        desc.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));
        harga.setFont(Font.font("Product Sans", FontWeight.NORMAL, 12));
        vbox.getChildren().addAll(name,desc,harga);
        hbox.getChildren().addAll(img,vbox, pane,btn1);
        vbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setHgrow(pane, Priority.ALWAYS);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(4.0);

        name.setPadding(new Insets(0,0,0,16));
        desc.setPadding(new Insets(0,0,0,16));
        harga.setPadding(new Insets(0,0,0,16));
    }
    public void updateItem(IndonesaianFood food, boolean empty){
        super.updateItem(food, empty);
        setText(null);
        setGraphic(null);
        if(name!=null && !empty){
            this.name.setText(food.getNama());
            this.desc.setText(food.getDesc());
            this.harga.setText(food.getHarga()+"K");
            this.order = food.getOrder();
            setGraphic(hbox);
        }
    }
}
