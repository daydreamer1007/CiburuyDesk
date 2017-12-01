package application;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;

import java.sql.*;
import java.time.LocalDate;

public class AdminController {
    @FXML
    Pane adminPane;

    @FXML
    ComboBox<String> filter;

    @FXML
    TableView tablelog;

    @FXML
    TableColumn<ObservableList<Integer>, Integer> cNo;

    @FXML
    TableColumn<ObservableList<String>, String> cNama;

    @FXML
    TableColumn<ObservableList<String>, String> cGender;

    @FXML
    TableColumn<ObservableList<Integer>, Integer> cUmur;

    @FXML
    TableColumn<ObservableList<String>, String> cAlamat;

    @FXML
    TableColumn<ObservableList<Date>, Date> cStart;

    @FXML
    TableColumn<ObservableList<Date>, Date> cEnd;

    @FXML
    Button logout;

    public void initialize(){
        adminPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        adminPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        tablelog.prefWidthProperty().bind(adminPane.prefWidthProperty().multiply(0.95));
        tablelog.prefHeightProperty().bind(adminPane.prefHeightProperty().multiply(0.8));
        tablelog.layoutXProperty().bind(adminPane.prefWidthProperty().subtract(tablelog.prefWidthProperty()).divide(2));
        tablelog.layoutYProperty().bind(tablelog.layoutXProperty().multiply(2));

        filter.getItems().addAll("All Time", "This Year", "This Month", "Today");
        filter.setValue("All Time");
        filter.layoutXProperty().bind(tablelog.layoutXProperty());
        filter.layoutYProperty().bind(tablelog.layoutXProperty());

        logout.prefWidthProperty().bind(adminPane.prefWidthProperty().divide(4));
        logout.prefHeightProperty().bind(adminPane.prefHeightProperty().multiply(0.06));
        logout.layoutXProperty().bind(adminPane.prefWidthProperty().subtract(logout.prefWidthProperty()).divide(2));
        logout.layoutYProperty().bind(tablelog.prefHeightProperty().add(tablelog.layoutYProperty().multiply(1.5)));

        cNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Integer>, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObservableList<Integer>, Integer> param) {
                return new ReadOnlyObjectWrapper(tablelog.getItems().indexOf(param.getValue()) + 1);
            }
        });

        cNo.setSortable(false);

        cNama.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                return new SimpleStringProperty(param.getValue().get(0));
            }
        });

        cGender.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                return new SimpleStringProperty(param.getValue().get(1));
            }
        });

        cUmur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Integer>, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObservableList<Integer>, Integer> param) {
                return new SimpleObjectProperty<Integer>(param.getValue().get(2));
            }
        });

        cAlamat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                return new SimpleStringProperty(param.getValue().get(3));
            }
        });

        cStart.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Date>, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TableColumn.CellDataFeatures<ObservableList<Date>, Date> param) {
                return new SimpleObjectProperty<Date>(param.getValue().get(4));
            }
        });

        cEnd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Date>, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TableColumn.CellDataFeatures<ObservableList<Date>, Date> param) {
                return new SimpleObjectProperty<Date>(param.getValue().get(5));
            }
        });

        cNo.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.05));
        cNama.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.2));
        cGender.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.1));
        cUmur.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.05));
        cAlamat.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.3).subtract(20));
        cStart.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.15));
        cEnd.prefWidthProperty().bind(tablelog.prefWidthProperty().multiply(0.15));

        cNo.setResizable(false);
        cNama.setResizable(false);
        cGender.setResizable(false);
        cUmur.setResizable(false);
        cAlamat.setResizable(false);
        cStart.setResizable(false);
        cEnd.setResizable(false);

        cNo.setStyle("-fx-alignment: CENTER");
        cGender.setStyle("-fx-alignment: CENTER");
        cUmur.setStyle("-fx-alignment: CENTER");
        cStart.setStyle("-fx-alignment: CENTER");
        cEnd.setStyle("-fx-alignment: CENTER");

        getData(filter.getValue());

        filter.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                getData(newValue);
            }
        });
    }

    private void getData(String filter){
        try{
            Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
            PreparedStatement statement;

            if(filter.equals("All Time")){
                statement = conn.prepareCall("SELECT * FROM log ORDER BY start DESC");
            }
            else if(filter.equals("This Year")){
                statement = conn.prepareCall("SELECT  * FROM log WHERE YEAR(start)=YEAR(NOW()) ORDER BY start DESC");
            }
            else if(filter.equals("This Month")){
                statement = conn.prepareCall("SELECT * FROM log WHERE YEAR(start)=YEAR(NOW()) AND MONTH(start)=MONTH(NOW()) ORDER BY start DESC");
            }
            else{
                statement = conn.prepareCall("SELECT * FROM log WHERE YEAR(start)=YEAR(NOW()) AND MONTH(start)=MONTH(NOW()) AND DAY(start)=DAY(NOW()) ORDER BY start DESC");
            }

            statement.execute();
            ResultSet result = statement.getResultSet();
            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            while(result.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(result.getString(i));
                }

                data.add(row);
            }

            tablelog.getItems().clear();
            tablelog.setItems(data);

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
