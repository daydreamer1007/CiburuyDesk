package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.List;

public class GuestController {
    @FXML
    Pane guestPane;

    @FXML
    Pane namaPane;

    @FXML
    Pane genderPane;

    @FXML
    Pane umurPane;

    @FXML
    Pane alamatPane;

    @FXML
    TextField nama;

    @FXML
    ToggleGroup gender;

    @FXML
    RadioButton genderL;

    @FXML
    RadioButton genderP;

    @FXML
    TextField umur;

    @FXML
    TextField alamat;

    @FXML
    Button batal;

    @FXML
    Button masuk;

    @FXML
    public void initialize(){
        guestPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        guestPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        namaPane.prefWidthProperty().bind(guestPane.prefWidthProperty().divide(2));
        namaPane.prefHeightProperty().bind(guestPane.prefHeightProperty().divide(17));
        namaPane.layoutXProperty().bind(guestPane.prefWidthProperty().subtract(namaPane.prefWidthProperty()).divide(2));
        namaPane.layoutYProperty().bind(guestPane.prefHeightProperty().divide(5));

        genderPane.prefWidthProperty().bind(namaPane.prefWidthProperty());
        genderPane.prefHeightProperty().bind(namaPane.prefHeightProperty().multiply(2));
        genderPane.layoutXProperty().bind(namaPane.layoutXProperty());
        genderPane.layoutYProperty().bind(namaPane.layoutYProperty().add(namaPane.prefHeightProperty()));

        umurPane.prefWidthProperty().bind(namaPane.prefWidthProperty());
        umurPane.prefHeightProperty().bind(namaPane.prefHeightProperty());
        umurPane.layoutXProperty().bind(namaPane.layoutXProperty());
        umurPane.layoutYProperty().bind(genderPane.layoutYProperty().add(genderPane.prefHeightProperty()));

        alamatPane.prefWidthProperty().bind(namaPane.prefWidthProperty());
        alamatPane.prefHeightProperty().bind(namaPane.prefHeightProperty());
        alamatPane.layoutXProperty().bind(namaPane.layoutXProperty());
        alamatPane.layoutYProperty().bind(umurPane.layoutYProperty().add(umurPane.prefHeightProperty()));

        nama.prefWidthProperty().bind(namaPane.prefWidthProperty().divide(2));
        nama.prefHeightProperty().bind(guestPane.prefHeightProperty().divide(25));
        nama.layoutXProperty().bind(namaPane.prefWidthProperty().divide(2));

        genderL.layoutXProperty().bind(nama.layoutXProperty());

        genderP.layoutXProperty().bind(nama.layoutXProperty());
        genderP.layoutYProperty().bind(genderPane.prefHeightProperty().divide(2));

        umur.prefWidthProperty().bind(nama.prefWidthProperty());
        umur.prefHeightProperty().bind(nama.prefHeightProperty());
        umur.layoutXProperty().bind(nama.layoutXProperty());

        alamat.prefWidthProperty().bind(nama.prefWidthProperty());
        alamat.prefHeightProperty().bind(nama.prefHeightProperty());
        alamat.layoutXProperty().bind(nama.layoutXProperty());

        namaPane.widthProperty().addListener(event -> resizeChange(namaPane.getChildren()));
        genderPane.widthProperty().addListener(event -> resizeChange(genderPane.getChildren()));
        umurPane.widthProperty().addListener(event -> resizeChange(umurPane.getChildren()));
        alamatPane.widthProperty().addListener(event -> resizeChange(alamatPane.getChildren()));

        masuk.prefWidthProperty().bind(nama.prefWidthProperty());
        masuk.prefHeightProperty().bind(nama.prefHeightProperty().multiply(1.5));
        masuk.layoutXProperty().bind(guestPane.prefWidthProperty().divide(2));
        masuk.layoutYProperty().bind(alamatPane.layoutYProperty().add(alamatPane.prefHeightProperty()));

        batal.prefWidthProperty().bind(masuk.prefWidthProperty().multiply(0.85));
        batal.prefHeightProperty().bind(masuk.prefHeightProperty());
        batal.layoutXProperty().bind(namaPane.layoutXProperty());
        batal.layoutYProperty().bind(masuk.layoutYProperty());

        nama.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 50){
                    masuk.setDisable(true);
                }
                else{
                    genderL.setDisable(false);
                    genderP.setDisable(false);

                    if(umur.getText().length() > 0 && alamat.getText().length() > 0 && alamat.getText().length() <= 100){
                        masuk.setDisable(false);
                    }
                }
            }
        });

        gender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue.getUserData() != null){
                    umur.setDisable(false);
                }
            }
        });

        umur.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    umur.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if(newValue.length() == 0){
                    masuk.setDisable(true);
                }
                else{
                    alamat.setDisable(false);

                    if(nama.getText().length() > 0 && nama.getText().length() <= 50 && alamat.getText().length() > 0 && alamat.getText().length() <= 100){
                        masuk.setDisable(false);
                    }
                }
            }
        });

        alamat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 100){
                    masuk.setDisable(true);
                }
                else{
                    masuk.setDisable(false);
                }
            }
        });
    }

    private void resizeChange(List<Node> list){
        Double sizeX = ((Pane)guestPane.getParent().getParent()).getWidth();
        Double sizeY = ((Pane)guestPane.getParent().getParent()).getHeight();
        Double fontSize = Math.hypot(sizeX, sizeY) / 80;

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getClass().equals(Label.class)){
                ((Label)list.get(i)).setFont(Font.font("System", FontWeight.BOLD, fontSize));
            }
            else if(list.get(i).getClass().equals(RadioButton.class)){
                ((RadioButton)list.get(i)).setFont(Font.font("System", FontWeight.BOLD, fontSize));
            }
        }
    }
}
