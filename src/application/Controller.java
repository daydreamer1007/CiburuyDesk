package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.xml.transform.Result;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.List;

public class Controller {
    @FXML
    Pane mainPane;

    @FXML
    Pane sidePane;

    @FXML
    Label titleLabel;

    @FXML
    Pane welcome;

    @FXML
    public Pane galeri;

    @FXML
    Pane naskah;

    @FXML
    Pane penelitian;

    @FXML
    Pane permainan;

    @FXML
    Pane about;

    @FXML
    Pane pencarian;

    @FXML
    BorderPane content;

    @FXML
    Label welcomeText;

    @FXML
    Label galeriText;

    @FXML
    Label naskahText;

    @FXML
    Label penelitianText;

    @FXML
    Label permainanText;

    @FXML
    Label pencarianText;

    @FXML
    Label aboutText;

    public static String dbms = "mysql";
    public static String address = "localhost";
    public static String port = "3306";
    public static String database = "mandala";
    public static String user = "root";
    public static String pass = "12345";

    public static String loggedName;
    public static String loggedStats;

    @FXML
    public void initialize(){
        DoubleProperty titleX = new SimpleDoubleProperty(10.0);
        DoubleProperty titleY = new SimpleDoubleProperty(14.4);

        titleX.bind(sidePane.widthProperty().subtract(titleLabel.widthProperty()).divide(2));
        titleY.bind(sidePane.heightProperty().divide(50));

        titleLabel.layoutXProperty().bind(titleX);
        titleLabel.layoutYProperty().bind(titleY);

        galeri.setDisable(true);
        naskah.setDisable(true);
        penelitian.setDisable(true);
        permainan.setDisable(true);
        pencarian.setDisable(true);
        about.setDisable(true);

        welcome.setCursor(Cursor.HAND);
        galeri.setCursor(Cursor.HAND);
        naskah.setCursor(Cursor.HAND);
        penelitian.setCursor(Cursor.HAND);
        permainan.setCursor(Cursor.HAND);
        pencarian.setCursor(Cursor.HAND);
        about.setCursor(Cursor.HAND);

        sidePane.widthProperty().addListener(event -> resizeChange(sidePane.getChildren()));
        sidePane.heightProperty().addListener(event -> resizeChange(sidePane.getChildren()));
        welcome.widthProperty().addListener(event -> resizeChange(welcome.getChildren()));
        welcome.heightProperty().addListener(event -> resizeChange(welcome.getChildren()));
        galeri.widthProperty().addListener(event -> resizeChange(galeri.getChildren()));
        galeri.heightProperty().addListener(event -> resizeChange(galeri.getChildren()));
        naskah.widthProperty().addListener(event -> resizeChange(naskah.getChildren()));
        naskah.heightProperty().addListener(event -> resizeChange(naskah.getChildren()));
        penelitian.widthProperty().addListener(event -> resizeChange(penelitian.getChildren()));
        penelitian.heightProperty().addListener(event -> resizeChange(penelitian.getChildren()));
        permainan.widthProperty().addListener(event -> resizeChange(permainan.getChildren()));
        permainan.heightProperty().addListener(event -> resizeChange(permainan.getChildren()));
        pencarian.widthProperty().addListener(event -> resizeChange(pencarian.getChildren()));
        pencarian.heightProperty().addListener(event -> resizeChange(pencarian.getChildren()));
        about.widthProperty().addListener(event -> resizeChange(about.getChildren()));
        about.heightProperty().addListener(event -> resizeChange(about.getChildren()));

        welcomeText.fontProperty().addListener(event -> labelResize(welcomeText.getGraphic()));
        galeriText.fontProperty().addListener(event -> labelResize(galeriText.getGraphic()));
        naskahText.fontProperty().addListener(event -> labelResize(naskahText.getGraphic()));
        penelitianText.fontProperty().addListener(event -> labelResize(penelitianText.getGraphic()));
        permainanText.fontProperty().addListener(event -> labelResize(permainanText.getGraphic()));
        pencarianText.fontProperty().addListener(event -> labelResize(pencarianText.getGraphic()));
        aboutText.fontProperty().addListener(event -> labelResize(aboutText.getGraphic()));

        try {
            content.getChildren().add(FXMLLoader.load(getClass().getResource("welcome.fxml")));
            ((Pane) content.lookup("#welcomePane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane) content.lookup("#welcomePane")).prefWidthProperty().bind(content.prefWidthProperty());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void resizeChange(List<Node> list){
        Double sizeX = mainPane.getWidth();
        Double sizeY = mainPane.getHeight();
        Double fontSize = Math.hypot(sizeX, sizeY) / 80;

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getClass().equals(Label.class)){
                ((Label)list.get(i)).setFont(Font.font("System", FontWeight.BOLD, fontSize));
            }
        }
    }

    private void labelResize(Node graphic){
        Double graphX = sidePane.getWidth();
        Double graphY = sidePane.getHeight();
        Double graphSize = Math.hypot(graphX, graphY) / 26;

        ((ImageView)graphic).setFitWidth(graphSize);
    }

    @FXML
    public void welcomeClicked(){
        welcome.setStyle("-fx-background-color: FFD700");
        welcome.setDisable(true);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();
        try {
            content.getChildren().add(FXMLLoader.load(getClass().getResource("welcome.fxml")));
            ((Pane)content.lookup("#welcomePane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane)content.lookup("#welcomePane")).prefWidthProperty().bind(content.prefWidthProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void galeriClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFD700");
        galeri.setDisable(true);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        try{
            content.getChildren().add(FXMLLoader.load(getClass().getResource("Galeri.fxml")));
            ((Pane)content.lookup("#galeriPane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane)content.lookup("#galeriPane")).prefWidthProperty().bind(content.prefWidthProperty());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void naskahClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFD700");
        naskah.setDisable(true);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        try{
            content.getChildren().add(FXMLLoader.load(getClass().getResource("Naskah.fxml")));
            ((Pane)content.lookup("#naskahPane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane)content.lookup("#naskahPane")).prefWidthProperty().bind(content.prefWidthProperty());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void penelitianClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFD700");
        penelitian.setDisable(true);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();
    }

    @FXML
    public void permainanClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFD700");
        permainan.setDisable(true);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();
    }

    @FXML
    public void pencarianClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFD700");
        pencarian.setDisable(true);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();
    }

    @FXML
    public void aboutClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFD700");
        about.setDisable(true);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();
    }


    //Functions to change the menue panes' colors when mouse is over them. Just for aestethic purpose

    @FXML
    public void welcomeMouseEnterExit(){
        if(!welcome.isDisabled()){
            if(welcome.getStyle().contains("FFA500")){
                welcome.setStyle("-fx-background-color: FFD700");
            }
            else{
                welcome.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void galeriMouseEnterExit(){
        if(!galeri.isDisabled()) {
            if (galeri.getStyle().contains("FFA500")) {
                galeri.setStyle("-fx-background-color: FFD700");
            } else {
                galeri.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void naskahMouseEnterExit(){
        if(!naskah.isDisabled()) {
            if (naskah.getStyle().contains("FFA500")) {
                naskah.setStyle("-fx-background-color: FFD700");
            } else {
                naskah.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void penelitianMouseEnterExit(){
        if(!penelitian.isDisabled()) {
            if (penelitian.getStyle().contains("FFA500")) {
                penelitian.setStyle("-fx-background-color: FFD700");
            } else {
                penelitian.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void permainanMouseEnterExit(){
        if(!permainan.isDisabled()) {
            if (permainan.getStyle().contains("FFA500")) {
                permainan.setStyle("-fx-background-color: FFD700");
            } else {
                permainan.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void pencarianMouseEnterExit(){
        if(!pencarian.isDisabled()) {
            if (pencarian.getStyle().contains("FFA500")) {
                pencarian.setStyle("-fx-background-color: FFD700");
            } else {
                pencarian.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void aboutMouseEnterExit(){
        if(!about.isDisabled()) {
            if (about.getStyle().contains("FFA500")) {
                about.setStyle("-fx-background-color: FFD700");
            } else {
                about.setStyle("-fx-background-color: FFA500");
            }
        }
    }
}
