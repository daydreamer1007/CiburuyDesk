package application;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import java.sql.*;

public class LoginController {
    @FXML
    Pane loginPane;

    @FXML
    ImageView logo;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button login;

    @FXML
    Button guest;


    @FXML
    public void initialize(){
        loginPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        loginPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        logo.setFitWidth(loginPane.getPrefWidth() * 0.2);
        logo.fitWidthProperty().bind(loginPane.prefWidthProperty().divide(8));
        logo.fitHeightProperty().bind(logo.fitWidthProperty());
        logo.setSmooth(true);
        logo.setPreserveRatio(true);
        logo.layoutXProperty().bind(loginPane.prefWidthProperty().subtract(logo.fitWidthProperty()).divide(2));
        logo.layoutYProperty().bind(loginPane.prefHeightProperty().divide(20));

        username.prefWidthProperty().bind(loginPane.prefWidthProperty().divide(4));
        username.prefHeightProperty().bind(loginPane.prefHeightProperty().divide(25));
        username.layoutXProperty().bind(loginPane.prefWidthProperty().subtract(username.prefWidthProperty()).divide(2));
        username.layoutYProperty().bind(logo.layoutYProperty().add(logo.fitHeightProperty()).add(10));

        password.prefWidthProperty().bind(username.prefWidthProperty());
        password.prefHeightProperty().bind(username.prefHeightProperty());
        password.layoutXProperty().bind(username.layoutXProperty());
        password.layoutYProperty().bind(username.layoutYProperty().add(username.prefHeightProperty()).add(10));
        password.setDisable(true);

        login.setCursor(Cursor.HAND);
        login.prefWidthProperty().bind(username.prefWidthProperty());
        login.prefHeightProperty().bind(username.prefHeightProperty().multiply(1.5));
        login.layoutXProperty().bind(username.layoutXProperty());
        login.layoutYProperty().bind(password.layoutYProperty().add(password.heightProperty()).add(30));
        login.setDisable(true);

        guest.setCursor(Cursor.HAND);
        guest.prefWidthProperty().bind(username.prefWidthProperty());
        guest.prefHeightProperty().bind(login.prefHeightProperty());
        guest.layoutXProperty().bind(username.layoutXProperty());
        guest.layoutYProperty().bind(login.layoutYProperty().add(login.prefHeightProperty()).add(15));

        System.out.println(loginPane.getPrefHeight());

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 50){
                    login.setDisable(true);
                }
                else{
                    password.setDisable(false);

                    if(password.getText().length() > 0 && password.getText().length() <= 50){
                        login.setDisable(false);
                    }
                }
            }
        });

        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 50){
                    login.setDisable(true);
                }
                else{
                    login.setDisable(false);
                }
            }
        });

    }
}
