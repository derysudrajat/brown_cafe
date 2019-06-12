package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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

public class CoffeeMenuController implements Initializable {
    public MaterialDesignIconView sortHargaIndicator1;
    @FXML
    private JFXListView<IndonesaianFood> listview;

    @FXML
    private StackPane coffeaStackpane;
    private ObservableList<IndonesaianFood> listofFood = FXCollections.observableArrayList();
    private IndonesaianFood food;
    boolean sortDSC =true;
    DBHelper dbHelper = new DBHelper();
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sortHargaIndicator1.setVisible(false);
        LoadData();
    }


    public ObservableList<IndonesaianFood> readDB(Connection connection,int nav) throws SQLException {

        String query =null;
        switch (nav){
            case 1:
                query = "SELECT * FROM kopi";
                break;
            case 2:
                query = "SELECT * FROM kopi ORDER BY populer DESC";
                break;
            case 3:
                query = "SELECT * FROM kopi ORDER BY harga_kopi DESC";
                break;
            case 4:
                query = "SELECT * FROM kopi ORDER BY harga_kopi ASC ";
                break;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<IndonesaianFood> indonesaianFoods = FXCollections.observableArrayList();

        while (resultSet.next()) {
            IndonesaianFood indonesaianFood = new IndonesaianFood(
                    resultSet.getInt("id_kopi"),
                    resultSet.getString("nama_kopi"),
                    resultSet.getString("deskripsi_kopi"),
                    resultSet.getInt("harga_kopi"),
                    resultSet.getInt("foto_kopi")
            );
            indonesaianFoods.add(indonesaianFood);
        }

        return indonesaianFoods;
    }

    private void LoadData() {
        try {
            connection = dbHelper.getConnection();
            listofFood = readDB(connection,1);
            listview.setCellFactory(param -> {
                return new Cell(listview,coffeaStackpane,4);
            });
            listview.setItems(listofFood);
        }catch (Exception e){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/Layout/databaseErrorState.fxml"));
            } catch (IOException e1) {

            }
            coffeaStackpane.getChildren().add(root);
        }
    }

    public void sortPopuler(ActionEvent event) {
        try {
            connection = dbHelper.getConnection();
            listofFood = readDB(connection,2);
            listview.setCellFactory(param -> {
                return new Cell(listview,coffeaStackpane,4);
            });
            listview.setItems(listofFood);
        }catch (Exception e){

        }
    }

    public void sortHarga(ActionEvent event) {
        sortHargaIndicator1.setVisible(true);
        try {
            if (sortDSC){
                sortHargaIndicator1.setGlyphName("MENU_DOWN");
                listofFood = readDB(connection,3);
                listview.setCellFactory(param -> {
                    return new Cell(listview,coffeaStackpane,4);
                });
                listview.setItems(listofFood);
                sortDSC=false;
            }else{
                sortHargaIndicator1.setGlyphName("MENU_UP");
                listofFood = readDB(connection,4);
                listview.setCellFactory(param -> {
                    return new Cell(listview,coffeaStackpane,4);
                });
                listview.setItems(listofFood);
                sortDSC=true;
            }
        }catch (Exception e){

        }

    }
}
