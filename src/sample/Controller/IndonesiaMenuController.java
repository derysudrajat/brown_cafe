package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import sample.Entity.IndonesaianFood;
import sample.Helper.DBHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IndonesiaMenuController extends MenuCategoryController implements Initializable {
    @FXML
    private JFXListView<IndonesaianFood> listview;
    @FXML
    private MaterialDesignIconView sortHargaIndicator;
    private ObservableList<IndonesaianFood> listofFood = FXCollections.observableArrayList();
    private IndonesaianFood food;
    private Connection connection;

    @FXML
    private StackPane indoStackpane;
    boolean sortDSC = true;
    DBHelper dbHelper = new DBHelper();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBHelper dbHelper = new DBHelper();
        sortHargaIndicator.setVisible(false);
        try {
            connection = dbHelper.getConnection();
            LoadData();
        } catch (SQLException e) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/Layout/databaseErrorState.fxml"));
            } catch (IOException e1) {

            }
            indoStackpane.getChildren().add(root);
        } catch (ClassNotFoundException e) {

        }
    }

    @FXML
    void showData(MouseEvent event) {

    }


    @FXML
    void sortHarga(ActionEvent event) throws SQLException {
        sortHargaIndicator.setVisible(true);
        if (sortDSC){
            sortHargaIndicator.setGlyphName("MENU_DOWN");
            listofFood = dbHelper.readIndoDB(connection,3);
            listview.setCellFactory(param -> {
                return new Cell(listview,indoStackpane,1);
            });
            listview.setItems(listofFood);
            sortDSC=false;
        }else{
            sortHargaIndicator.setGlyphName("MENU_UP");
            listofFood = dbHelper.readIndoDB(connection,4);
            listview.setCellFactory(param -> {
                return new Cell(listview,indoStackpane,1);
            });
            listview.setItems(listofFood);
            sortDSC=true;
        }

    }

    @FXML
    void sortPopuler(ActionEvent event) throws SQLException {
        sortHargaIndicator.setVisible(false);
        listofFood = dbHelper.readIndoDB(connection,2);
        listview.setCellFactory(param -> {
            return new Cell(listview,indoStackpane,1);
        });
        listview.setItems(listofFood);

    }

    private void LoadData() throws SQLException {
        listofFood = dbHelper.readIndoDB(connection,1);
        listview.setCellFactory(param -> {
            return new Cell(listview,indoStackpane,1);
        });
        listview.setItems(listofFood);
    }

}
