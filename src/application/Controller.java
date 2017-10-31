package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class Controller {
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
