package application;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    Pane mainPane;

    @FXML
    Pane sidePane;

    @FXML
    Pane title;

    @FXML
    Label titleLabel;

    @FXML
    Pane welcome;

    @FXML
    Pane galeri;

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
    public void initialize(){
        DoubleProperty titleX = new SimpleDoubleProperty(10.0);
        DoubleProperty titleY = new SimpleDoubleProperty(14.4);

        titleX.bind(sidePane.widthProperty().subtract(titleLabel.widthProperty()).divide(2));
        titleY.bind(sidePane.heightProperty().divide(50));

        titleLabel.layoutXProperty().bind(titleX);
        titleLabel.layoutYProperty().bind(titleY);

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
            ((ScrollPane)content.lookup("#galeriScroll")).prefHeightProperty().bind(content.prefHeightProperty());
            ((ScrollPane)content.lookup("#galeriScroll")).prefWidthProperty().bind(content.prefWidthProperty());
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
