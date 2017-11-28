package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.List;

public class welcomeController {
    @FXML
    Pane welcomePane;

    @FXML
    BorderPane welcome;

    @FXML
    Button logout;

    private Label welcomeLabel;

    @FXML
    public void initialize(){
        welcomePane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        welcomePane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        welcomeLabel = new Label();
        welcomeLabel.setText("Selamat datang, " + Controller.loggedName);
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setFont(Font.font("System Bold", FontWeight.BOLD, 54));

        welcome.prefWidthProperty().bind(welcomePane.prefWidthProperty());
        welcome.prefHeightProperty().bind(welcomePane.prefHeightProperty().divide(3));
        welcome.setCenter(welcomeLabel);

        logout.prefWidthProperty().bind(welcomePane.prefWidthProperty().divide(4));
        logout.prefHeightProperty().bind(welcomePane.prefHeightProperty().multiply(0.06));
        logout.layoutXProperty().bind(welcomePane.prefWidthProperty().subtract(logout.prefWidthProperty()).divide(2));
        logout.layoutYProperty().bind(welcome.prefHeightProperty());

        welcome.widthProperty().addListener(event -> resizeChange(welcome.getChildren()));
    }

    private void resizeChange(List<Node> list){
        Double sizeX = ((Pane)welcomePane.getParent().getParent()).getWidth();
        Double sizeY = ((Pane)welcomePane.getParent().getParent()).getHeight();
        Double fontSize = Math.hypot(sizeX, sizeY) / 32;

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
